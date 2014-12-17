package controllers;

import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Created by ga.sotelo69 on 19/06/2014.
 */
public class Secured extends Security.Authenticator {
    @Override
    public String getUsername(Http.Context context) {
        return Controller.session("userId");
    }

    @Override
    public Result onUnauthorized(Http.Context context) {
        return redirect(routes.Home.error());
    }
}
