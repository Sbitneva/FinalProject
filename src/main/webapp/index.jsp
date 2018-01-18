<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <title>Cruise Company</title>
    <link href="css/main.css" type="text/css" rel="stylesheet">
    <link href="css/authorization_form.css" type="text/css" rel="stylesheet">
</head>
<body onload="document.authorization.login.focus();">
<script src="../js/check_empty_field.js"></script>
<form name="authorization" action="Cruise?command=login" onsubmit="required()" method="post">
    <table id="login_table">
        <tbody>
        <tr>
            <td>
                <table cellpadding="0" cellspacing="20" width="400">
                    <tbody>
                    <tr>
                        <td class="auth_cell_titles">Email:</td>
                        <td class="auth_cell" width="100%">
                            <input name="email" style="width: 100%;" class="auth" type="text"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="auth_cell_titles">Password:</td>
                        <td class="auth_cell">
                            <input name="password" style="width: 100%;" class="auth" type="password"/>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="auth_submit" align="left">
                            <button type="submit" id="auth_submit_button" action="../jsp/client/client-page.jsp">Sign
                                in
                            </button>
                            <form name="registration" method="post">
                                <a href="../jsp/registration/registration.jsp">Registration</a>
                            </form>
                        </td>
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