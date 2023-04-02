<%-- 
    Document   : checkout
    Created on : Mar 15, 2023, 11:32:48 AM
    Author     : truon
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Receipt</title>
    </head>
    <body>
        <h1>RECEIPT:  [${requestScope.ORDER.id}]</h1>
        <h2>Name:  ${requestScope.ORDER.name}</h2>
        <h2>Address:  ${requestScope.ORDER.address}</h2>
        <h2>Date:  ${requestScope.ORDER.date}</h2>
        <br>
        <table border="1">
            <thead>
                <tr>
                    <th>Item Name</th>
                    <th>Quantity</th>
                    <th>Unit Price</th>
                    <th>Item Total</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${sessionScope.DETAILS}">
                    <tr>
                        <td>${item.item_name}</td>
                        <td style="text-align: right">
                            ${item.quantity}
                        </td>
                        <td style="text-align: right">
                            ${item.price}
                        </td>
                        <td style="text-align: right">
                            ${item.total}
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="3">Total Price</td>
                    <td>${requestScope.ORDER.total}</td>
                </tr>
            </tbody>
        </table>
        <br>
        <a href="loginPage">Go to log in page</a>
        <br>
        <a href="shopping">Go back to shopping page</a>
    </body>
</html>
