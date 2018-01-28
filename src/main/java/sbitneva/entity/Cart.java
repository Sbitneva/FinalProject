package sbitneva.entity;

import java.util.ArrayList;

/**
 * Cart management entity.
 */
public class Cart {

    private ArrayList<Ticket> tickets = new ArrayList<>();
    private ArrayList<Ticket> deletedTickets = new ArrayList<>();
    private int discountedCheckout = 0;
    private int checkout = 0;

    /**
     * Get all tickets.
     *
     * @return List of tickets
     */
    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    /**
     * Set tickets.
     *
     * @param tickets List of tickets
     */
    public void setTickets(final ArrayList<Ticket> tickets) {
        this.tickets = tickets;
    }

    /**
     * Add ticket with ID.
     *
     * @param id Ticket ID
     */
    public void addTicketWithId(final int id) {
        this.tickets.add(new Ticket(id));
    }

    /**
     * Get checkout including discount.
     *
     * @return Price
     */
    public int getDiscountedCheckout() {
        return discountedCheckout;
    }

    /**
     * Set checkout including discount.
     *
     * @param discountedCheckout Price with discount
     */
    public void setDiscountedCheckout(final int discountedCheckout) {
        this.discountedCheckout = discountedCheckout;
    }

    /**
     * Get checkout.
     *
     * @return Price
     */
    public int getCheckout() {
        return checkout;
    }

    /**
     * Set checkout.
     *
     * @param checkout Checkout price
     */
    public void setCheckout(final int checkout) {
        this.checkout = checkout;
    }

    /**
     * Add ticket to deleted.
     *
     * @param ticket Ticket
     */
    public void addDeletedTicket(final Ticket ticket) {
        this.deletedTickets.add(ticket);
    }


    /**
     * Get deleted tickets from the cart.
     *
     * @return Deleted tickets
     */
    public ArrayList<Ticket> getDeletedTickets() {
        return deletedTickets;
    }

    /**
     * Set deleted tickets.
     *
     * @param deletedTickets Deleted tickets
     */
    public void setDeletedTickets(final ArrayList<Ticket> deletedTickets) {
        this.deletedTickets = deletedTickets;
    }
}
