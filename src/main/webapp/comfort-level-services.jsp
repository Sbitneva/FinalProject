<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="MessagesBundle"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="css/main.css" type="text/css" rel="stylesheet">
    <link href="css/all-backgrounds.css" type="text/css" rel="stylesheet">
    <link href="css/comfort-levels-services.css" type="text/css" rel="stylesheet">
    <title>Cruise Company</title>
</head>
<body>
<h1><fmt:message key="comfort-level-services.services_table.title"/> : ${comfortLevel.comfortLevelName}</h1>

<table id="services_table">
    <tr>
        <th><fmt:message key="comfort-level-services.services_table.services"/></th>
    </tr>
    <c:forEach items="${comfortLevel.services}" var="service">
        <tr>
            <td>${service.serviceName}</td>
        </tr>

    </c:forEach>
</table>
</body>