<%-- 
    Document   : viewcart
    Created on : Mar 15, 2023, 9:03:25 AM
    Author     : truon
--%>

<%@page import="java.util.Map"%>
<%@page import="ductm.cart.CartObj"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Your Cart</title>
    </head>
    <body>
        <h1>Your Cart</h1>
        <c:set var="items" value="${sessionScope.CART.items}" />
        <c:if test="${empty items}" >
            <h2>
                Empty cart !!!
            </h2>
            <br>
            <a href="shopping">Go back to shopping page</a>
        </c:if>
        <c:if test="${not empty items}">
            <form action="removeFromCart">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Name</th>
                            <th>Quantity</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" items="${items}" varStatus="counter">
                            <tr>
                                <td style="text-align: right">
                                    ${counter.count}
                                    .</td>
                                <td>
                                    ${item.key}
                                </td>
                                <td style="text-align: right">
                                    ${item.value}
                                </td>
                                <td>
                                    <input type="checkbox" name="chkItem" value="${item.key}" />
                                </td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td colspan="3">
                                <a href="shopping">Add more products to your cart</a>
                            </td>
                            <td>
                                <input type="submit" value="Remove" />
                            </td>
                        </tr>
                    </tbody>
                </table>

            </form>
            <br>
            <h1>Check out</h1>
            <c:set var="orderErrors" value="${requestScope.ORDER_ERROR}" />
            <form action="checkOut" method="POST">
                Name* <input type="text" name="txtCheckOutName" value="" required/> <br>
                <br>
                <c:if test="${not empty orderErrors.nameLengthError}">
                    <font color = "red">
                    ${orderErrors.nameLengthError}
                    </font><br><br>
                </c:if>
                Address* <textarea name="txtAddress" 
                                   value="" rows = "5" cols = "20"
                                   style="overflow-y: scroll; resize: none" required>
                </textarea> <br>
                <br>
                <c:if test="${not empty orderErrors.addressLengthError}">
                    <font color = "red">
                    ${orderErrors.addressLengthError}
                    </font><br><br>
                </c:if>
                <input type="submit" value="Confirm"/>
                <input type="reset" value="Reset" />
            </form>
        </c:if>
    </body>
</html>
