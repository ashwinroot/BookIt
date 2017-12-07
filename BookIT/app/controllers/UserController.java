package controllers;

import models.EventManager;
import models.User;
import models.Customer;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;

import views.html.User.*;
import views.html.Admin.*;


public class UserController extends Controller {

    @Inject
    FormFactory formFactory;



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

                session("connected", userDB.userEmail);
                return redirect(routes.CustomerController.showCustomerDashBoard(userDB.userEmail));
                //return ok(showCustomerProfile.render());
            }

            else if(userDB.getDecriminatorValue().equals("E")){
                session("connected", userDB.userEmail);

                return redirect(routes.EventManagerController.showEventManagerProfile(userDB.userEmail));
                //return redirect(showEventManagerProfile.render());
            }
            else if(userDB.getDecriminatorValue().equals("A")){

                return ok(showAdminProfile.render());
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



}
