package controllers;

import play.*;
import play.data.*;
import play.data.validation.Constraints.*;
import play.mvc.*;
import views.html.*;

import static play.data.Form.*;

public class Files extends Controller {

    /**
     * Home page
     */
    public static Result index() {
        return ok(files.render());
    }

}
