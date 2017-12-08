package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

@Entity
public class Event extends Model{

    @Id
    private Integer eventId;
    private String eventName;
    private Date eventDate;
    private String eventLocation;
    private float perTicketCost;
    private String eventOwnerEmail;
    private Integer availableNoOfSeats;
    private float totalSales;
    private ArrayList<User> attendees;
    private ArrayList<User> observers;


    public static Finder<String, Event> find = new Finder<>(Event.class);


    public Event(String eventName, Date eventDate, String eventLocation, float perTicketCost, String eventOwnerEmail, Integer availableNoOfSeats){

        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventLocation = eventLocation;
        this.perTicketCost = perTicketCost;
        this.eventOwnerEmail = eventOwnerEmail;
        this.availableNoOfSeats = availableNoOfSeats;
        this.totalSales = 0;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public float getPerTicketCost() {
        return perTicketCost;
    }

    public void setPerTicketCost(float perTicketCost) {
        this.perTicketCost = perTicketCost;
    }

    public String getEventOwnerEmail() {
        return eventOwnerEmail;
    }

    public void setEventOwnerEmail(String eventOwnerEmail) {
        this.eventOwnerEmail = eventOwnerEmail;
    }

    public Integer getAvailableNoOfSeats() {
        return availableNoOfSeats;
    }

    public void setAvailableNoOfSeats(Integer availableNoOfSeats) {
        this.availableNoOfSeats = availableNoOfSeats;
    }

    public float getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(float totalSales) {
        this.totalSales = totalSales;
    }

    public ArrayList<User> getAttendees() {
        return attendees;
    }

    public void setAttendees(ArrayList<User> attendees) {
        this.attendees = attendees;
    }

    Boolean addAttendees(Customer _cust)
    {
        this.attendees.add(_cust);
        return true;
    }

    public ArrayList<User> getObservers() {
        return observers;
    }

    public void setObservers(ArrayList<User> observers) {
        this.observers = observers;
    }

    Boolean attachObserver(Customer _cust)
    {
        this.observers.add(_cust);
        return true;
    }

    Boolean detachObserver(Customer _cust)
    {
        Iterator iter = observers.iterator();
        while(iter.hasNext())
        {
            if(iter.next() == _cust)
            {
                observers.remove(_cust);
                return true;
            }
        }
        return false;
    }

    Boolean notifyObserver()
    {
        return true;
    }
}
