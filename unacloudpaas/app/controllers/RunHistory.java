package controllers;

import models.PlatformExecution;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

/**
 * Created by ga.sotelo69 on 05/06/2014.
 */
public class RunHistory extends Controller {
    public static Result runningHistory() {
        int count=PlatformExecution.find.findRowCount();
        return ok(runningHistory.render(count,PlatformExecution.find.orderBy("id").findPagingList(25).getPage(0).getList()));
    }
}