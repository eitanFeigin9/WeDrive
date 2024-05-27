<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.temporal.ChronoUnit" %>
<%@ page import="entity.ServerClient" %>
<%@ page import="event.EventData" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="database.Users" %>
<%@ page import="ride.DriverRide" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Driver Menu</title>
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
        .button.delete-button {
            background-color: #f44336;
        }
        .button.edit-button {
            margin-right: 10px;
        }
        .button:hover {
            opacity: 0.8;
        }
    </style>
</head>
<body>
<h2>Driver Menu</h2>
<table>
    <tr>
        <th>Event Name</th>
        <th>Current Amount Of Hitchhikers</th>
        <th>Max Capacity</th>
        <th>Fuel Return Per Hitchhiker</th>
        <th>Total fuel returns</th>
    </tr>
    <%
        String userName = (String)request.getSession().getAttribute("userName");
        ServerClient client = Users.getUserByFullName(userName);
        HashMap<String, DriverRide> rides = client.getDrivingEvents();
        for (DriverRide ride : rides.values()) {
            String currEventName = ride.getEventName();
    %>
    <tr>
        <td><%= currEventName %></td>
        <td><%= ride.getCurrNumOfHitchhikers() %></td>
        <td><%= ride.getMaxCapacity() %></td>
        <td><%= ride.getFuelReturnsPerHitchhiker() %></td>
        <td><%= ride.getTotalFuelReturns() %></td>
    </tr>
    <% } %>
</table>
<div class="button-container">
    <a href="eventOwnerMenu.jsp" class="button">Back to Event Owner Menu</a>
</div>
</body>
</html>
