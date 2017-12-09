package controllers;

import io.ebean.Ebean;
import models.*;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.libs.mailer.MailerClient;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.Customer.*;

import notifiers.MailerService;

import javax.inject.Inject;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CustomerController extends Controller{

    @Inject
    FormFactory formFactory;
    @Inject
    MailerClient mailerClient;

    public Result createCustomer(){

        return ok(createCustomer.render());

    }

    public Result saveCustomer(){

        DynamicForm df = formFactory.form().bindFromRequest();

        Customer customer = new Customer(df.get("customerFirstName"), df.get("customerLastName"), df.get("customerEmail"), df.get("customerPassword"), BigInteger.valueOf(Long.parseLong(df.get("customerPhoneNo"))));
        customer.save();
        MailerService m = new MailerService(mailerClient);
        m.verifyUser((User)customer);
        return redirect(routes.CustomerController.showCustomerDashBoard(customer.getUserEmail()));

    }

    public Result showCustomerDashBoard(String customerEmail){
        User customer = User.find.byId(customerEmail);
        List<Event> allEvents = Ebean.find(Event.class).where().findList();
        return ok(showCustomerDashboard.render(customer,allEvents));
    }

    public Result showCustomerProfile(String customerEmail){
        User customer = User.find.byId(customerEmail);
        return ok(showCustomerProfile.render(customer));
    }

    public Result addToWishList(Integer eventId){
        String user = session("connected");
        User customer = User.find.byId(user);
        WishList wishList = new WishList(customer.getUserEmail(),eventId);
        List<Event> allEvents= Ebean.find(Event.class).where().findList();
        wishList.save();
        return ok(showCustomerDashboard.render(customer, allEvents));
    }

    public Result showCustomerBookingHistory(String customerEmail)
    {
        User user = User.find.byId(customerEmail);
        List<Ticket> Tickets = Ebean.find(Ticket.class).where().eq("customerMail", customerEmail).findList();
        if (Tickets.size() > 0)
        {
            return ok(showCustomerBookingHistory.render(Tickets, user));
        }
        else
            return forbidden("No history to show");
    }

    public Result showCustomerWishList(String customerEmail){
       User customer = User.find.byId(customerEmail);
        List<WishList> wishList = Ebean.find(WishList.class).where().eq("customerEmail", customerEmail).findList();
        Iterator<WishList> iter = wishList.iterator();
        List<Event> eventWishList = new ArrayList<>();
        while(iter.hasNext()){
            eventWishList.add(Event.find.byId(iter.next().getEventID().toString()));
        }
        return ok(showCustomerWishList.render(eventWishList, customer));
    }

    public Result updateCustomerProfile(String customerEmail){

        User customer = Customer.find.byId(customerEmail);
//        List<Event> allEvents = Ebean.find(Event.class).where().findList();
        return ok(updateCustomerProfile.render(customer));
    }

    public Result modifyCustomerProfile(String customerEmail){
        User customer = User.find.byId(customerEmail);
        DynamicForm df = formFactory.form().bindFromRequest();
        customer.setUserFirstName(df.get("customerFirstName"));
        customer.setUserLastName(df.get("customerLastName"));
        List<Event> allEvents = Ebean.find(Event.class).where().findList();
        customer.setPhoneNo(BigInteger.valueOf(Long.parseLong(df.get("customerPhoneNo"))));
        customer.update();

        return ok(showCustomerDashboard.render(customer,allEvents));
    }


    public Result searchEventbyName(String customerEmail)
    {
        DynamicForm df = formFactory.form().bindFromRequest();
        List<Event> eventList = new ArrayList<Event>();
        String name = df.get("query_name");
        String location = df.get("query_location");
        String date = df.get("query_date");
        User customer = User.find.byId(customerEmail);
        if(name!="")
            eventList.addAll(Ebean.find(Event.class).where().like("event_name","%"+name+"%").findList());
        if(location!="")
            eventList.addAll(Ebean.find(Event.class).where().like("event_location","%"+location+"%").findList());
        if(date!="")
            eventList.addAll(Ebean.find(Event.class).where().like("event_date","%"+date+"%").findList());
        return ok(showCustomerDashboard.render(customer,eventList));
    }

    public Result sendMail(String mail)
    {
        MailerService m = new MailerService(mailerClient);
        int status = m.sendEmail();

        return forbidden("Mailer service status:"+status);
    }
}
