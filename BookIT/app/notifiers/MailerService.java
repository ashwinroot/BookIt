package notifiers;

import models.Event;
import models.Ticket;
import models.User;
import play.libs.mailer.Email;
import play.libs.mailer.MailerClient;
import javax.inject.Inject;
//import javax.xml.transform.Result;
import play.mvc.Result;
import java.io.File;
import java.io.FileWriter;

import org.apache.commons.mail.EmailAttachment;
import play.mvc.Controller;

public class MailerService {
    MailerClient mailerClient;

    public MailerService(MailerClient mailerClient){
        this.mailerClient = mailerClient;
    }

    public int sendEmail(MailerClient mclient){

        this.mailerClient = mclient;
        String cid = "1234";
        //try
        //{
            Email email = new Email();
            email.setSubject("Simple email");
            email.setFrom("bookitcsci5448@gmail.com");
            email.addTo("ashwinoscar@gmail.com");
            email.setBodyText("A text message -- sample mail body");
            //email.setBodyHtml("<html><body><p>An <b>html</b> message with cid <img src=\"cid:" + cid + "\"></p></body></html>");
            //if (email==null)
             //   return 0;

        try{

            FileWriter fw=new FileWriter("testout.txt");
            fw.write("Welcome to javaTpoint. "+email.getBodyText()+" body");
            fw.flush();
            fw.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        if (mailerClient==null)
        {
            try{

                FileWriter fw=new FileWriter("mailclientnull.txt");
                fw.write("Welcome to javaTpoint. "+email.getBodyText()+" body");
                fw.flush();
                fw.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        mailerClient.send(email);
            return 1;
        //}
        //catch (Exception e)
        //{
        //    e.printStackTrace();
        //}

    }

    public void mailPassword(String mailId)
    {
        Email email = new Email();
        User user = User.find.byId(mailId);
        email.setSubject("Password reset mail");
        email.setFrom("bookitcsci5448@gmail.com");
        email.addTo(user.getUserEmail());
        email.setBodyText("Your password is: "+user.getUserPassword());
        mailerClient.send(email);
    }

    public void verifyUser(User user)
    {
        Email email = new Email();
        email.setSubject("Account verification");
        email.setFrom("bookitcsci5448@gmail.com");
        email.addTo(user.getUserEmail());
        email.setBodyText("Your mail-id has been used to create an account in BookIt. If this action is not performed by you. Please contact BookIt.");
        mailerClient.send(email);
    }

    public void bookingConfirmation(Ticket t, Event event)
    {
        Email email = new Email();
        email.setSubject("Booking confirmation mail");
        email.setFrom("bookitcsci5448@gmail.com");
        email.addTo(t.getCustomerMail());
        email.setBodyText("Your booking for the event" +" "+event.getEventName()+" is confirmed. Ticket id: "+t.getTicketId());
        mailerClient.send(email);
    }

    public void eventUpdateNotification(Integer eventId, String userMail)
    {
        Email email = new Email();
        Event event = Event.find.byId(new Integer(eventId).toString());
        User user = User.find.byId(userMail);
        email.setSubject("Event update notification");
        email.setFrom("bookitcsci5448@gmail.com");
        //email.addTo(user.getUserEmail());
        email.addTo(user.getUserEmail());
        email.setBodyText("Event details has been changed.");
        email.setBodyHtml("<html><body> <h2>Event Details</h2>" +
                "        <ul>" +
                "            <li><strong>Event: </strong>"+ event.getEventName() +"</li>" +
                "            <li><strong>Event Location: </strong>"+ event.getEventLocation() +"</li>" +
                "            <li><strong>Event Date: </strong>"+ event.getEventDate() +"</li>" +
                "        </ul>" +
                "        <ul></body></html>");
        //return forbidden("mail: "+user.getUserEmail()+" "+event.getEventName());
        mailerClient.send(email);
    }

    public void cancelConfirmation(Event event, User user, int ticketId)
    {
        Email email = new Email();
        email.setSubject("Ticket cancellation confirmation");
        email.setFrom("bookitcsci5448@gmail.com");
        email.addTo(user.getUserEmail());
        email.setBodyText("Your event ticket has been cancelled");
        email.setBodyHtml("<html><body> <h2>Ticket Details</h2>" +
                "        <ul>" +
                "            <li><strong>Ticket Id: </strong>"+ ticketId +"</li>" +
                "            <li><strong>Event: </strong>"+ event.getEventName() +"</li>" +
                "            <li><strong>Event Location: </strong>"+ event.getEventLocation() +"</li>" +
                "            <li><strong>Event Date: </strong>"+ event.getEventDate() +"</li>" +
                "        </ul>" +
                "        <ul></body></html>");
        mailerClient.send(email);
    }
}