<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Cruise Company</title>
    <link href="../../css/main.css" type="text/css" rel="stylesheet">
    <link href="../../css/all-backgrounds.css" type="text/css" rel="stylesheet">
    <link href="../../css/ship-administrator/staff.css" type="text/css" rel="stylesheet">
</head>
<body>
<h1>Comfort level : ${comfortLevel.comfortLevelName}</h1>

<table id="staff_table">
    <tr>
        <th>Id</th>
        <th>First name</th>
        <th>Last name</th>
        <th>Position</th>
    </tr>
    <c:forEach items="${staff}" var="staffItem">
        <tr>
            <td>${staffItem.staffId}</td>
            <td>${staffItem.firstName}</td>
            <td>${staffItem.lastName}</td>
            <td>${staffItem.position}</td>
        </tr>
    </c:forEach>
</table>
</body>