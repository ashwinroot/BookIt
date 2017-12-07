package models;

import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class WishList extends Model {
    @Id
    private Integer wishID;
    private String customerEmail;
    private Integer eventID;

    public WishList(String customerEmail, Integer eventID){
        this.customerEmail = customerEmail;
        this.eventID = eventID;
    }

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
}
