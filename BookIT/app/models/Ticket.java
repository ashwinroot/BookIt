package models;

import com.sun.org.apache.xpath.internal.operations.Bool;
import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

@Entity
public class Ticket extends Model {

    @Id
    public int ticketId;
    public int numSeats;
    //int eventID;
    public String eventManagerMail;
    //int userID;
    public String customerMail;
    public Date bookDate;
    public boolean Status;

}
