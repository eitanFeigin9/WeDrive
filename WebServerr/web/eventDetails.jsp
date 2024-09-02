<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.temporal.ChronoUnit" %>
<%@ page import="entity.ServerClient" %>
<%@ page import="event.EventData" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="database.Users" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Event Details</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f2f2f2;
        }
        h2 {
            text-align: center;
            color: #333;
            margin-top: 20px;
        }
        table {
            width: 80%;
            margin: 20px auto;
            border-collapse: collapse;
            background-color: #fff;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
        }
        th, td {
            padding: 12px 15px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #f2f2f2;
            font-weight: bold;
            color: #333;
        }
        tr:hover {
            background-color: #f5f5f5;
        }
        tr:nth-child(even) {
            background-color: #fafafa;
        }
        .button-container {
            text-align: center;
            margin-top: 20px;
        }
        .button {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            border-radius: 5px;
            cursor: pointer;
        }
        .button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
<h2>Event Details</h2>
<table>
    <tr>
        <th>Event Name</th>
        <th>Event Kind</th>
        <th>Date</th>
        <th>Location</th>
        <th>Days Till Event</th>
    </tr>
    <%
        String userName = (String)request.getSession().getAttribute("userName");
        ServerClient client = Users.getUserByFullName(userName);
        HashMap<String, EventData> events = client.getOwnedEvents();
        for (EventData event : events.values()) {
            LocalDate eventDate = LocalDate.parse(event.getEventDate());
            long daysTillEvent = ChronoUnit.DAYS.between(LocalDate.now(), eventDate);
    %>
    <tr>
        <td><%= event.getEventName() %></td>
        <td><%= event.getEventKind() %></td>
        <td><%= event.getEventDate() %></td>
        <td><%= event.getLocation() %></td>
        <td><%= daysTillEvent %></td>
    </tr>
    <% } %>
</table>
<div class="button-container">
    <a href="eventOwnerMenu.jsp" class="button">Back to Event Owner Menu</a>
</div>
</body>
</html>






