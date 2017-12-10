package controllers;

import akka.http.impl.engine.ws.WebSocket;
import io.ebean.Ebean;
import models.Customer;
import models.Event;
import models.Ticket;
import models.User;
import notifiers.MailerService;
import play.api.libs.mailer.MailerClient;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.filters.headers.SecurityHeadersFilter;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.Customer.*;
import views.html.EventManager.showEventManagerProfile;
import views.html.Ticket.*;
//import views.html.Ticket.bookTicketDetails;
import views.html.Ticket.createTicket;

import javax.inject.Inject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static java.lang.Boolean.TRUE;

public class TicketController extends Controller{

    @Inject
    FormFactory formFactory;
    @Inject
    MailerClient mailerClient;

    public Result bookTicket(int eventId)
    {
        Event event = Event.find.byId(new Integer(eventId).toString());
        String mail = session("connected");
        User user = User.find.byId(mail);
        if(event.getAvailableNoOfSeats() > 0)
        {
            return ok(createTicket.render(event, user));
        }

        return forbidden(""+event.getEventName()+" is full");
    }

    public Result confirmTicket(int eventId)
    {
        DynamicForm form = formFactory.form().bindFromRequest();
        String mail = session("connected");
        User user = User.find.byId(mail);
        Event event = Event.find.byId(new Integer(eventId).toString());

        EventController econ = new EventController();

        //return forbidden(""+mail+" "+event.getEventName());

        String eventManager = event.getEventOwnerEmail();

        Ticket t;
        try
        {
            Ticket temp = new Ticket(form.get("numtickets"),eventManager,event.getEventId(),user.getUserEmail(),TRUE);
            t = temp;
        }
        catch (Exception e)
        {
            return forbidden("Error in ticket creation "+e);

        }

        t.save();
        MailerService m = new MailerService(mailerClient);
        m.bookingConfirmation(t,event);
        int status = econ.updateEvent(t, event.getEventId());
        //Event temp = Event.find.byId(new Integer(eventId).toString());
        //return forbidden("attendees: "+status);
        return ok(bookingSuccess.render(t,event,user));

        //return TODO;
    }

    public Result cancelTicket(Integer ticketId)
    {
        Ticket t = Ticket.find.byId(ticketId.toString());
        String userId = session("connected");
        User user = User.find.byId(userId);
        Event event = Event.find.byId(new Integer(t.getEventId()).toString());

        EventController econ = new EventController();
        Integer status = econ.cancelEventTicket(t,event);
        t.delete();
        MailerService m = new MailerService(mailerClient);
        m.cancelConfirmation(event,user,ticketId);
        if (status == 0)
            return ok(cancelSuccess.render(user,event));
        else
            //return forbidden("Error in cancelling ticket "+status+" "+event.getEventId()+" "+event.getAttendees().size());
            return forbidden("Error in cancelling ticket "+status);

    }


}

