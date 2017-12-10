package models;


public abstract class Search {

    private String query;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public abstract String generateQuery();
}
