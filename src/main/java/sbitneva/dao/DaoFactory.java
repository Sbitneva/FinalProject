package sbitneva.dao;

/**
 * DAO factory singleton.
 */
public final class DaoFactory {

    private static UserDao userDao = new UserDao();
    private static ExcursionDao excursionDao = new ExcursionDao();
    private static ShipDao shipDao = new ShipDao();
    private static PortDao portDao = new PortDao();
    private static StaffDao staffDao = new StaffDao();
    private static TicketDao ticketDao = new TicketDao();
    private static DaoFactory daoFactory = new DaoFactory();
    private static TicketsExcursionsDao ticketsExcursionsDao = new TicketsExcursionsDao();
    private static ComfortLevelDao comfortLevelDao = new ComfortLevelDao();
    private static CartDao cartDao = new CartDao();

    private DaoFactory() {

    }

    /**
     * Get cart DAO.
     *
     * @return Cart DAO
     */
    public static CartDao getCartDao() {
        return cartDao;
    }

    /**
     * Get DAO factory instance.
     *
     * @return DAO factory
     */
    public static DaoFactory getInstance() {
        return daoFactory;
    }

    /**
     * Get user DAO.
     *
     * @return User DAO
     */
    public static UserDao getUserDao() {
        return userDao;
    }

    /**
     * Get excursion DAO.
     *
     * @return Excursion DAO
     */
    public static ExcursionDao getExcursionDao() {
        return excursionDao;
    }

    /**
     * Get ship DAO.
     *
     * @return Ship DAO
     */
    public static ShipDao getShipDao() {
        return shipDao;
    }

    /**
     * Get port DAO.
     *
     * @return Port DAO
     */
    public static PortDao getPortDao() {
        return portDao;
    }

    /**
     * Get staff DAO.
     *
     * @return Staff DAO
     */
    public static StaffDao getStaffDao() {
        return staffDao;
    }

    /**
     * Get ticket DAO.
     *
     * @return Ticket DAO
     */
    public static TicketDao getTicketDao() {
        return ticketDao;
    }

    /**
     * Get tickets excursions DAO.
     *
     * @return Tickets excursions DAO
     */
    public static TicketsExcursionsDao getTicketsExcursionsDao() {
        return ticketsExcursionsDao;
    }

    /**
     * Get comfort level DAO.
     *
     * @return Comfort level DAO
     */
    public static ComfortLevelDao getComfortLevelDao() {
        return comfortLevelDao;
    }

}
