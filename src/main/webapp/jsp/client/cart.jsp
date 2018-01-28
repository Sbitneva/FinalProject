<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="MessagesBundle"/>

<html>
<head>
    <title>Cruise Company</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="../../css/main.css" type="text/css" rel="stylesheet">
    <link href="../../css/all-backgrounds.css" type="text/css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="../../css/client/client-page.css">
    <link rel="stylesheet" type="text/css" href="../../css/client/cart.css">
    <link href="../../css/client/tickets.css" type="text/css" rel="stylesheet">
</head>
<body>
<table id="menu-table">
    <tr>
        <td><h1><fmt:message key="client.cart.tickets_table.title"/></h1></td>
        <td>
            <form id="main_page_button" action="/Cruise?command=client" method="post">
                <button id="continue_shopping_button" type="submit">GO TO ACCOUNT</button>
            </form>
        </td>

        <td>
            <form id="buy_ticket_form" action="/Cruise?command=getCruises" method="post">
                <button id="buy_ticket_other_button" type="submit"><fmt:message
                        key="client.tickets.menu-table.buy_ticket_form.buy_ticket_button"/></button>
            </form>
        </td>

        <td id="logout_td">
            <form id="logout" class="functions-class" action="/Cruise?command=logout" method="post">
                <button id="logout_button" type="submit"><fmt:message
                        key="client.tickets.menu-table.logout.logout_button"/></button>
            </form>
        </td>
    </tr>
</table>
<h1></h1>

<div class="tables-class">
    <table id="tickets_table">
        <tr>
            <th><fmt:message key="client.cart.tickets_table.ticket_id"/></th>
            <th><fmt:message key="client.cart.tickets_table.ship_name"/></th>
            <th><fmt:message key="client.cart.tickets_table.cruise_duration"/></th>
            <th><fmt:message key="client.cart.tickets_table.comfort_level"/></th>
            <th><fmt:message key="client.cart.tickets_table.price"/></th>
            <th><fmt:message key="client.cart.tickets_table.discount"/></th>
            <th><fmt:message key="client.cart.tickets_table.discounted_price"/></th>
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
            <th><fmt:message key="client.cart.subtotal.title"/></th>
        </tr>
        <tr>
            <td>${cart.checkout}</td>
        </tr>
        <tr>
            <th><fmt:message key="client.cart.subtotal.subtotal_with_discount"/></th>
        </tr>
        <tr>
            <td id="subtotal_row"><b>${cart.discountedCheckout}</b></td>
        </tr>
        <tr>
            <td>
                <form id="checkout_form" action="/Cruise?command=checkout" method="post">
                    <button id="checkout_button" type="submit"><fmt:message
                            key="client.cart.checkout_form.checkout_button"/></button>
                </form>
            </td>
        </tr>
    </table>
</div>
<c:if test="${deleted > 0}">
    <br>
    <h1><fmt:message key="client.cart.deleted_tickets_table.title"/></h1>

    <table id="deleted_tickets_table">
        <tr>
            <th><fmt:message key="client.cart.deleted_tickets_table.ticket_id"/></th>
            <th><fmt:message key="client.cart.deleted_tickets_table.ship_name"/></th>
            <th><fmt:message key="client.cart.deleted_tickets_table.cruise_duration"/></th>
            <th><fmt:message key="client.cart.deleted_tickets_table.comfort_level"/></th>
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