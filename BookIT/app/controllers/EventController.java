package controllers;

import io.ebean.Ebean;
import models.Event;

import models.EventManager;
import models.User;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.Event.createEvent;
import views.html.Event.showEventDetails;
import views.html.Event.updateEvent;

import javax.inject.Inject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

        String user = session("connected");
        User eventManager = User.find.byId(user);

        DateFormat datef = new SimpleDateFormat("MM/dd/yyyy");
        try{
            Date date = datef.parse(df.get("date"));
            Event event = new Event(df.get("eventname"),date,df.get("eventlocation"), Float.parseFloat(df.get("cost")), eventManager.userEmail, Integer.parseInt(df.get("seats")));
            event.save();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return redirect(routes.EventManagerController.showEventManagerProfile(eventManager.userEmail));
    }

    public Result showEvent(Integer eventId){
        Event event = Event.find.byId(eventId.toString());
        return ok(showEventDetails.render(event));
    }

    public Result updateEvent(Integer eventId){
        Event event = Event.find.byId(eventId.toString());
        return ok(updateEvent.render(event));
    }

    public Result modifyEvent(Integer eventId)
    {

        String user = session("connected");
        User eventManager = User.find.byId(user);

        DynamicForm df = formFactory.form().bindFromRequest();
        Event event = Event.find.byId(eventId.toString());

        DateFormat datef = new SimpleDateFormat("MM/dd/yyyy");
        try{
            Date date = datef.parse(df.get("date"));
            event.setEventDate(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        event.setEventName(df.get("eventname"));
        event.setEventLocation(df.get("eventlocation"));
        event.setPerTicketCost(Float.parseFloat(df.get("cost")));
        event.setAvailableNoOfSeats(Integer.parseInt(df.get("seats")));
        event.update();
        return redirect(routes.EventManagerController.showEventManagerProfile(eventManager.userEmail));
    }

    public Result deleteEvent(Integer eventId){
        Event.find.byId(eventId.toString()).delete();
        String user = session("connected");
        User eventManager = User.find.byId(user);
        return redirect(routes.EventManagerController.showEventManagerProfile(eventManager.userEmail));
    }

}
