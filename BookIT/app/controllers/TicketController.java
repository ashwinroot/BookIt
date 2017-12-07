package controllers;

import io.ebean.Ebean;
import models.Customer;
import models.Event;
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
import java.util.Iterator;
import java.util.List;

public class TicketController extends Controller{

    @Inject
    FormFactory formFactory;

    public Result bookTicket(int eventId, String userMail)
    {
        Event event = Event.find.byId(new Integer(eventId).toString());
        if(event.getAvailableNoOfSeats() > 0)
        {
            return ok(createTicket.render(event,userMail));
        }

        return forbidden(""+event.getEventName()+" is full");
    }

    public Result confirmTicket()
    {
        DynamicForm form = formFactory.form().bindFromRequest();
        User user = User.find.byId(form.get("usermail"));
        return TODO;
    }

}
