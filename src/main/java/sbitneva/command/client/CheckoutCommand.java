package sbitneva.command.client;

import org.apache.log4j.Logger;
import sbitneva.command.factory.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CheckoutCommand implements Command {
    private static Logger log = Logger.getLogger(CheckoutCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}