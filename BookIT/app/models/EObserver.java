package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EObserver extends Model {


    @Id
    private Integer ID;
    private String customerEmail;
    private Integer eventID;

    public EObserver(String customerEmail, Integer eventID){
        this.customerEmail = customerEmail;
        this.eventID = eventID;
    }

    public static Finder<String, EObserver> find = new Finder<>(EObserver.class);

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Integer getEventID() {
        return eventID;
    }

    public void setEventID(Integer eventID) {
        this.eventID = eventID;
    }

    public Integer getID() {
        return ID;
    }
}
