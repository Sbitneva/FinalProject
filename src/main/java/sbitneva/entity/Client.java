package sbitneva.entity;

import java.util.ArrayList;

public class Client {
    private int clientId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int shipId = 0;
    private ArrayList<Ticket> tickets = new ArrayList<>();
    private ArrayList<Excursion> excursions = new ArrayList<>();

    public ArrayList<Excursion> getExcursions() {
        return excursions;
    }

    public void setExcursions(ArrayList<Excursion> excursions) {
        this.excursions = excursions;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getShipId() {
        return shipId;
    }

    public void setShipId(int shipId) {
        this.shipId = shipId;
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(ArrayList<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId=" + clientId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", shipId='" + shipId + '\'' +
                '}';
    }
}
