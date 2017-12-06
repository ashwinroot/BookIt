package controllers;

import models.Customer;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.User.createCustomer;

import javax.inject.Inject;

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
}
