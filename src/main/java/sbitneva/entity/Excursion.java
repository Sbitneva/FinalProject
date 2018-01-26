package sbitneva.entity;

public class Excursion {

    private int excursionId;
    private String excursionName;
    private int shipId;
    private String shipName;
    private String portName;
    private int price;
    private int ticketId;

    public Excursion() {

    }

    public Excursion(int excursionId) {
        this.excursionId = excursionId;
    }


    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getExcursionId() {
        return excursionId;
    }

    public void setExcursionId(int excursionId) {
        this.excursionId = excursionId;
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

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getExcursionName() {
        return excursionName;
    }

    public void setExcursionName(String excursionName) {
        this.excursionName = excursionName;
    }

    @Override
    public String toString() {
        return "Excursion{" +
                "excursionId=" + excursionId +
                ", shipId=" + shipId +
                ", shipName='" + shipName + '\'' +
                ", portName='" + portName + '\'' +
                ", price=" + price +
                '}';
    }
}
