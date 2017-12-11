package models;

import io.ebean.Finder;

import javax.persistence.*;
import java.math.BigInteger;
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

    public static Finder<String, EventManager> find = new Finder<>(EventManager.class);

    public EventManager(String userFirstName, String userLastName, String userEmail, String userPassword, BigInteger phoneNo) {
        super(userFirstName, userLastName, userEmail, userPassword, phoneNo);

    }

    public ArrayList<Event> getCreatedEvents() {
        return createdEvents;
    }

    public void setCreatedEvents(ArrayList<Event> createdEvents) {
        this.createdEvents = createdEvents;
    }

    public boolean getisApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }


}
