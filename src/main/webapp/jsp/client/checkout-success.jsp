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
</head>
<body>
<h1><fmt:message key="client.checkout-success.continue_shopping.title"/></h1>

<form id="continue_shopping" action="/Cruise?command=client" method="post">
    <button id="continue_shopping_button" type="submit"><fmt:message
            key="client.checkout-success.continue_shopping.continue_shopping_button"/></button>
</form>
</body>