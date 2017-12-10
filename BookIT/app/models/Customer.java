package models;

//import com.sun.xml.internal.bind.v2.TODO;

import controllers.CustomerController;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigInteger;
import java.util.ArrayList;


@Entity
@DiscriminatorValue("C")
@Table(name = "Customer")
public class Customer extends User implements EventObserver{

    private ArrayList<Event> wishList;
    private ArrayList<Event> bookedEvents;
    private boolean hasAny;

    public Customer(String userFirstName, String userLastName, String userEmail, String userPassword, BigInteger phoneNo) {
        super(userFirstName, userLastName, userEmail, userPassword, phoneNo);

    }

    public ArrayList<Event> getBookedEvents()
    {
        return this.bookedEvents;
    }

    public void update()
    {
        CustomerController ccon = new CustomerController();
        ccon.sendMail(this.getUserEmail());
    }

}
