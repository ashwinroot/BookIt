package models;

import javax.persistence.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

@Entity
@DiscriminatorValue("E")
@Table(name = "EventManager")

public class EventManager extends User {

    @OneToMany
    private ArrayList<Event> createdEvents;
    private boolean isApproved;


    public ArrayList<Event> getCreatedEvents() {
        return createdEvents;
    }

    public void setCreatedEvents(ArrayList<Event> createdEvents) {
        this.createdEvents = createdEvents;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    /*
    Event createEvent(String _eventName,String _eventLocation, Date _Date, float _ticketCost, int _availSeats)
    {
        Event event = new Event(_eventName,_Date,_eventLocation,_ticketCost,this,_availSeats);
        return event;
    }
    */
}
