package models;

public class SimpleSearch extends Search {


    public String generateQuery() {

        return "select event_id from event where event_id > 0 ";
    }
}
