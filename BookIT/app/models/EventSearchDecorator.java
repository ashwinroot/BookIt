package models;

public class EventSearchDecorator extends SearchDecorator {

    private String eventName;

    public EventSearchDecorator(Search search, String eName){
        super(search);
        this.eventName = eName;
    }

    public String generateQuery() {
        String query = super.generateQuery();
        if(!eventName.equals(""))
            query = query + "and event_name like \"%" + eventName + "%\"";
        return query;
    }
}
