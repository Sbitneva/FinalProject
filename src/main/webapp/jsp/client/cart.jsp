<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>Cruise Company</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="../../css/main.css" type="text/css" rel="stylesheet">
    <link href="../../css/all-backgrounds.css" type="text/css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="../../css/client/client-page.css" >
    <link rel="stylesheet" type="text/css" href="../../css/client/cart.css" >
</head>
<body>
<h1>Your Cart: </h1>
<div class="tables-class">
    <table id="tickets_table">
        <tr>
            <th>Ticket Id</th>
            <th>Ship Name</th>
            <th>Cruise Duration</th>
            <th>Comfort Level</th>
            <th>Price</th>
            <th>Discount</th>
            <th>Discounted Price</th>
        </tr>
        <c:forEach items="${cart.tickets}" var="ticket">
            <tr>
                <td>${ticket.ticketId}</td>
                <td>${ticket.shipName}</td>
                <td>${ticket.cruiseDuration}</td>
                <td>${ticket.comfortLevelName}</td>
                <td>${ticket.price}</td>
                <td>${ticket.discount}</td>
                <td>${ticket.discountedPrice}</td>
            </tr>
        </c:forEach>

    </table>

    <table id="subtotal">
        <tr>
            <th>Subtotal</th>
        </tr>
        <tr>
            <td>${cart.checkout}</td>
        </tr>
        <tr>
            <th>Subtotal with discount</th>
        </tr>
        <tr>
            <td id="subtotal_row"><b>${cart.discountedCheckout}</b></td>
        </tr>
        <tr>
            <td>
                <form id="checkout_form" action="" method="post">
                    <button id="checkout_button" type="submit"> CHECK OUT </button>
                </form>
            </td>
        </tr>
    </table>
</div>
    <c:if test="${deleted > 0}">
        <br> <h1>This tickets are not available anymore and deleted from your cart :</h1>

        <table id="deleted_tickets_table">
            <tr>
                <th>Ticket Id</th>
                <th>Ship Name</th>
                <th>Cruise Duration</th>
                <th>Comfort Level</th>

            </tr>
            <c:forEach items="${cart.deletedTickets}" var="ticket">
                <tr>
                    <td>${ticket.ticketId}</td>
                    <td>${ticket.shipName}</td>
                    <td>${ticket.cruiseDuration}</td>
                    <td>${ticket.comfortLevelName}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</body>