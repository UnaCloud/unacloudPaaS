package unacloud.paas.monitor;

import com.avaje.ebean.CallableSql;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import com.avaje.ebean.SqlUpdate;
import models.Node;
import models.PlatformExecution;
import models.RolExecution;
import models.enums.ExecutionState;
import unacloud.paas.back.utils.PaaSUtils;
import unacloud.paas.failrecovery.FailureRecoveryManager;
import unacloud.paas.waiters.WaiterManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.rmi.server.ExportException;
import java.util.*;

public class VirtualMachineMonitor extends Thread {
    private static final int MAX_FAIL = 3;
    private static VirtualMachineMonitor monitor;
    private static final long WAIT_TIME = 60000;

    public synchronized static void startMonitor() {
        System.out.println("startMonitor");
        if (monitor == null) {
            (monitor = new VirtualMachineMonitor()).start();
        }
    }

    @Override
    public void run() {
        Set<Long> failedPlaftorms = new HashSet<>();
        while (true) {
            failedPlaftorms.clear();
            PaaSUtils.sleep(((System.currentTimeMillis() / WAIT_TIME) + 1) * WAIT_TIME - System.currentTimeMillis());
            List<PlatformExecution> platforms = getUsedVirtualMachines();
            if (!platforms.isEmpty()) {
                checkFailure(platforms);
                checkTermination(platforms);
            }
        }
    }

    private void checkFailure(List<PlatformExecution> platforms) {
        checkSSH(platforms);
        for (int p = 0; p < platforms.size(); p++) {
            final PlatformExecution platform = platforms.get(p);
            for (RolExecution role : platform.getRolExecution()) {
                List<Node> failedNodes = new ArrayList<>();
                for (Node node : role.getNodes()) {
                    if (node.getMaxSecuentialFailCount() >= MAX_FAIL) {
                        failedNodes.add(node);
                    }
                }
                if (!failedNodes.isEmpty()) {
                    platforms.remove(p--);
                    FailureRecoveryManager.onMachineFailure(platform.getId(), failedNodes);
                }
            }
        }
    }

    private void checkSSH(List<PlatformExecution> platforms) {
        List<Node> changes = new LinkedList<>();
        for (PlatformExecution platform : platforms) {
            for (RolExecution role : platform.getRolExecution()) {
                for (Node node : role.getNodes()) {
                    try (Socket s = new Socket();) {
                        s.connect(new InetSocketAddress(node.getIpAddress(), 22), 6000);
                        if (node.getMaxSecuentialFailCount() != 0) {
                            node.maxSecuentialFailCount = 0;
                            changes.add(node);
                        }
                    } catch (IOException ex) {
                        node.maxSecuentialFailCount++;
                        changes.add(node);
                    }
                }
            }
        }
        for (Node node : changes) {
            SqlUpdate update=Ebean.createSqlUpdate("UPDATE Node n SET n.maxSecuentialFailCount = "+node.getMaxSecuentialFailCount()+" WHERE n.id = "+node.getId());
            update.execute();
        }
    }

    private void checkTermination(List<PlatformExecution> platforms) {
        for (PlatformExecution platform : platforms) {
            if (platform.getPlatform().getWaiterClass() != null) {
                WaiterManager.checkTermination(platform);
            }
        }

    }

    private List<PlatformExecution> getUsedVirtualMachines() {
        return Ebean.find(PlatformExecution.class).where(Expr.eq("eternal",true)).findList();
        /*TypedQuery<PlatformExecution> query = entityManager.createNamedQuery("PlatformExecution.findByEternal", PlatformExecution.class);
        query.setParameter("state", ExecutionState.RUNNING);
        return query.getResultList();*/
    }
}