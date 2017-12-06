package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

@Entity
public class Event {

    @Id
    private Integer eventId;
    private String eventName;
    private Date eventDate;
    private String eventLocation;
    private float perTicketCost;
    private EventManager eventOwner;
    private Integer availableNoOfSeats;
    private Integer totalSales;
    private ArrayList<Customer> attendees;
    private ArrayList<Customer> observers;

    public ArrayList<Customer> getObservers() {
        return observers;
    }

    public void setObservers(ArrayList<Customer> observers) {
        this.observers = observers;
    }

    Event(String eventName, Date eventDate, String eventLocation, float perTicketCost, EventManager eventOwner, Integer availableNoOfSeats){

        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventLocation = eventLocation;
        this.perTicketCost = perTicketCost;
        this.eventOwner = eventOwner;
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

    public EventManager getEventOwner() {
        return eventOwner;
    }

    public void setEventOwner(EventManager eventOwner) {
        this.eventOwner = eventOwner;
    }

    public Integer getAvailableNoOfSeats() {
        return availableNoOfSeats;
    }

    public void setAvailableNoOfSeats(Integer availableNoOfSeats) {
        this.availableNoOfSeats = availableNoOfSeats;
    }

    public Integer getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(Integer totalSales) {
        this.totalSales = totalSales;
    }

    public ArrayList<Customer> getAttendees() {
        return attendees;
    }

    public void setAttendees(ArrayList<Customer> attendees) {
        this.attendees = attendees;
    }

    Boolean addAttendees(Customer _cust)
    {
        this.attendees.add(_cust);
        return true;
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
