package controllers;


import io.ebean.Ebean;
import models.Admin;
import models.Event;
import models.EventManager;
import models.User;
import play.data.FormFactory;
import play.mvc.Result;
import views.html.Admin.showAdminProfile;
import views.html.Admin.manageEventManager;
import views.html.User.index;

import javax.inject.Inject;
import java.util.List;

import static play.mvc.Results.ok;

public class AdminController {

    @Inject
    FormFactory formFactory;


    public Result showAdminDashboard(String email)

    {
        User admin = Admin.find.byId(email);
        List<Event> allEvents = Ebean.find(Event.class).where().findList();
        return ok(showAdminProfile.render(admin , allEvents));
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
}
