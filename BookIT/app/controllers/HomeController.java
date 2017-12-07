package controllers;


import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;

import views.html.index;
import views.html.Home.*;

import javax.inject.Inject;


public class HomeController extends Controller {
    @Inject
    FormFactory formFactoryHome;

    public Result index() {
        return ok(index.render("Hello world"));
    }

    public Result welcome(String name, String lastName){
        return ok(welcome.render(name, lastName));
    }

    public Result login(){
//        Form<User> loginForm = formFactoryHome.form(User.class);
//
//        return ok(login1.render(loginForm));
        return ok(login2.render());
    }

    public Result logout(){

        session().remove("connected");
        return redirect(routes.HomeController.login());
    }


}
