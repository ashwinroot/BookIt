package controllers;

import akka.http.impl.engine.ws.WebSocket;
import io.ebean.Ebean;
import models.Customer;
import models.Event;
import models.Ticket;
import models.User;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
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
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static java.lang.Boolean.TRUE;

public class TicketController extends Controller{

    @Inject
    FormFactory formFactory;

    public Result bookTicket(int eventId)
    {
        Event event = Event.find.byId(new Integer(eventId).toString());
        if(event.getAvailableNoOfSeats() > 0)
        {
            return ok(createTicket.render(event));
        }

        return forbidden(""+event.getEventName()+" is full");
    }

    public Result confirmTicket(int eventId)
    {
        DynamicForm form = formFactory.form().bindFromRequest();
        String mail = session("connected");
        User user = User.find.byId(mail);
        Event event = Event.find.byId(new Integer(eventId).toString());

        //return forbidden(""+mail+" "+event.getEventName());

        String eventManager = event.getEventOwnerEmail();

        Ticket t;
        try
        {
            Ticket temp = new Ticket(form.get("numtickets"),eventManager,user.userEmail,TRUE);
            t = temp;
        }
        catch (Exception e)
        {
            return forbidden("Error in ticket creation "+e);

        }
        t.save();
        return forbidden(""+t.getCustomerMail()+" "+t.getNumSeats());
        
        //return TODO;
    }

}
