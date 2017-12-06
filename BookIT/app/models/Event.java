package models;

import java.util.Date;

public class Event {

    private Integer eventId;
    private String eventName;
    private Date eventDate;
    private String eventLocation;
    private float perTicketCost;
    private EventManager eventOwner;
    private Integer availableNoOfSeats;
    private Integer totalSales;

    Event(String eventName, Date eventDate, String eventLocation, float perTicketCost, EventManager eventOwner, Integer availableNoOfSeats){

        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventLocation = eventLocation;
        this.perTicketCost = perTicketCost;
        this.eventOwner = eventOwner;
        this.availableNoOfSeats = availableNoOfSeats;
        this.totalSales = 0;
    }
}
