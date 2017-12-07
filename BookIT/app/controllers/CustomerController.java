package controllers;

import io.ebean.Ebean;
import models.Customer;
import models.Event;
import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.Customer.*;
import views.html.EventManager.showEventManagerProfile;

import javax.inject.Inject;
import java.util.Iterator;
import java.util.List;

public class CustomerController extends Controller{

    @Inject
    FormFactory formFactory;

    public Result createCustomer(){

        Form<Customer> customerForm = formFactory.form(Customer.class);
        return ok(createCustomer.render(customerForm));

    }

    public Result saveCustomer(){

        Form<Customer> customerForm= formFactory.form(Customer.class).bindFromRequest();
        Customer customer = customerForm.get();

        customer.save();
        return redirect(routes.UserController.index());

    }

    public Result showCustomerDashBoard(String customerEmail){
        User customer = User.find.byId(customerEmail);

        return ok(showCustomerDashboard.render(customer));
    }

    public Result showCustomerProfile(String customerEmail){
        return TODO;
    }

    public Result showCustomerBookingHistory(String customerEmail){
        return TODO;
    }

    public Result showCustomerWishList(String customerEmail){
        return TODO;
    }
}
