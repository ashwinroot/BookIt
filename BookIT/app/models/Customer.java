package models;

//import com.sun.xml.internal.bind.v2.TODO;

import controllers.CustomerController;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.FileWriter;
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

    /*
    public void updateUser()
    {
        CustomerController ccon = new CustomerController();
        try{

            FileWriter fw=new FileWriter("customer_updateuser.txt");
            fw.write("Welcome to javaTpoint. "+this.getUserEmail()+" "+this.getUserFirstName()  );
            fw.flush();
            fw.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        ccon.sendMail(this.getUserEmail());
    }
    */

}
