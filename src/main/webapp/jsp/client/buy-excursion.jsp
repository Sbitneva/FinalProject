<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Cruise Company</title>
    <link href="../../css/main.css" type="text/css" rel="stylesheet">
    <link href="../../css/all-backgrounds.css" type="text/css" rel="stylesheet">
    <link href="../../css/client/buy-excursion.css" type="text/css" rel="stylesheet">
</head>
<body>
<h2>Select Excursion: </h2>
<table id="excursions_table">
    <tr>
        <th>Excursion Name</th>
        <th>Port Name</th>
        <th>Price</th>
        <th>Additional properties</th>
    </tr>
    <c:forEach items="${ports}" var="port">
        <c:forEach items="${port.excursions}" var="excursion">
            <tr>
                <td>${excursion.excursionName}</td>
                <td>${excursion.portName}</td>
                <td>${excursion.price}</td>
                <td>
                    <form id="buy_excursion_form"
                          action="/CruiseServlet?command=buyExcursion&action=buy&ticketId=${ticketId}&excursionId=${excursion.excursionId}"
                          method="post">
                        <button id="buy_excursion_button" type="submit"> Buy new Excursion</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </c:forEach>
</table>
</body>
</html>