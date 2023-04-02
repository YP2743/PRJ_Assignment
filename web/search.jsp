<%-- 
    Document   : search
    Created on : Feb 22, 2023, 4:20:17 PM
    Author     : truon
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="ductm.account.AccountDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search</title>
    </head>
    <body>
        <font color = "red">
        Welcome, ${sessionScope.USER.lastname}
        </font>
        <h1>Search Page</h1>
        <form action="search">
            Search <input type="text" name="txtSearchValue" 
                          value="${param.txtSearchValue}"/> <br><br>
            <input type="submit" value="search" name="btAction"/>
        </form>
        <br>
        <c:set var="searchValue" value="${param.txtSearchValue}"/>
        <c:if test="${not empty searchValue}">
            <c:set var="resultList" value="${requestScope.SEARCH_RESULT}"/>
            <c:if test="${empty resultList}">
                <h2>Nothing</h2>
            </c:if>
            <c:if test="${not empty resultList}">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Username</th>
                            <th>Password</th>
                            <th>Full name</th>
                            <th>Role</th>
                            <th>Delete</th>
                            <th>Update</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="count" items="${resultList}" varStatus="counter">
                            <c:url var="urlRewriting" value="delete">
                                <c:param name="pk" value="${count.username}"/>
                                <c:param name="lastSearchValue" value="${param.txtSearchValue}"/>
                            </c:url>

                        <form action="update" method="POST">
                            <tr>
                                <td>${counter.count}.</td>
                                <td>
                                    ${count.username}
                                    <input type="hidden" name="txtUsername" value="${count.username}" />
                                </td>
                                <td>
                                    <input type="text" name="txtPassword" value="${count.password}" required/>
                                </td>
                                <td>${count.lastname}</td>
                                <td>
                                    <input type="checkbox" name="checkRole" value="ON" 
                                           <c:if test="${count.isAdmin eq true}">
                                               checked="checked"
                                           </c:if>
                                           />
                                </td>
                                <td>
                                    <a href="${urlRewriting}">delete</a>
                                </td>
                                <td>
                                    <input type="submit" value="update" />
                                    <input type="hidden" name="lastSearchValue" value="${param.txtSearchValue}" />
                                </td>
                            </tr>

                        </form>
                    </c:forEach>
                    <c:set var="updateErrors" value="${requestScope.UPDATE_ERROR}" />
                    <c:if test="${not empty updateErrors.passwordUpdateError}">
                        <font color = "red">
                        ${updateErrors.passwordUpdateError}
                        </font><br><br>
                    </c:if>

                    </tbody>
                </table>
            </c:if>
        </c:if>
        <br>
        <form action="logout" method="POST">
            <input type="submit" value="log out" />
        </form>
    </body>
</html>
