package sbitneva.entity;

public class Ticket implements Cloneable {
    private int ticketId;
    private int shipId;
    private String shipName;
    private int price;
    private int comfortLevel;
    private String comfortLevelName;
    private int discount;
    private int cruiseDuration;
    private int discountedPrice;
    private int ownerId;
    private int cart = 1;

    public Ticket() {

    }

    public Ticket(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getCart() {
        return cart;
    }

    public void setCart(int cart) {
        this.cart = cart;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

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

    public int getDiscountedPrice() {

        return discountedPrice;
    }

    public void setDiscountedPrice(int discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public void setDiscountedPrice() {
        if (this.discount > 0) {
            discountedPrice = price - (price / 100 * discount);
        } else {
            this.discountedPrice = price;
        }
    }


    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", shipId=" + shipId +
                ", shipName='" + shipName + '\'' +
                ", price=" + price +
                ", comfortLevel=" + comfortLevel +
                ", comfortLevelName='" + comfortLevelName + '\'' +
                ", discount=" + discount +
                ", cruiseDuration=" + cruiseDuration +
                ", discountedPrice=" + discountedPrice +
                ", ownerId=" + ownerId +
                ", setIsInCart=" + cart +
                '}';
    }
}
