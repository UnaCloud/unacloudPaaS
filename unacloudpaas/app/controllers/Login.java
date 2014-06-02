package controllers;

import models.User;
import play.data.*;
import play.mvc.*;
import play.mvc.Controller;
import views.html.*;
/**
 * Created by ga.sotelo69 on 25/04/2014.
 */
public class Login extends Controller{
    public static Result login(){

        return ok(login.render(Form.form(LoginData.class)));
    }
    public static class LoginData {

        public String username;
        public String password;

    }
    public static Result authenticate() {
        Form<LoginData> loginForm = Form.form(LoginData.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        } else {
            User u=User.find.byId(loginForm.get().username);
            if(u!=null&&u.password.equals(loginForm.get().password)){
                session().clear();
                session("username", loginForm.get().username);
                return redirect(
                        routes.Home.index()
                );
            }else{
                return badRequest(login.render(loginForm));
            }
        }
    }
}
