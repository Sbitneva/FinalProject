package sbitneva.entity;

/**
 * Ticket entity.
 */
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
    private Boolean cart = true;

    /**
     * Ticket default constructor.
     */
    public Ticket() {

    }

    /**
     * Ticket from ID constructor.
     *
     * @param ticketId Ticket ID
     */
    public Ticket(final int ticketId) {
        this.ticketId = ticketId;
    }

    /**
     * Get ticket in cart state.
     *
     * @return Is ticket in cart or not
     */
    public Boolean getCart() {
        return cart;
    }

    /**
     * Set ticket in cart state.
     *
     * @param cart Is ticket in cart or not
     */
    public void setCart(final Boolean cart) {
        this.cart = cart;
    }

    /**
     * Get ticket owner ID.
     *
     * @return Owner ID
     */
    public int getOwnerId() {
        return ownerId;
    }

    /**
     * Set ticket owner ID.
     *
     * @param ownerId Owner ID
     */
    public void setOwnerId(final int ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * Get ticket ID.
     *
     * @return Ticket ID
     */
    public int getTicketId() {
        return ticketId;
    }

    /**
     * Set ticket ID.
     *
     * @param ticketId Ticket ID
     */
    public void setTicketId(final int ticketId) {
        this.ticketId = ticketId;
    }

    /**
     * Get ticket's ship ID.
     *
     * @return Ticket's ship ID
     */
    public int getShipId() {
        return shipId;
    }

    /**
     * Set ticket's ship ID.
     *
     * @param shipId Ticket's ship ID
     */
    public void setShipId(final int shipId) {
        this.shipId = shipId;
    }

    /**
     * Get ticket's ship name.
     *
     * @return Ticket's ship name
     */
    public String getShipName() {
        return shipName;
    }

    /**
     * Set ticket's ship name.
     *
     * @param shipName Ticket's ship name
     */
    public void setShipName(final String shipName) {
        this.shipName = shipName;
    }

    /**
     * Get ticket price.
     *
     * @return Price
     */
    public int getPrice() {
        return price;
    }

    /**
     * Set ticket price.
     *
     * @param price Price
     */
    public void setPrice(final int price) {
        this.price = price;
    }

    /**
     * Get ticket's comfort level.
     *
     * @return Comfort level
     */
    public int getComfortLevel() {
        return comfortLevel;
    }

    /**
     * Set ticket's comfort level.
     *
     * @param comfortLevel Comfort level
     */
    public void setComfortLevel(final int comfortLevel) {
        this.comfortLevel = comfortLevel;
    }

    /**
     * Get ticket's discount.
     *
     * @return Discount value
     */
    public int getDiscount() {
        return discount;
    }

    /**
     * Set ticket's discount.
     *
     * @param discount Discount value
     */
    public void setDiscount(final int discount) {
        this.discount = discount;
    }

    /**
     * Get cruise duration.
     *
     * @return Duration
     */
    public int getCruiseDuration() {
        return cruiseDuration;
    }

    /**
     * Set cruise duration.
     *
     * @param cruiseDuration Duration
     */
    public void setCruiseDuration(final int cruiseDuration) {
        this.cruiseDuration = cruiseDuration;
    }

    /**
     * Get ticket's comfort level name.
     *
     * @return Comfort level name
     */
    public String getComfortLevelName() {
        return comfortLevelName;
    }

    /**
     * Set ticket's comfort level name.
     *
     * @param comfortLevelName Comfort level name
     */
    public void setComfortLevelName(final String comfortLevelName) {
        this.comfortLevelName = comfortLevelName;
    }

    /**
     * Get discounted price.
     *
     * @return Discounted price
     */
    public int getDiscountedPrice() {

        return discountedPrice;
    }

    /**
     * Set discounted price.
     *
     * @param discountedPrice Discounted price
     */
    public void setDiscountedPrice(final int discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    /**
     * Apply discount to a price.
     */
    public void applyDiscountToPrice() {
        if (this.discount > 0) {
            discountedPrice = price - (price / 100 * discount);
        } else {
            this.discountedPrice = price;
        }
    }

    /**
     * Copy constructor.
     *
     * @return Ticket deep copy
     * @throws CloneNotSupportedException If not supported
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Ticket representation.
     *
     * @return Ticket's string description
     */
    @Override
    public String toString() {
        return "Ticket{"
                + "ticketId=" + ticketId
                + ", shipId=" + shipId
                + ", shipName='" + shipName + '\''
                + ", price=" + price
                + ", comfortLevel=" + comfortLevel
                + ", comfortLevelName='" + comfortLevelName + '\''
                + ", discount=" + discount
                + ", cruiseDuration=" + cruiseDuration
                + ", discountedPrice=" + discountedPrice
                + ", ownerId=" + ownerId
                + ", setIsInCart=" + cart
                + '}';
    }
}
