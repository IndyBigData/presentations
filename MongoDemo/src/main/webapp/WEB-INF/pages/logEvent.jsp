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
        <h1>Listing Logging Event</h1>
        
       ${count} Log Events Recorded
       
       <br/>
                
        <table>
            <tr>
                <th>Message</th>
                <th>Created</th>
            </tr>
            <c:forEach items="${logEvents}" var="logEvent" varStatus="idx">
                <tr class="<c:out value="${idx.count % 2 == 0 ? 'none' : 'highlight'}"/>">
                    <td>${logEvent.message}</td>
                    <td>${logEvent.createdAt}</td>
                </tr>
            </c:forEach>
        </table>

    </body>
</html>
