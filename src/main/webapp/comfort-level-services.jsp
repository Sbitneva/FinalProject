<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Cruise Company</title>
    <link href="css/main.css" type="text/css" rel="stylesheet">
    <link href="css/all-backgrounds.css" type="text/css" rel="stylesheet">
    <link href="css/comfort-levels-services.css" type="text/css" rel="stylesheet">
</head>
<body>
    <h1>Comfort level : ${comfortLevel.comfortLevelName}</h1>

    <table id="services_table">
        <tr>
            <th>Services</th>
        </tr>
        <c:forEach items="${comfortLevel.services}" var="service">
            <tr>
                <td>${service.serviceName}</td>
            </tr>

        </c:forEach>
    </table>
</body>