package sbitneva.entity;

import java.util.ArrayList;

/**
 * Client entity.
 */
public class Client {

    private int clientId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int shipId = 0;
    private ArrayList<Ticket> tickets = new ArrayList<>();
    private ArrayList<Excursion> excursions = new ArrayList<>();

    /**
     * Get client's additional excursions list.
     *
     * @return Client excursions list
     */
    public ArrayList<Excursion> getExcursions() {
        return excursions;
    }

    /**
     * Set client's additional excursions list.
     *
     * @param excursions Client excursions list
     */
    public void setExcursions(final ArrayList<Excursion> excursions) {
        this.excursions = excursions;
    }

    /**
     * Get client's ID.
     *
     * @return Client ID
     */
    public int getClientId() {
        return clientId;
    }

    /**
     * Set client's ID.
     *
     * @param clientId Client ID
     */
    public void setClientId(final int clientId) {
        this.clientId = clientId;
    }

    /**
     * Get client's first name.
     *
     * @return First name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set client's first name.
     *
     * @param firstName First name
     */
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get client's last name.
     *
     * @return Last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set client's last name.
     *
     * @param lastName Last name
     */
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get client's email.
     *
     * @return E-mail
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set client's email.
     *
     * @param email E-mail
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * Get client's password.
     *
     * @return Password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set client's password.
     *
     * @param password Password
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * Get client's ship ID.
     *
     * @return Client's ship ID
     */
    public int getShipId() {
        return shipId;
    }

    /**
     * Set client's ship ID.
     *
     * @param shipId Client's ship ID
     */
    public void setShipId(final int shipId) {
        this.shipId = shipId;
    }

    /**
     * Get client's tickets.
     *
     * @return Tickets list
     */
    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    /**
     * Set client's tickets.
     *
     * @param tickets Tickets list
     */
    public void setTickets(final ArrayList<Ticket> tickets) {
        this.tickets = tickets;
    }

    /**
     * Client representation.
     *
     * @return Client entity description string
     */
    @Override
    public String toString() {
        return "Client{"
                + "clientId=" + clientId
                + ", firstName='" + firstName + '\''
                + ", lastName='" + lastName + '\''
                + ", email='" + email + '\''
                + ", password='" + password + '\''
                + ", shipId='" + shipId + '\''
                + '}';
    }
}
