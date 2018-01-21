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

<form id="staff_button" action="/Cruise?command=getStaff&shipId=${ship.shipId}" type="submit"
      method="post">
    <button id="staff" type="submit">
        Show ships staff
    </button>
</form>
<b>Current page = ${page}</b>

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
                      action="/Cruise?command=getServices&comfortId=${ticket.comfortLevel}"
                      method="post">
                    <button id="show" type="submit">Show services</button>
                </form>
            </td>
            <td>
                <form id="apply_discount"
                      action="/Cruise?command=setDiscount&ticketId=${ticket.ticketId}&page=${page}"
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
    <br>
    </table>
        <table id="pagination_table">
            <tr align="center">
                <c:forEach var = "i" begin="1" end="${pages}">
                    <form id="pagination_form" action="/Cruise?command=showShip" method="post" >
                    <td>
                        <button name="page" type="submit" type="text" value="${i}">${i}</button>
                    </td>
                    </form>
                </c:forEach>
            </tr>
        </table>
    </body>
</html>