package controllers;


import io.ebean.Ebean;
import models.Event;
import models.Ticket;
import models.User;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.Customer.showCustomerDashboard;
import views.html.Event.createEvent;
import views.html.Event.showEventDetails;
import views.html.Event.showSearchEvents;
import views.html.Event.updateEvent;


import javax.inject.Inject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
        //return forbidden("user details "+user);

        User eventManager = User.find.byId(user);

        DateFormat datef = new SimpleDateFormat("MM/dd/yyyy");
        try{
            Date date = datef.parse(df.get("date"));
            Event event = new Event(df.get("eventname"),date,df.get("eventlocation"), Float.parseFloat(df.get("cost")), eventManager.getUserEmail(), Integer.parseInt(df.get("seats")));
            event.save();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return redirect(routes.EventManagerController.showEventManagerDashBoard(eventManager.getUserEmail()));

    }

    public Result showEvent(Integer eventId){
        Event event = Event.find.byId(eventId.toString());
        return ok(showEventDetails.render(event));
    }

    public Result searchEvent(String userMail){
        List<Event> event = Event.find.all();
        return ok(showSearchEvents.render(event,userMail));
    }



    public Result updateEvent(Integer eventId){
        Event event = Event.find.byId(eventId.toString());
        return ok(updateEvent.render(event));
    }

    public boolean updateEvent(Ticket t, Event event){
        //Event event = Event.find.byId(eventId.toString());
        User user = User.find.byId(t.getCustomerMail());

        //ArrayList<User> eventObservers = event.getObservers();
        //eventObservers.add(user);
        ArrayList<User> eventAttendees = event.getAttendees();
        int beforeLength;
        if (eventAttendees==null)
        {
            ArrayList<User> temp = new ArrayList<User>();
            temp.add(user);
            event.setAttendees(temp);
            beforeLength = 0;
        }
        else
        {
            eventAttendees.add(user);
            event.setAttendees(eventAttendees);
            beforeLength = eventAttendees.size();
        }

        ArrayList<User> eventObservers = event.getObservers();
        if (eventObservers==null)
        {
            ArrayList<User> temp = new ArrayList<User>();
            temp.add(user);
            event.setObservers(temp);
        }
        else
        {
            eventObservers.add(user);
            event.setObservers(eventAttendees);
        }
        event.setAvailableNoOfSeats(event.getAvailableNoOfSeats()-t.getNumSeats());
        event.setTotalSales(event.getTotalSales()+(event.getPerTicketCost() * t.getNumSeats()));
        event.update();
        ArrayList<User> temp = event.getAttendees();
        int afterLength = temp.size();

        if (beforeLength == afterLength)
            return false;
        //return ok(updateEvent.render(event));
        return true;
    }


    public Result deleteEvent(Integer eventId){
        Event event = Event.find.byId(eventId.toString());
        Event.find.byId(eventId.toString()).delete();

        String user = session("connected");
        User eventManager = User.find.byId(user);

        return redirect(routes.EventManagerController.showEventManagerDashBoard(eventManager.getUserEmail()));
    }

    public Result modifyEvent(Integer eventId) {
        DynamicForm df = formFactory.form().bindFromRequest();

        String user = session("connected");
        User eventManager = User.find.byId(user);

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
        return redirect(routes.EventManagerController.showEventManagerDashBoard(eventManager.getUserEmail()));
    }

}
