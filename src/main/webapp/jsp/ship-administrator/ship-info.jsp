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
<h1>${ship.shipName}</h1>
<form id="staff_button" action="?command=shipAdmin&action=staff&shipId=${ship.shipId}" type="submit"
      method="post">
    <button id="staff" type="submit">
        Show ships staff
    </button>
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
                <form id="show_services"
                      action="?command=shipAdmin&action=services&comfortId=${ticket.comfortLevel}"
                      method="post">
                    <button id="show" type="submit">Show services</button>
                </form>
            </td>
            <td>
                <form id="apply_discount"
                      action="?command=shipAdmin&action=apply&shipId=${ship.shipId}&ticketId=${ticket.ticketId}"
                      method="post">
                    <input type="text" name="discount" contenteditable="true" value="${ticket.discount}">
                    <button id="apply" type="submit">Apply discount</button>
                </form>
            </td>
            <td>
                    ${ticket.price}
            <td>
        </tr>
    </c:forEach>
</table>
</body>
</html>