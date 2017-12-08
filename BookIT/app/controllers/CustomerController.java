package controllers;

import io.ebean.Ebean;
import models.*;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.Customer.*;

import javax.inject.Inject;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CustomerController extends Controller{

    @Inject
    FormFactory formFactory;

    public Result createCustomer(){

        return ok(createCustomer.render());

    }

    public Result saveCustomer(){

        DynamicForm df = formFactory.form().bindFromRequest();

        Customer customer = new Customer(df.get("customerFirstName"), df.get("customerLastName"), df.get("customerEmail"), df.get("customerPassword"), BigInteger.valueOf(Long.parseLong(df.get("customerPhoneNo"))));
        customer.save();
        return redirect(routes.CustomerController.showCustomerDashBoard(customer.getUserEmail()));

    }

    public Result showCustomerDashBoard(String customerEmail){
        User customer = User.find.byId(customerEmail);

        return ok(showCustomerDashboard.render(customer));
    }

    public Result showCustomerProfile(String customerEmail){
        User customer = User.find.byId(customerEmail);
        return ok(showCustomerProfile.render(customer));
    }

    public Result addToWishList(Integer eventId){
        String user = session("connected");
        User customer = User.find.byId(user);
        WishList wishList = new WishList(customer.getUserEmail(),eventId);
        wishList.save();
        return ok(showCustomerDashboard.render(customer));
    }

    public Result showCustomerBookingHistory(String customerEmail)
    {
        User user = User.find.byId(customerEmail);
        List<Ticket> Tickets = Ebean.find(Ticket.class).where().eq("customerMail", customerEmail).findList();
        if (Tickets.size() > 0)
        {
            return ok(showCustomerBookingHistory.render(Tickets));
        }
        else
            return forbidden("No history to show");
    }

    public Result showCustomerWishList(String customerEmail){
        //List<Event> wishList = Ebean.find(WishList.class).where().eq("eventOwnerEmail", customerEmail).findList();
        List<WishList> wishList = Ebean.find(WishList.class).where().eq("customerEmail", customerEmail).findList();
        Iterator<WishList> iter = wishList.iterator();
        List<Event> eventWishList = new ArrayList<>();
        while(iter.hasNext()){
            eventWishList.add(Event.find.byId(iter.next().getEventID().toString()));
        }
        return ok(showCustomerWishList.render(eventWishList));
    }

    public Result updateCustomerProfile(String customerEmail){

        User customer = Customer.find.byId(customerEmail);
        return ok(updateCustomerProfile.render(customer));
    }

    public Result modifyCustomerProfile(String customerEmail){
        User customer = User.find.byId(customerEmail);
        DynamicForm df = formFactory.form().bindFromRequest();
        customer.setUserFirstName(df.get("customerFirstName"));
        customer.setUserLastName(df.get("customerLastName"));
        customer.setPhoneNo(BigInteger.valueOf(Long.parseLong(df.get("customerPhoneNo"))));
        customer.update();

        return ok(showCustomerDashboard.render(customer));
    }



}
