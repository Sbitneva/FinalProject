package sbitneva.dao;

public class DaoFactory {

    private static UserDao userDao = new UserDao();
    private static ExcursionDao excursionDao = new ExcursionDao();
    private static ShipDao shipDao = new ShipDao();
    private static PortDao portDao = new PortDao();
    private static StaffDao staffDao = new StaffDao();
    private static SharedDao sharedDao = new SharedDao();
    private static TicketDao ticketDao = new TicketDao();
    private static DaoFactory daoFactory = new DaoFactory();

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

    public static SharedDao getSharedtDao() {
        return sharedDao;
    }

    public static TicketDao getTicketDao() {
        return ticketDao;
    }
}
