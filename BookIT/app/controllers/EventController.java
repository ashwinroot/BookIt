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
        //User user = User.find.byId(t.getCustomerMail());
        /*
        String eventAttendees = event.getAttendees();
        int aCount = 0;
        int beforeLength = event.getNumAttendees();
        if (eventAttendees.equals(""))
        {
            //String temp = new ArrayList<String>();
            //temp.add(t.getCustomerMail());
            event.setAttendees(t.getCustomerMail());
            aCount = 1;
        }
        else
        {
            aCount = event.getNumAttendees();
            String[] temp = eventAttendees.split(" ");
            List<Ticket> tList = Ebean.find(Ticket.class).where().eq("customerMail", t.getCustomerMail()).findList();
            if(tList.size() < 2)
            {
                //eventAttendees.add(t.getCustomerMail());
                event.setAttendees(eventAttendees+" "+t.getCustomerMail());
                aCount+=1;

            }
        }

        int oCount = 0;
        String eventObservers = event.getObservers();
        if (eventObservers.equals(""))
        {
            event.setObservers(t.getCustomerMail());
            oCount = 1;
        }
        else
        {
            oCount = event.getNumObservers();
            String[] temp = eventObservers.split(" ");
            List<Ticket> tList = Ebean.find(Ticket.class).where().eq("customerMail", t.getCustomerMail()).findList();
            if(tList.size() < 2)
            {
                //eventAttendees.add(t.getCustomerMail());
                event.setObservers(eventObservers+" "+t.getCustomerMail());
                oCount+=1;

            }

        }
        */
        event.setAvailableNoOfSeats(event.getAvailableNoOfSeats()-t.getNumSeats());
        event.setTotalSales(event.getTotalSales()+(event.getPerTicketCost() * t.getNumSeats()));
        //event.setNumAttendees(aCount);
        //event.setNumObservers(oCount);
        event.addObserver(t.getCustomerMail());
        event.update();
        //int temp = event.getNumAttendees();
        return 0;
    }

    public Integer cancelEventTicket(Ticket t, Event event){
        //Event event = Event.find.byId(eventId.toString());
        String userMail = session("connected");
        User user = User.find.byId(userMail);

        //if ()
        //ArrayList<User> eventObservers = event.getObservers();
        //eventObservers.add(user);
        /*
        String eventAttendees = event.getAttendees();
        int beforeLength;
        if (eventAttendees.equals(""))
        {
            return 1;
        }
        else
        {
            beforeLength = event.getNumAttendees();
            String[] temp = eventAttendees.split(" ");
            String newAttendees = "";
            int count=0;
            for(int i=0;i<temp.length;i++)
            {
                if (!temp[i].equals(user.getUserEmail()))
                {
                    newAttendees = newAttendees+" "+temp[i];
                    count = count+1;
                }


            }
            event.setAttendees(newAttendees);
            event.setNumAttendees(count);
        }

        String eventObservers = event.getObservers();
        if (eventObservers.equals(""))
        {
            return 2;
        }
        else
        {
            String[] temp = eventObservers.split(" ");
            String newObservers = "";
            int count=0;
            for(int i=0;i<temp.length;i++)
            {
                if (!temp[i].equals(user.getUserEmail()))
                {
                    newObservers = newObservers+" "+temp[i];
                    count = count+1;
                }

            }
            event.setObservers(newObservers);
            event.setNumObservers(count);
        }
        */
        event.setAvailableNoOfSeats(event.getAvailableNoOfSeats()+ t.getNumSeats());
        event.setTotalSales(event.getTotalSales()-(event.getPerTicketCost() * t.getNumSeats()));
        event.update();
        event.removeObserver(user.getUserEmail());
        //int temp = event.getNumAttendees();
        //if (beforeLength == temp)
        //    return 3;
        //return ok(updateEvent.render(event));
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
        event.notifyObserver();

        MailerService m = new MailerService(mailerClient);
        m.eventUpdateNotification(event.getEventId(),eventManager.getUserEmail());
        /*
        String eventObservers = event.getObservers();
        String[] temp = eventObservers.split(" ");
        HashMap<String,Integer> mailList = new HashMap<String,Integer>();
        for(int i=0;i<temp.length;i++)
        {
            if (!mailList.containsKey(temp[i]))
            {
                User u = User.find.byId(temp[i]);
                m.eventUpdateNotification(event,u);
                mailList.put(temp[i],i);
            }
        }

        String eventAttendees = event.getAttendees();
        temp = eventAttendees.split(" ");
        for(int i=0;i<temp.length;i++)
        {
            if (!mailList.containsKey(temp[i]))
            {
                User u = User.find.byId(temp[i]);
                m.eventUpdateNotification(event,u);
                mailList.put(temp[i],i);
            }

        }
        */
        //return ok();
        //Date date = datef.parse(df.get("date"));
        //return forbidden(df.get("eventname")+" "+df.get("eventlocation")+" "+df.get("cost")+" "+df.get("seats")+" "+date);
        return redirect(routes.EventManagerController.showEventManagerDashBoard(eventManager.getUserEmail()));
    }

}
