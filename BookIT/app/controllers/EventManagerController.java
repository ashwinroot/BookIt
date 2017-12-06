package controllers;

import io.ebean.Ebean;
import models.Event;
import models.EventManager;
import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.EventManager.*;
import views.html.User.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EventManagerController extends Controller{

    @Inject
    FormFactory formFactory;


    public Result createEventManager(){
        Form<EventManager> eventManagerForm = formFactory.form(EventManager.class);
        return ok(createEventManager.render(eventManagerForm));
    }

    public Result saveEventManager(){

        Form<EventManager> eventManagerForm= formFactory.form(EventManager.class).bindFromRequest();
        EventManager eventManager = eventManagerForm.get();

        eventManager.save();
        return redirect(routes.UserController.index());

    }

    public Result showEventManagerProfile(String eventManagerEmail){
        User eventManager = User.find.byId(eventManagerEmail);
        List<Event> ownedEvents = Ebean.find(Event.class).where().eq("eventOwnerEmail", eventManagerEmail).findList();
        Iterator<Event> iter = ownedEvents.iterator();



        return ok(showEventManagerProfile.render(eventManager, ownedEvents));
    }



}
