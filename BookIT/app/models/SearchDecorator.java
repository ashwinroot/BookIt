package models;

public abstract class SearchDecorator extends Search{

    private Search baseSearch;

    public SearchDecorator(Search search){

        baseSearch = search;

    }

    public String generateQuery() {

        return baseSearch.generateQuery();
    }
}
