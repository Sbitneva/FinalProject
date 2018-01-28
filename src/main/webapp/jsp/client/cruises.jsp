<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="MessagesBundle"/>
<html>
<head>
    <title>Cruise Company</title>
    <link href="../../css/main.css" type="text/css" rel="stylesheet">
    <link href="../../css/all-backgrounds.css" type="text/css" rel="stylesheet">
    <link href="../../css/client/ship.css" type="text/css" rel="stylesheet">
</head>
<body>
<table id="ships_table">
    <h1><fmt:message key="client.cruises.ships_table.title"/></h1>
    <tr>
        <th><fmt:message key="client.cruises.ships_table.ship_id"/></th>
        <th><fmt:message key="client.cruises.ships_table.ship_name"/></th>
        <th><fmt:message key="client.cruises.ships_table.cruise_duration"/></th>
        <th><fmt:message key="client.cruises.ships_table.additional_properties"/></th>
    </tr>
    <c:forEach items="${ships}" var="ship">
        <tr>
            <td>${ship.shipId}</td>
            <td>${ship.shipName}</td>
            <td>${ship.cruiseDuration}</td>
            <td>
                <form id="show_tickets_form"
                      action="/Cruise?command=showShip&shipId=${ship.shipId}"
                      method="post">
                    <button id="show_tickets_button" type="submit"><fmt:message
                            key="client.cruises.ships_table.show_tickets_form.show_tickets_button"/></button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>