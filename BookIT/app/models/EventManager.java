package models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

@Entity
@DiscriminatorValue("E")
@Table(name = "EventManager")
public class EventManager extends User {
    private ArrayList<Event> createdEvents;
    private boolean isApproved;

/*

    EventManager(){
        isApproved = false;
    }
    public boolean createEvent(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        String dateInString = "31-08-1982";
        Date date = null;
        try {
            date = sdf.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Event E = new Event("NFL", date, "Denver", 100,
                this, 100);

        return true;
    }

*/
/*
    public boolean updateEvent(){

    }

    public boolean deleteEvent(){

    }

    public void viewEvent(){

    }*/
}
