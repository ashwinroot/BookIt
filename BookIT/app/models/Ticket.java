package models;

import com.sun.org.apache.xpath.internal.operations.Bool;
import io.ebean.Finder;
import io.ebean.Model;
import io.ebeaninternal.server.lib.util.Str;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.security.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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

    public Ticket(String seats, String eventManagerMail, String customerMail, boolean status) throws Exception
    {
        this.numSeats = new Integer(seats);
        this.eventManagerMail = eventManagerMail;
        this.customerMail = customerMail;
        String timeStamp = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
        SimpleDateFormat datef = new SimpleDateFormat("MM/dd/yyyy");
        Date cd = datef.parse(timeStamp);
        this.bookDate = cd;
        this.Status = status;

    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public void setNumSeats(int numSeats) {
        this.numSeats = numSeats;
    }

    public String getEventManagerMail() {
        return eventManagerMail;
    }

    public void setEventManagerMail(String eventManagerMail) {
        this.eventManagerMail = eventManagerMail;
    }

    public String getCustomerMail() {
        return customerMail;
    }

    public void setCustomerMail(String customerMail) {
        this.customerMail = customerMail;
    }

    public Date getBookDate() {
        return bookDate;
    }

    public void setBookDate(Date bookDate) {
        this.bookDate = bookDate;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }
}
