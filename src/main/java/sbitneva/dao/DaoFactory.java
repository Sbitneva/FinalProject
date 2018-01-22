package sbitneva.dao;

public class DaoFactory {

    private static UserDao userDao = new UserDao();
    private static ExcursionDao excursionDao = new ExcursionDao();
    private static ShipDao shipDao = new ShipDao();
    private static PortDao portDao = new PortDao();
    private static StaffDao staffDao = new StaffDao();
    private static TicketDao ticketDao = new TicketDao();
    private static DaoFactory daoFactory = new DaoFactory();
    private static TicketsExcursionsDao ticketsExcursionsDao = new TicketsExcursionsDao();
    private static ComfortLevelDao comfortLevelDao = new ComfortLevelDao();
    private static BasicDao basicDao = new BasicDao();
    private static CartDao cartDao = new CartDao();

    public static CartDao getCartDao() {
        return cartDao;
    }

    private DaoFactory() {

    }

    public static DaoFactory getInstance() {
        return daoFactory;
    }

    public static UserDao getUserDao() {
        return userDao;
    }

    public static ExcursionDao getExcursionDao() {
        return excursionDao;
    }

    public static ShipDao getShipDao() {
        return shipDao;
    }

    public static PortDao getPortDao() {
        return portDao;
    }

    public static StaffDao getStaffDao() {
        return staffDao;
    }

    public static TicketDao getTicketDao() {
        return ticketDao;
    }

    public static TicketsExcursionsDao getTicketsExcursionsDao() {
        return ticketsExcursionsDao;
    }

    public static ComfortLevelDao getComfortLevelDao() {
        return comfortLevelDao;
    }

    public static BasicDao getBasicDao() {
        return basicDao;
    }
}
