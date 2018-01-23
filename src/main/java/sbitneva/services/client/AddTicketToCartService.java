package sbitneva.services.client;

public class AddTicketToCartService {
    private static AddTicketToCartService addTicketToCartService = new AddTicketToCartService();
    private AddTicketToCartService(){

    }

    public static AddTicketToCartService getAddTicketToCartService() {
        return addTicketToCartService;
    }


}
