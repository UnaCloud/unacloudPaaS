package unacloud.paas.back.sshutils;

import models.ExecutionLog;
import models.Node;
import unacloud.paas.back.beans.LogManagerBean;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SSHCommandNoWait extends ProcessManager {

    private final List<Long> processIds = new ArrayList<>();
    private final int processSize;
    private Node dest;

    public SSHCommandNoWait(Node dest, String command, ExecutionLog log, int processSize) {
        super(log, "ssh root@" + dest.getIpAddress() + " " + command);
        this.processSize = processSize;
        this.dest = dest;
    }

    @Override
    public void waitFor() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            for (int e = 0; e < processSize; e++) {
                String h = br.readLine();
                log.appendLine(h);
                if (h != null && h.matches("[0-9]+")) {
                    processIds.add(Long.parseLong(h));
                }
            }
            log.appendLine(Arrays.toString(processIds.toArray(new Long[0])));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()))) {
            for(String h;(h=br.readLine())!=null;)log.appendLine(h);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        LogManagerBean.storeStaticLog(log);
        p.destroy();
    }
    public List<Long> getProcessIds() {
        return processIds;
    }

    public Node getDest() {
        return dest;
    }
}
