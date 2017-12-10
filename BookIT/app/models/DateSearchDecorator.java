package models;

import java.util.Date;

public class DateSearchDecorator extends SearchDecorator {


    private String eventDate;

    public DateSearchDecorator(Search search, String date){
        super(search);
        this.eventDate = date;
    }


    public String generateQuery() {
        String query = super.generateQuery();
        if(!eventDate.equals(""))
            query = query + "and event_date = \"" + eventDate + "\"";
        return query;

    }
}
