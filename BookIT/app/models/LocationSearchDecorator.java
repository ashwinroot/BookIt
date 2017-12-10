package models;

public class LocationSearchDecorator extends SearchDecorator{

    private String eventLocation;

    public LocationSearchDecorator(Search search, String location){
        super(search);
        this.eventLocation = location;
    }


    public String generateQuery() {
        String query = super.generateQuery();
        if(!eventLocation.equals(""))
            query = query + "and event_location like \"%" + eventLocation + "%\"";
        return query;

    }
}
