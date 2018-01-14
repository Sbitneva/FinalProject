package sbitneva.entity;

public class Ticket {
    private int ticketId;
    private int shipId;
    private String shipName;
    private int price;
    private int comfortLevel;
    private String comfortLevelName;
    private int discount;
    private int cruiseDuration;

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getShipId() {
        return shipId;
    }

    public void setShipId(int shipId) {
        this.shipId = shipId;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getComfortLevel() {
        return comfortLevel;
    }

    public void setComfortLevel(int comfortLevel) {
        this.comfortLevel = comfortLevel;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getCruiseDuration() {
        return cruiseDuration;
    }

    public void setCruiseDuration(int cruiseDuration) {
        this.cruiseDuration = cruiseDuration;
    }

    public String getComfortLevelName() {
        return comfortLevelName;
    }

    public void setComfortLevelName(String comfortLevelName) {
        this.comfortLevelName = comfortLevelName;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", shipId=" + shipId +
                ", shipName='" + shipName + '\'' +
                ", price=" + price +
                ", comfortLevel=" + comfortLevel +
                ", discount=" + discount +
                ", cruiseDuration=" + cruiseDuration +
                '}';
    }
}
