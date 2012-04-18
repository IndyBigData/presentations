<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="../css/default.css">
        <title>MongoDemo</title>
    </head>
    <body>
        <h1>User Agent Breakdown</h1>
        
        <table>
            <tr>
                <th>User Agent</th>
                <th>Count</th>
            </tr>
        <c:forEach items="${totals}" var="total" varStatus="idx">
            <tr class="<c:out value="${idx.count % 2 == 0 ? 'none' : 'highlight'}"/>">
                <td>${total.id}</td>
                <td>${total.value}</td>
            </tr>
        </c:forEach>
        </table>

    </body>
</html>
