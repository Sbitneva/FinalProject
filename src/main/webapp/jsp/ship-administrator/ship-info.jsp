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
    <link href="../../css/main.css" type="text/css" rel="stylesheet">
    <link href="../../css/all-backgrounds.css" type="text/css" rel="stylesheet">
    <link href="../../css/ship-administrator/ship-info.css" type="text/css" rel="stylesheet">
</head>
<body>
<h1>${ship.shipName}</h1>

<form id="staff_button" action="/Cruise?command=getStaff&shipId=${ship.shipId}" type="submit"
      method="post">
    <button id="staff" type="submit">
        <fmt:message key="ship-administrator.ship-info.staff_button.staff"/>
    </button>
</form>


<table id="tickets_table">
    <tr>
        <th><fmt:message key="ship-administrator.ship-info.tickets_table.ticket_id"/></th>
        <th><fmt:message key="ship-administrator.ship-info.tickets_table.comfort_level"/></th>
        <th><fmt:message key="ship-administrator.ship-info.tickets_table.discount"/></th>
        <th><fmt:message key="ship-administrator.ship-info.tickets_table.price"/></th>
    </tr>

    <c:forEach items="${ship.tickets}" var="ticket">
        <tr>
            <td>${ticket.ticketId}</td>
            <td>${ticket.comfortLevelName}
                <form id="show_services"
                      action="/Cruise?command=getServices&comfortId=${ticket.comfortLevel}"
                      method="post">
                    <button id="show" type="submit"><fmt:message key="ship-administrator.ship-info.tickets_table.show_services.show"/></button>
                </form>
            </td>
            <td>
                <form id="apply_discount"
                      action="/Cruise?command=setDiscount&ticketId=${ticket.ticketId}&page=${page}"
                      method="post">
                    <input type="text" name="discount" contenteditable="true" value="${ticket.discount}">
                    <button id="apply" type="submit"><fmt:message key="ship-administrator.ship-info.tickets_table.apply_discount.submit"/></button>
                </form>
            </td>
            <td>
                    ${ticket.price}
            <td>
        </tr>
    </c:forEach>
    <br>
    <b><fmt:message key="ship-administrator.ship-info.tickets_table.current_page"/> ${page}</b>
</table>
<table id="pagination_table">
    <tr align="center">
        <c:forEach var="i" begin="1" end="${pages}">
            <form id="pagination_form" action="/Cruise?command=showShip" method="post">
                <td>
                    <button name="page" type="submit" type="text" value="${i}">${i}</button>
                </td>
            </form>
        </c:forEach>
    </tr>
</table>
</body>
</html>