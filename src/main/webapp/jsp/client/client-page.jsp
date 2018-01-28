<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
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
</head>
<body>

<div class="functions-class">
    <h1 id="hello_string" class="functions-class"><fmt:message key="client.client-page.hello_string"/>${client.firstName} ${client.lastName} </h1>
    <form id="show_cart" class="functions-class" action="/Cruise?command=cart" method="post">
        <button id="show_cart_button" type="submit"><fmt:message
                key="client.client-page.show_cart.show_cart_button"/></button>
    </form>
    <form id="logout" class="functions-class" action="/Cruise?command=logout" method="post">
        <button id="logout_button" type="submit"><fmt:message key="client.client-page.logout.logout_button"/></button>
    </form>
</div>
<form id="buy_ticket_form" action="/Cruise?command=getCruises" method="post">
    <button id="buy_ticket_button" type="submit"><fmt:message
            key="client.client-page.buy_ticket_form.buy_ticket_button"/></button>
</form>

<h2><fmt:message key="client.client-page.tickets_table.titile"/></h2>

<table id="tickets_table">
    <tr>
        <th><fmt:message key="client.client-page.tickets_table.ticket_id"/></th>
        <th><fmt:message key="client.client-page.tickets_table.ship_name"/></th>
        <th><fmt:message key="client.client-page.tickets_table.cruise_duration"/></th>
        <th><fmt:message key="client.client-page.tickets_table.comfort_level"/></th>
        <th><fmt:message key="client.client-page.tickets_table.additional_properties"/></th>
    </tr>
    <c:forEach items="${client.tickets}" var="ticket">
        <tr>
            <td>${ticket.ticketId}</td>
            <td>${ticket.shipName}</td>
            <td>${ticket.cruiseDuration}</td>
            <td>
                <form id="show_services"
                      action="/Cruise?command=getServices&comfortId=${ticket.comfortLevel}"
                      method="post">
                        ${ticket.comfortLevelName}
                    <button id="show" type="submit"><fmt:message
                            key="client.client-page.tickets_table.show_services.show"/></button>
                </form>
            </td>
            <td>
                <form id="buy_excursion_form"
                      action="/Cruise?command=getExcursions&ticketId=${ticket.ticketId}"
                      method="post">
                    <button id="buy_excursion_button" type="submit"><fmt:message
                            key="client.client-page.tickets_table.buy_excursion_form.buy_excursion_button"/></button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<h2><fmt:message key="client.client-page.excursions_table.title"/></h2>

<table id="excursions_table">
    <tr>
        <th><fmt:message key="client.client-page.excursions_table.ticket_id"/></th>
        <th><fmt:message key="client.client-page.excursions_table.excursion_name"/></th>
        <th><fmt:message key="client.client-page.excursions_table.ship_name"/></th>
        <th><fmt:message key="client.client-page.excursions_table.port"/></th>
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