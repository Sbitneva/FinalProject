<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<html>
    <head>
        <title>Cruise Company</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="../../css/main.css" type="text/css" rel="stylesheet">
        <link href="../../css/all-backgrounds.css" type="text/css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="../../css/client/client-page.css" >
    </head>
    <body>

        <div class="functions-class">
            <h1 id="hello_string" class="functions-class">Hello, ${client.firstName} ${client.lastName} </h1>
            <form id="show_cart" class="functions-class" action="/Cruise?command=cart" method="post">
                <button id="show_cart_button" type="submit">Tickets Cart</button>
            </form>
            <form id="logout" class="functions-class"action="/Cruise?command=logout" method="post">
                <button id="logout_button" type="submit">logout</button>
            </form>
        </div>
        <form id="buy_ticket_form" action="/Cruise?command=getCruises" method="post">
            <button id="buy_ticket_button" type="submit"> Buy new Ticket</button>
        </form>

        <h2>Your purchased tickets: </h2>

        <table id="tickets_table">
            <tr>
                <th>Ticket Id</th>
                <th>Ship Name</th>
                <th>Cruise Duration</th>
                <th>Comfort Level</th>
                <th>Additional properties</th>
            </tr>
            <c:forEach items="${client.tickets}" var="ticket">
                <tr>
                    <td>${ticket.ticketId}</td>
                    <td>${ticket.shipName}</td>
                    <td>${ticket.cruiseDuration}</td>
                    <td> <form id="show_services"
                               action="/Cruise?command=getServices&comfortId=${ticket.comfortLevel}"
                               method="post">
                            ${ticket.comfortLevelName}
                        <button id="show" type="submit">Show services</button>
                    </form>
                    </td>
                    <td>
                        <form id="buy_excursion_form"
                              action="/Cruise?command=getExcursions&ticketId=${ticket.ticketId}"
                              method="post">
                            <button id="buy_excursion_button" type="submit"> Buy new Excursion</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <h2>Your purchased excursions: </h2>

        <table id="excursions_table">
            <tr>
                <th>Ticket Id</th>
                <th>Excursion Name</th>
                <th>Cruise Ship Name</th>
                <th>Port</th>
            </tr>
            <c:forEach items="${client.excursions}" var="excursion">
                <tr>
                    <td>${excursion.ticketId}</td>
                    <td>${excursion.excursionName}</td>
                    <td>${excursion.shipName}</td>
                    <td>${excursion.portName}</td>
                </tr>
            </c:forEach>
        </table>

    </body>
</html>