<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Cruise Company</title>
    <link href="../../css/main.css" type="text/css" rel="stylesheet">
    <link href="../../css/all-backgrounds.css" type="text/css" rel="stylesheet">
    <link href="../../css/ship-administrator/ship-info.css" type="text/css" rel="stylesheet">
</head>
<body>
    <h2>${ship.shipName}</h2>
    <form id="ship_form">
        <!--
                <b>Ports:</b>
                <c:forEach items="${ship.ports}" var="port">
                    <br>${port.portName}
                </c:forEach>
                <br><b>Cruise duration: ${ship.cruiseDuration} </b>
                <br>
                -->
    </form>
    <table id="tickets_table">
        <tr>
            <th>Ticket Id</th>
            <th>Comfort Level</th>
            <th>Discount %</th>
            <th>Price</th>
        </tr>
        <c:forEach items="${ship.tickets}" var="ticket">
            <tr>
                <td>${ticket.ticketId}</td>
                <td>${ticket.comfortLevelName}
                    <form id="show_services" action="" method="post">
                        <button id="show" type="submit">Show services</button>
                    </form>
                </td>
                <td>
                    <form id="apply_discount" action="" method="post">
                        <input type="text" contenteditable="true" value="${ticket.discount}">
                        <button id="apply" type="submit">Apply discount</button>
                    </form>
                </td>
                <td>${ticket.price}<td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>