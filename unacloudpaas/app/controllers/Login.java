package controllers;

import com.avaje.ebean.Expr;
import models.User;
import play.data.*;
import play.mvc.*;
import play.mvc.Controller;
import views.html.*;

public class Login extends Controller{
    public static class LoginData {
        public String username;
        public String password;
        @Override
        public String toString() {
            return "LoginData{" +
                    "username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }
    public static Result authenticate() {
        Form<LoginData> loginForm = Form.form(LoginData.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest("Wrong parameters");
        } else {
            System.out.println(loginForm.get().username+" "+loginForm.get().password);
            User u=User.find.where(Expr.eq("username",loginForm.get().username)).findUnique();
            if(u!=null&&u.password.equals(loginForm.get().password)){
                System.out.println(" "+u.username+" "+u.password);
                session().clear();
                session("userId", ""+u.userId);
                return redirect(routes.Home.home());
            }else{
                return badRequest("Invalid credentials");
            }
        }
    }
    public static Result logout(){
        session().clear();
        return ok("Log out");
    }
}
