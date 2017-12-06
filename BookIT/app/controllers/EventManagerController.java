package controllers;

import models.EventManager;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.EventManager.*;

import javax.inject.Inject;

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

}
