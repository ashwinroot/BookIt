package controllers;


import io.ebean.Ebean;
import models.Admin;
import models.Event;
import models.EventManager;
import models.User;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.Admin.showAdminProfile;
import views.html.Admin.showAdminDashboard;
import views.html.Admin.manageEventManager;
import views.html.Admin.updateAdminProfile;
import views.html.Customer.showCustomerDashboard;
import views.html.EventManager.updateEventManagerProfile;
import views.html.User.index;

import javax.inject.Inject;
import java.math.BigInteger;
import java.util.List;

import controllers.EventManagerController;

import static play.mvc.Results.ok;

public class AdminController extends Controller{

    @Inject
    FormFactory formFactory;


    public Result showAdminDashboard(String email)

    {
        User admin = Admin.find.byId(email);
        List<Event> allEvents = Ebean.find(Event.class).where().findList();
        return ok(showAdminDashboard.render(admin , allEvents));
    }
    public Result user(){

        List<User> users = User.find.all();

        return ok(index.render(users));
    }

    public Result showManageEM(String email)
    {
        User admin = Admin.find.byId(email);
        List <User> em = Ebean.find(User.class).where().eq("user_type","E").findList();
        return ok(manageEventManager.render(admin,em));
    }

    public Result editManageEM(String adminE, String emE)
    {
        User admin = Admin.find.byId(adminE);
        User em = Admin.find.byId(emE);
        return ok(updateEventManagerProfile.render(em));
    }
    public Result deleteManageEM(String adminE, String emE)
    {
        User admin = Admin.find.byId(adminE);
        User.find.ref(emE).delete();
        List <User> em_list = Ebean.find(User.class).where().eq("user_type","E").findList();
        return ok(manageEventManager.render(admin,em_list));
    }
    public Result showAdminProfile(String adminE)
    {
        User admin = Admin.find.byId(adminE);
        return ok(showAdminProfile.render(admin));
    }
    public Result updateAdminProfile(String adminE)
    {
        User admin= User.find.byId(adminE);/**/
        DynamicForm df = formFactory.form().bindFromRequest();
        admin.setUserFirstName(df.get("customerFirstName"));
        admin.setUserLastName(df.get("customerLastName"));
        List<Event> allEvents = Ebean.find(Event.class).where().findList();
        admin.setPhoneNo(BigInteger.valueOf(Long.parseLong(df.get("customerPhoneNo"))));
        admin.update();
        return ok(showAdminDashboard.render(admin,allEvents));
    }

    public Result supdateAdminProfile(String adminE)
    {
        User admin= User.find.byId(adminE);
        return ok(updateAdminProfile.render(admin));
    }
}
