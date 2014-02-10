package unacloud.paas.back.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import unacloud.paas.back.database.entities.CommandWait;
import unacloud.paas.back.database.entities.ExecutionLog;
import unacloud.paas.back.database.entities.Node;
import unacloud.paas.back.database.entities.Platform;
import unacloud.paas.back.database.entities.PlatformExecution;
import unacloud.paas.back.database.entities.RolExecution;
import unacloud.paas.back.database.entities.User;
import unacloud.paas.back.database.enums.ExecutionState;
import unacloud.paas.back.execution.entities.RuntimeExecutionLog;
import unacloud.paas.back.iaasservices.DeployedCluster;
import unacloud.paas.back.iaasservices.DeployedClusterRol;
import unacloud.paas.back.user.FolderManager;
import unacloudws.requests.VirtualImageRequest;
import unacloudws.responses.VirtualMachineExecutionWS;

@Stateless
@LocalBean
public class PlatformExecutionManagerBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext()
    private EntityManager entityManager;

    public void updatePlatformExecutionState(long platformExecutionId, ExecutionState state) {
        PlatformExecution platform = entityManager.find(PlatformExecution.class, platformExecutionId);
        platform.setStatus(state);
        entityManager.merge(platform);
    }

    public PlatformExecution generateVoidPlatformExecution(long platformExecutionId) {
        PlatformExecution platform = entityManager.find(PlatformExecution.class, platformExecutionId);
        return platform;
    }

    public boolean createPlatformExecution(PlatformExecution platformExecution, String username) {
        User user = entityManager.find(User.class, username);
        platformExecution.setUser(user);
        entityManager.persist(platformExecution);
        return true;
    }

    public List<PlatformExecution> getPlatformExecutionList(String username) {
        User user = entityManager.find(User.class, username);
        return user.getPlatformExecutions();
    }

    public List<PlatformExecution> getRunningPlatformExecutionList(String username) {
        TypedQuery<PlatformExecution> query = entityManager.createNamedQuery("findByUsernameAndState", PlatformExecution.class);
        query.setParameter("username", username);
        query.setParameter("state", ExecutionState.RUNNING);
        return query.getResultList();
    }

    public int getRunningExecutionPlatformsCount(String username) {
        TypedQuery<Integer> query = entityManager.createQuery("select count(*) from `platformExecution` where `user_username`=? and `executionState_state`<=" + ExecutionState.RUNNING.getId() + ";", Integer.class);
        return query.getSingleResult();
    }

    public PlatformExecution createPlatformExecution(long platformId) {
        Platform platform = entityManager.find(Platform.class, platformId);
        PlatformExecution ret = new PlatformExecution();
        ret.setPlatform(platform);
        return entityManager.merge(ret);
    }

    public PlatformExecution getPlatformExecution(long platformExecutionId) {
        PlatformExecution platformExecution = entityManager.find(PlatformExecution.class, platformExecutionId);
        return platformExecution;
    }

    public void addCommandWait(long nodeId, long processId) {
        Node node = entityManager.find(Node.class, nodeId);
        CommandWait command = new CommandWait();
        command.setNode(node);
        command.setProcessId(processId);
        command.setStatus(ExecutionState.RUNNING);
        entityManager.merge(command);
    }

    public void deletePlatformExecution(long platformExecutionId) {
        System.out.println("deletePlatformExecution " + platformExecutionId);
        PlatformExecution platformExecution = entityManager.find(PlatformExecution.class, platformExecutionId);
        entityManager.remove(platformExecution);
        FolderManager.deletePlatformFolder(platformExecutionId);
    }

    public List<ExecutionLog> getPlatformLog(long platformExecutionId) {
        TypedQuery<ExecutionLog> query=entityManager.createNamedQuery("ExecutionLog.findByPlatform", ExecutionLog.class);
        query.setParameter("platformExecutionId", platformExecutionId);
        return query.getResultList();
    }

    private static String padd(long l) {
        String h = "" + l;
        while (h.length() < 2) {
            h = "0" + h;
        }
        return h;
    }
    public static PlatformExecutionManagerBean getStaticInstance() {
        try {
            Context c = new InitialContext();
            return (PlatformExecutionManagerBean) c.lookup("java:global/UnaCloudPaaSBack/PlatformExecutionManagerBean!unacloud.paas.back.beans.PlatformExecutionManagerBean");
        } catch (NamingException ne) {
            Logger.getLogger("FailureRecoveryManager").log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
