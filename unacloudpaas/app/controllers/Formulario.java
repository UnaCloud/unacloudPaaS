package controllers;

import play.*;
import play.data.*;
import play.data.validation.Constraints;
import play.data.validation.Constraints.*;
import play.mvc.*;
import views.html.*;

import static play.data.Form.*;
import static play.data.Form.form;

public class Formulario extends Controller {

    /**
     * Describes the hello form.
     */
    public static class Hello {
        @Required
        public String name;
        @Required
        @Constraints.Min(1) @Constraints.Max(100) public Integer repeat;
        public String color;
    }

    // -- Actions

    /**
     * Home page
     */
    public static Result index() {
        return ok(formulario.render(form(Hello.class)));
    }

    /**
     * Handles the form submission.
     */
    public static Result sayHello() {
        Form<Hello> form = form(Hello.class).bindFromRequest();
        if(form.hasErrors()) {
            return badRequest(formulario.render(form));
        } else {
            Hello data = form.get();
            return ok(
                    index.render()
            );
        }
    }

}
