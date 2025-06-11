package com.atm.buenas_practicas_java.DTO;

import java.util.List;

public class SearchResponse {

    private List<?> users;
    private List<?> concerts;
    private List<?> places;

    public SearchResponse(List<?> users, List<?> concerts, List<?> places) {
        this.users = users;
        this.concerts = concerts;
        this.places = places;
    }

    public List<?> getUsers() {
        return users;
    }

    public void setUsers(List<?> users) {
        this.users = users;
    }

    public List<?> getConcerts() {
        return concerts;
    }

    public void setConcerts(List<?> concerts) {
        this.concerts = concerts;
    }

    public List<?> getPlaces() {
        return places;
    }

    public void setPlaces(List<?> places) {
        this.places = places;
    }
}
