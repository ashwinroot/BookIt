package controllers;


import models.User;
import notifiers.MailerService;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.libs.mailer.MailerClient;
import play.mvc.*;

import views.html.index;
import views.html.Home.*;
import views.html.User.*;

import javax.inject.Inject;


public class HomeController extends Controller {
    @Inject
    FormFactory formFactoryHome;
    @Inject
    MailerClient mailerClient;

    public Result index() {
        return ok(login2.render());
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

    public Result resetPassword()
    {

        return ok(resetPassword.render());
    }

    public Result resetSendMail()
    {
        MailerService m = new MailerService(mailerClient);

        DynamicForm df = formFactoryHome.form().bindFromRequest();
        String mail = df.get("email");
        m.mailPassword(mail);

        return ok(resetPasswordConfirmation.render());
    }


}
