package models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigInteger;

@Entity
@DiscriminatorValue("A")
@Table(name = "Admin")
public class Admin extends User{

    public Admin(String userFirstName, String userLastName, String userEmail, String userPassword, BigInteger phoneNo) {
        super(userFirstName, userLastName, userEmail, userPassword, phoneNo);

    }

}
