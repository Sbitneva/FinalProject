<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>

<fmt:setLocale value="${language}"/>

<fmt:setBundle basename="MessagesBundle"/>

<html>
<head>
    <title>Cruise Company</title>
    <link href="css/main.css" type="text/css" rel="stylesheet">
    <link href="css/authorization_form.css" type="text/css" rel="stylesheet">
</head>
<body onload="document.authorization.login.focus();">
${language}
<script src="../js/check_empty_field.js"></script>
<form name="authorization" action="Cruise?command=login" onsubmit="required()" method="post">
    <table id="login_table">
        <tbody>
        <tr id="error_messages">
            <td align="center">${errors}</td>
        </tr>
        <tr>
            <td>
                <table cellpadding="0" cellspacing="20" width="400">
                    <tbody>
                    <tr>
                        <td class="auth_cell_titles"><fmt:message key="authorization.login_table.email"/></td>
                        <td class="auth_cell" width="100%">
                            <input name="email" style="width: 100%;" class="auth" type="text"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="auth_cell_titles"><fmt:message key="authorization.login_table.password"/>:</td>
                        <td class="auth_cell">
                            <input name="password" style="width: 100%;" class="auth" type="password"/>
                        </td>

                    <tr></tr>
                    <tr>
                        <td></td>
                        <td class="auth_submit" align="left">
                            <button type="submit" id="auth_submit_button" action="../jsp/client/client-page.jsp">
                                <fmt:message key="authorization.login_table.auth_submit_button"/>
                            </button>
                            <form name="registration" method="post">
                                <a href="../jsp/registration/registration.jsp">
                                    <fmt:message key="authorization.login_table.registration"/>
                                </a>
                            </form>
                        </td>
                    </tr>
                    <tr id="languages_buttons">
                        <form>
                            <select id="language" name="language" onchange="submit()">
                                <option value="en_EN" ${language == 'en_EN' ? 'selected' : ''}>English</option>
                                <option value="ru_UA" ${language == 'ru_UA' ? 'selected' : ''}>Русский</option>
                            </select>
                        </form>
                    </tr>
                    </tbody>
                </table>
            </td>
        </tr>
        </tbody>
    </table>
</form>
</body>
</html>