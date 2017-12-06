package controllers;

import models.Event;

import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.Event.createEvent;

import javax.inject.Inject;

public class EventController extends Controller{

    @Inject
    FormFactory formFactory;

    public Result createEvent()
    {
        return ok(createEvent.render());
    }
    public Result saveEvent()
    {
        DynamicForm df = formFactory.form().bindFromRequest();
//        Event _e = new Event(df.get("eventname"),df.get("date"),df.get("eventlocation"),)
        return forbidden(df.get("eventname")+" "+df.get("date"));
    }
}
