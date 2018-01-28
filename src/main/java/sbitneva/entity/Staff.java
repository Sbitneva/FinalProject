package sbitneva.entity;

/**
 * Staff entity.
 */
public class Staff {

    private int staffId;
    private String firstName;
    private String lastName;
    private String position;
    private int shipId;
    private int shipName;

    /**
     * Get staff ID.
     *
     * @return Staff ID
     */
    public int getStaffId() {
        return staffId;
    }

    /**
     * Set staff ID.
     *
     * @param staffId Staff ID
     */
    public void setStaffId(final int staffId) {
        this.staffId = staffId;
    }

    /**
     * Get staff first name.
     *
     * @return First name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set staff first name.
     *
     * @param firstName First name
     */
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get staff last name.
     *
     * @return Last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set staff last name.
     *
     * @param lastName Last name
     */
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get staff position.
     *
     * @return Position
     */
    public String getPosition() {
        return position;
    }

    /**
     * Set staff position.
     *
     * @param position Position
     */
    public void setPosition(final String position) {
        this.position = position;
    }

    /**
     * Get staff's ship ID.
     *
     * @return Ship ID
     */
    public int getShipId() {
        return shipId;
    }

    /**
     * Set staff's ship ID.
     *
     * @param shipId Ship ID
     */
    public void setShipId(final int shipId) {
        this.shipId = shipId;
    }

    /**
     * Get staff's ship name.
     *
     * @return Ship name
     */
    public int getShipName() {
        return shipName;
    }

    /**
     * Set staff's ship name.
     *
     * @param shipName Ship name
     */
    public void setShipName(final int shipName) {
        this.shipName = shipName;
    }

    /**
     * Staff representation.
     *
     * @return Staff's string description
     */
    @Override
    public String toString() {
        return "Staff{"
                + "staffId=" + staffId
                + ", firstName='" + firstName + '\''
                + ", lastName='" + lastName + '\''
                + ", position='" + position + '\''
                + ", shipId=" + shipId
                + ", shipName=" + shipName
                + '}';
    }
}
