package controllers;


import io.ebean.Ebean;
import models.EObserver;
import models.Event;
import models.Ticket;
import models.User;
import notifiers.MailerService;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.libs.mailer.MailerClient;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.Customer.showCustomerDashboard;
import views.html.Event.createEvent;
import views.html.Event.showEventDetails;
import views.html.Event.showSearchEvents;
import views.html.EventManager.updateEvent;


import javax.inject.Inject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class EventController extends Controller{

    @Inject
    FormFactory formFactory;
    @Inject
    MailerClient mailerClient;

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
        String user = session("connected");
        User eventManager = User.find.byId(user);
        Event event = Event.find.byId(eventId.toString());
        return ok(showEventDetails.render(event));
    }

    public Result searchEvent(String userMail){
        List<Event> event = Event.find.all();
        return ok(showSearchEvents.render(event,userMail));
    }



    public Result updateEvent(Integer eventId){
        String user = session("connected");
        User eventManager = User.find.byId(user);
        Event event = Event.find.byId(eventId.toString());
        return ok(updateEvent.render(event,eventManager));
    }

    public int updateEvent(Ticket t, Integer eventId){

        Event event = Event.find.byId(eventId.toString());
        event.setAvailableNoOfSeats(event.getAvailableNoOfSeats()-t.getNumSeats());
        event.setTotalSales(event.getTotalSales()+(event.getPerTicketCost() * t.getNumSeats()));
        event.addObserver(t.getCustomerMail());
        event.update();
        return 0;
    }

    public Integer cancelEventTicket(Ticket t, Event event){

        String userMail = session("connected");
        User user = User.find.byId(userMail);
        event.setAvailableNoOfSeats(event.getAvailableNoOfSeats()+ t.getNumSeats());
        event.setTotalSales(event.getTotalSales()-(event.getPerTicketCost() * t.getNumSeats()));
        event.update();
        event.removeObserver(user.getUserEmail());
        return 0;
    }



    public Result deleteEvent(Integer eventId){
        Event event = Event.find.byId(eventId.toString());
        Event.find.byId(eventId.toString()).delete();

        String user = session("connected");
        User eventManager = User.find.byId(user);
        //event.notifyObserver();
        return redirect(routes.EventManagerController.showEventManagerDashBoard(eventManager.getUserEmail()));
    }

    public Result modifyEvent(Integer eventId) throws Exception{
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
        event.notifyObserver(mailerClient);

        MailerService m = new MailerService(mailerClient);
        m.eventUpdateNotification(event.getEventId(),eventManager.getUserEmail());
        return redirect(routes.EventManagerController.showEventManagerDashBoard(eventManager.getUserEmail()));
    }

}
