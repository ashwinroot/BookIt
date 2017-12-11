package controllers;

import io.ebean.Ebean;
import models.Event;
import models.EventManager;
import models.User;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.EventManager.*;
import views.html.User.*;

import javax.inject.Inject;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EventManagerController extends Controller{

    @Inject
    FormFactory formFactory;

    public Result saveEventManager(){

        DynamicForm df = formFactory.form().bindFromRequest();
        EventManager eventManager = new EventManager(df.get("customerFirstName"), df.get("customerLastName"), df.get("customerEmail"), df.get("customerPassword"), BigInteger.valueOf(Long.parseLong(df.get("customerPhoneNo"))));
        eventManager.save();
        session("connected", eventManager.getUserEmail());
        return redirect(routes.EventManagerController.showEventManagerDashBoard(eventManager.getUserEmail()));
    }

    public Result showEventManagerDashBoard(String eventManagerEmail){
        User eventManager = User.find.byId(eventManagerEmail);
        List<Event> ownedEvents = Ebean.find(Event.class).where().eq("eventOwnerEmail", eventManagerEmail).findList();
        return ok(showEventManagerDashBoard.render(eventManager, ownedEvents));
    }

    public Result showEventManagerProfile(String eventManagerEmail){
        User eventManager = User.find.byId(eventManagerEmail);
        return ok(showEventManagerProfile.render(eventManager));
    }

    public Result updateEventManagerProfile(String eventManagerEmail){

        User eventManager = User.find.byId(eventManagerEmail);
        return ok(updateEventManagerProfile.render(eventManager));
    }

    public Result modifyEventManagerProfile(String eventManagerEmail){
        User eventManager = User.find.byId(eventManagerEmail);
        DynamicForm df = formFactory.form().bindFromRequest();
        eventManager.setUserFirstName(df.get("customerFirstName"));
        eventManager.setUserLastName(df.get("customerLastName"));
        eventManager.setPhoneNo(BigInteger.valueOf(Long.parseLong(df.get("customerPhoneNo"))));
        eventManager.update();

        List<Event> ownedEvents = Ebean.find(Event.class).where().eq("eventOwnerEmail", eventManagerEmail).findList();

        return ok(showEventManagerDashBoard.render(eventManager,ownedEvents));
    }


}
