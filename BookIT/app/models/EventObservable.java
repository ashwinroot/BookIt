package models;
import play.libs.mailer.MailerClient;
public interface EventObservable {

    public void addObserver(String user);

    public void removeObserver(String user);

    public void notifyObserver(MailerClient mailerClient);
}
