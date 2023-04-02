<%-- 
    Document   : createNewAccount
    Created on : Mar 14, 2023, 10:28:34 PM
    Author     : truon
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create</title>
    </head>
    <body>
        <h1>Create New Account</h1>
        <form action="CreateNewAccountServlet" method="POST">
            <c:set var="error" value="${requestScope.CREATE_ERROR}"/>
            Username* <input type="text" name="txtUsername" 
                             value="${param.txtUsername}" />(6 - 20 characters)<br>
            <c:if test="${not empty error.usernameLengthError}">
                <font color = "red">
                ${error.usernameLengthError}
                </font><br><br>
            </c:if>
            <c:if test="${not empty error.usernameIsExisted}">
                <font color = "red">
                ${error.usernameIsExisted}
                </font><br><br>
            </c:if>
            Password* <input type="password" name="txtPassword" 
                             value="" />(6 - 30 characters)<br>
            <c:if test="${not empty error.passwordLengthError}">
                <font color = "red">
                ${error.passwordLengthError}
                </font><br><br>
            </c:if>
            Confirm* <input type="password" name="txtConfirm" 
                            value="" /><br>
            <c:if test="${not empty error.confirmNotMatched}">
                <font color = "red">
                ${error.confirmNotMatched}
                </font><br><br>
            </c:if>
            Full name* <input type="text" name="txtFullname" 
                              value="${param.txtFullname}" />(2 - 50 characters)<br>
            <c:if test="${not empty error.fullnameLengthError}">
                <font color = "red">
                ${error.fullnameLengthError}
                </font><br><br>
            </c:if>
            <input type="submit" value="Create New Account" />
            <input type="reset" value="Reset" />
        </form>
        <br><br>
        <a href="loginPage">Go to log in page</a>
    </body>
</html>
