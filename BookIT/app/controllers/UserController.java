package controllers;

import models.EventManager;
import models.User;
import models.Customer;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;

import views.html.User.*;


public class UserController extends Controller {

    @Inject
    FormFactory formFactory;



    public Result index(){

        List<User> users = User.find.all();

        return ok(index.render(users));
    }

    public Result verifyUser(){
        Form<User> loginForm= formFactory.form(User.class).bindFromRequest();
        User user = loginForm.get();

        User userDB = user.find.byId(user.userEmail);

        if(userDB != null){
            if(userDB.getDecriminatorValue().equals("C")){

                return ok(showCustomerProfile.render());
            }

            else if(userDB.getDecriminatorValue().equals("E")){

                return ok(showEventManagerProfile.render());
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
