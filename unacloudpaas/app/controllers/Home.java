package controllers;

import play.*;
import play.data.*;
import play.data.validation.Constraints.*;
import play.mvc.*;
import views.html.*;

import static play.data.Form.*;

public class Home extends Controller {

    @Security.Authenticated(Secured.class)
    public static Result home() {
        return ok(home.render());
    }
    public static Result index() {
        return ok(login.render(false));
    }
    public static Result error() {
        return ok(login.render(true));
    }
}
