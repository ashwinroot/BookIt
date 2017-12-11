package models;

import controllers.CustomerController;
import controllers.UserController;
import io.ebean.*;

import javax.persistence.*;
import java.io.FileWriter;
import java.math.BigInteger;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name="USER_TYPE")
public class User extends Model implements EventObserver{


    private Integer userID;
    private String userFirstName;



    private String userLastName;
    @Id
    private String userEmail;
    private String userPassword;
    private BigInteger phoneNo;

    public static Finder<String, User> find = new Finder<>(User.class);

    public User(String userFirstName, String userLastName, String userEmail, String userPassword, BigInteger phoneNo) {
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.phoneNo = phoneNo;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public BigInteger getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(BigInteger phoneNo) {
        this.phoneNo = phoneNo;
    }



    @Transient
    public String getDecriminatorValue() {
        return this.getClass().getAnnotation(DiscriminatorValue.class).value();
    }

    /*
    public void mailUser(){

        UserController ccon = new UserController();
        try{

            FileWriter fw=new FileWriter("user_mailuser.txt");
            fw.write("Welcome to javaTpoint. "+this.getUserEmail()+" "+this.getUserFirstName()  );
            fw.flush();
            fw.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        ccon.sendMail(this.getUserEmail());

    }
    */

    public void updateUser(){

        //CustomerController ccon = new CustomerController();


        //mailUser();

    }



}
