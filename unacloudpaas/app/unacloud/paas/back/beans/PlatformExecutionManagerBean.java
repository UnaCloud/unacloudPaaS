package unacloud.paas.back.beans;

import com.avaje.ebean.Ebean;
import models.*;
import models.enums.ExecutionState;
import unacloud.paas.back.user.FolderManager;

import java.util.Collections;
import java.util.List;

public class PlatformExecutionManagerBean {

    public static void updatePlatformExecutionState(long platformExecutionId, ExecutionState state) {
        PlatformExecution platform = PlatformExecution.find.byId(platformExecutionId);
        platform.status=state;
        Ebean.update(platform);
    }

    public static List<PlatformExecution> getPlatformExecutionList(String username) {
        User user= Ebean.find(User.class,username);
        return user.platformExecutions;
    }

    public static List<PlatformExecution> getRunningPlatformExecutionList(String username) {
        List<PlatformExecution> list= Ebean.find(PlatformExecution.class).where().eq("username",username).eq("state",ExecutionState.RUNNING).findList();
        //TypedQuery<PlatformExecution> query = entityManager.createNamedQuery("findByUsernameAndState", PlatformExecution.class);
        //query.setParameter("username", username);
        //query.setParameter("state", ExecutionState.RUNNING);
        return list;
    }

    public static int getRunningExecutionPlatformsCount(String username) {
        //TypedQuery<Integer> query = entityManager.createQuery("select count(*) from `platformExecution` where `user_username`=? and `executionState_state`<=" + ExecutionState.RUNNING.getId() + ";", Integer.class);
        //return query.getSingleResult();
        return 0;
    }

    public static PlatformExecution createPlatformExecution(long platformId) {
        Platform platform = Platform.find.byId(platformId);
        PlatformExecution ret = new PlatformExecution();
        ret.platform=platform;
        Ebean.save(ret);
        return ret;
    }

    public static PlatformExecution getPlatformExecution(long platformExecutionId) {
        PlatformExecution platformExecution = PlatformExecution.find.byId(platformExecutionId);
        return platformExecution;
    }

    public static void addCommandWait(long nodeId, long processId) {
        Node node = Node.find.byId(nodeId);
        CommandWait command = new CommandWait();
        command.node=node;
        command.processId=processId;
        command.status=ExecutionState.RUNNING;
        Ebean.save(command);
    }

    public static void deletePlatformExecution(long platformExecutionId) {
        System.out.println("deletePlatformExecution " + platformExecutionId);
        PlatformExecution platformExecution = PlatformExecution.find.byId(platformExecutionId);
        Ebean.delete(platformExecution);
        FolderManager.deletePlatformFolder(platformExecutionId);
    }

    public static List<ExecutionLog> getPlatformLog(long platformExecutionId) {
        return Collections.emptyList();
        /*TypedQuery<ExecutionLog> query=entityManager.createNamedQuery("ExecutionLog.findByPlatform", ExecutionLog.class);
        query.setParameter("platformExecutionId", platformExecutionId);
        return query.getResultList();*/
    }
}
