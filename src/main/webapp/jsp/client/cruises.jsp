<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <title>Cruise Company</title>
    <link href="../../css/main.css" type="text/css" rel="stylesheet">
    <link href="../../css/all-backgrounds.css" type="text/css" rel="stylesheet">
    <link href="../../css/client/ship.css" type="text/css" rel="stylesheet">
</head>
<body>
<table id="ships_table">
    <h1>Available cruises:</h1>
    <tr>
        <th>Ship Id</th>
        <th>Ship Name</th>
        <th>Cruise Duration</th>
        <th>Additional properties</th>
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
                    <button id="show_tickets_button" type="submit"> Show tickets</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>