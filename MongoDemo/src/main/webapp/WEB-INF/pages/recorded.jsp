<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="../css/default.css">
        <title>Hugs from IndyHUG!</title>
    </head>
    <body>
        <h1>Thanks for participating!</h1>
        <table>
            <tr>
                <th>User Agent</th>
                <td>${logEvent.message}</td>
            </tr>
            <tr>
                <th>Recorded By</th>
                <td>${logEvent.source}</td>
            </tr>
            <tr>
                <th>Created At</th>
                <td>${logEvent.createdAt}</td>
            </tr>
        </table>
    </body>
</html>
