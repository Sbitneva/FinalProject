<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
    <head>
        <title>Cruise Company</title>
        <link href="../../css/main.css" type="text/css" rel="stylesheet">
        <link href="../../css/all-backgrounds.css" type="text/css" rel="stylesheet">
        <link href="../../css/client/client-page.css" type="text/css" rel="stylesheet">
    </head>
    <body>
        <h1 id="hello_string">Hello, ${user.firstName} ${user.lastName} </h1>

        <h2>Your purchased tickets: </h2>

        <table id="tickets_table">
            <tr>
                <th>Ticket Id</th>
                <th>Ship Name</th>
                <th>Cruise Duration</th>
                <th>Comfort Level</th>
            </tr>
            <c:forEach items="${user.tickets}" var="ticket">
                <tr>
                    <td>${ticket.ticketId}</td>
                    <td>${ticket.shipName}</td>
                    <td>${ticket.cruiseDuration}</td>
                    <form action="CruiseServlet?command=comfortInfo&comfort_id=${ticket.comfortLevel}" method="post">
                        <td>${ticket.comfortLevel}</td>
                    </form>
                </tr>
            </c:forEach>
        </table>
        <form id="buy_ticket_form" action="/CruiseServlet?command=buyTicket" method="post">
            <button id="buy_ticket_button" type="submit"> Buy new Ticket</button>
        </form>

        <h2>Your purchased excursions: </h2>

        <table id="excursions_table">
            <tr>
                <th>Excursion Name</th>
                <th>Cruise Ship Name</th>
                <th>Port</th>
            </tr>
            <c:forEach items="${user.excursions}" var="excursion">
                <tr>
                    <td>${excursion.excursionName}</td>
                    <td>${excursion.shipName}</td>
                    <td>${excursion.portName}</td>
                </tr>
            </c:forEach>
        </table>

        <form id="buy_excursion_form" action="/CruiseServlet?command=buyExcursion" method="post">
            <button id="buy_excursion_button" type="submit"> Buy new Excursion</button>
        </form>
    </body>
</html>