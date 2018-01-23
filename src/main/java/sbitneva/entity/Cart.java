package sbitneva.entity;

import java.util.ArrayList;

public class Cart {

    ArrayList<Ticket> tickets = new ArrayList<>();
    ArrayList<Ticket> deletedTickets = new ArrayList<>();
    int discountedCheckout = 0;
    int checkout = 0;

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(ArrayList<Ticket> tickets) {
        this.tickets = tickets;
    }

    public void addTicketWithId(int id) {
        this.tickets.add(new Ticket(id));
    }

    public int getDiscountedCheckout() {
        return discountedCheckout;
    }

    public void setDiscountedCheckout(int discountedCheckout) {
        this.discountedCheckout = discountedCheckout;
    }

    public int getCheckout() {
        return checkout;
    }

    public void setCheckout(int checkout) {
        this.checkout = checkout;
    }

    public void addDeletedTicket(Ticket ticket) {
        this.deletedTickets.add(ticket);
    }

    public ArrayList<Ticket> getDeletedTickets() {
        return deletedTickets;
    }

    public void setDeletedTickets(ArrayList<Ticket> deletedTickets) {
        this.deletedTickets = deletedTickets;
    }
}
