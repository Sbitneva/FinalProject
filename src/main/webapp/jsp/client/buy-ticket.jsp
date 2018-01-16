<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
<head>
    <title>Cruise Company</title>
    <link href="../../css/main.css" type="text/css" rel="stylesheet">
    <link href="../../css/all-backgrounds.css" type="text/css" rel="stylesheet">
    <link href="../../css/client/buy-ticket.css" type="text/css" rel="stylesheet">
</head>
<body>
    <h2>${ship.shipName}</h2>
    <form id="ship_form">
        <b>Ports:</b>
        <c:forEach items="${ship.ports}" var="port">
            <br>${port.portName}
        </c:forEach>
        <br>
            <b>Tickets amount: ${fn:length(ship.tickets)}</b>
        <br>
        <br>
            <b>Cruise duration: ${ship.cruiseDuration} </b>
        <br>
    </form>

    <table id="ship_table">
        <tr>
            <th>Ticket Id</th>
            <th>Comfort Level</th>
            <th>Discount %</th>
            <th>Price</th>
            <th>Check to Buy</th>

        </tr>
        <c:forEach items="${ship.tickets}" var="ticket">
            <tr>
                <td style="width:5%;">${ticket.ticketId}</td>
                <td> <form id="show_services"
                          action="CruiseServlet?command=shipAdmin&action=services&comfortId=${ticket.comfortLevel}"
                          method="post">
                        ${ticket.comfortLevelName}
                        <button id="show" type="submit">Show services</button>
                    </form>
                </td>
                <td style="width:5%;">${ticket.discount}</td>
                <td>${ticket.price}</td>
                <td style="width:5%;">
                    <form id="buy_excursion_form"
                          action="/CruiseServlet?command=buyTicket&action=buyTicket&ticketId=${ticket.ticketId}"
                          method="post">
                        <button id="buy_excursion_button" type="submit">Buy</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

</body>
</html>