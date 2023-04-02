<%-- 
    Document   : shopping
    Created on : Mar 15, 2023, 8:59:30 AM
    Author     : truon
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Shopping Page</title>
    </head>
    <body>
        <h1>Product List</h1>
        <c:set var="product_dto" value="${requestScope.PRODUCT}"/>
        <c:if test="${empty product_dto}">
            <h2>Items are not available !!!</h2>
        </c:if>
        <c:if test="${not empty product_dto}">
            <c:set var="addItemErrors" value="${requestScope.ADD_TO_CART_ERROR}" />
            <c:if test="${not empty addItemErrors.invalidQuantity}" >
                <font color="red">
                ${addItemErrors.invalidQuantity}
                </font>
            </c:if>
            <table border="1">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Price</th>
                        <th>Remaining</th>
                        <th>Amount to buy</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${product_dto}" varStatus="counter">
                    <form action="addToCart" method="POST">
                        <tr>
                            <td style="text-align: right">
                                ${counter.count}.
                            </td>
                            <td style="text-align: right">
                                ${item.sku}
                            </td>
                            <td>${item.name}</td>
                            <td>${item.description}</td>
                            <td style="text-align: right">
                                ${item.price}
                            </td>
                            <td style="text-align: right">
                                ${item.quantity}
                            </td>
                            <td>
                                <input type="number" name="txtQuantity" value="0" 
                                       style="text-align:right; width: 100px;" 
                                       min="0"
                                       max="${item.quantity}"
                                       />
                            </td>
                            <td>
                                <input type="submit" value="Add" />
                                <input type="hidden" name="ddlBook" value="${item.sku}" />

                            </td>
                        </tr>
                    </form>
                </c:forEach>
            </tbody>
        </table>
        <br>
        <a href="viewCartPage">View your cart</a>
    </c:if>
    <br><br>
    <a href="loginPage">Go to log in page</a>
</body>
</html>
