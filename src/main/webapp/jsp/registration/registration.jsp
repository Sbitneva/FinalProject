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
    <title>Registration</title>
    <link href="../../css/all-backgrounds.css" rel="stylesheet">
    <link href="../../css/registration/registration.css" rel="stylesheet">
</head>
<body>
<form id="error">

</form>

<div id="wrapper">
    <form action="/Cruise?command=registration" method="post">
        <b>${errors}</b>
        <fieldset>
            <legend><fmt:message key="registration.title"/></legend>
            <div>
                <input type="text" name="first_name" placeholder=<fmt:message key="registration.first_name"/>/>
            </div>
            <div>
                <input type="text" name="last_name" placeholder=<fmt:message key="registration.last_name"/>/>
            </div>
            <div>
                <input type="text" name="email" placeholder=<fmt:message key="registration.email"/>/>
            </div>
            <div>
                <input type="password" name="password" placeholder=<fmt:message key="registration.password"/>/>
            </div>
            <input type="submit" name="submit" value=<fmt:message key="registration.send"/>/>
        </fieldset>
    </form>
</div>
</body>
</html>