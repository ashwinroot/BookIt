package controllers;

import models.EventManager;
import models.User;
import models.Customer;
import notifiers.MailerService;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.libs.mailer.MailerClient;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.io.FileWriter;
import java.util.List;

import views.html.User.*;
import views.html.Admin.*;


public class UserController extends Controller {

    @Inject
    FormFactory formFactory;
    @Inject
    MailerClient mailerClient;




    public Result index(){

        List<User> users = User.find.all();

        return ok(index.render(users));
    }

    public Result verifyUser(){
        //Form<User> loginForm= formFactory.form(User.class).bindFromRequest();
        //User user = loginForm.get();

        DynamicForm df = formFactory.form().bindFromRequest();
        String userEmail = df.get("email");

        User userDB = User.find.byId(userEmail);

        if(userDB != null){
            if(userDB.getDecriminatorValue().equals("C")){

                session("connected", userDB.getUserEmail());
                return redirect(routes.CustomerController.showCustomerDashBoard(userDB.getUserEmail()));
                //return ok(showCustomerProfile.render());
            }

            else if(userDB.getDecriminatorValue().equals("E")){
                session("connected", userDB.getUserEmail());

                return redirect(routes.EventManagerController.showEventManagerDashBoard(userDB.getUserEmail()));
                //return redirect(showEventManagerProfile.render());
            }
            else if(userDB.getDecriminatorValue().equals("A")){

                return redirect(routes.AdminController.showAdminDashboard(userDB.getUserEmail()));
            }

            else
                return forbidden("Not found ");
        }

        else
            return forbidden("Not Found");

    }

    public Result edit(String id){

        User user = User.find.byId(id);
        Form<User> userForm = formFactory.form(User.class).fill(user);
        return ok(edit.render(userForm));
    }

    public Result update(){
        return TODO;
    }

    public Result destroy(Integer id){
        return TODO;
    }

    /*
    public Result sendMail(String mail)
    {
        MailerService m = new MailerService(mailerClient);
        try{

            FileWriter fw=new FileWriter("usercon_sendmail.txt");
            fw.write("Welcome to bookit. "+mail);
            fw.flush();
            fw.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        int status = m.sendEmail(mailerClient);

        return forbidden("Mailer service status:"+status);
    }
    */


}
