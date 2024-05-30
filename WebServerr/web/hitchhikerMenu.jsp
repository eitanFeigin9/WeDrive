<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.temporal.ChronoUnit" %>
<%@ page import="entity.ServerClient" %>
<%@ page import="event.EventData" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="database.Users" %>
<%@ page import="ride.DriverRide" %>
<%@ page import="ride.HitchhikerRide" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hitchhiker Menu</title>
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
            padding: 8px 15px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            border-radius: 5px;
            cursor: pointer;
            background-color: #4CAF50;
            color: white;
            border: none;
            margin-right: 10px;
        }
        .button.delete-button {
            background-color: #f44336;
        }
        .button.edit-button {
            background-color: #4CAF50;
        }
        .button:hover {
            opacity: 0.8;
        }
    </style>
</head>
<body>
<h2>Hitchhiker Current Rides</h2>
<table>
    <tr>
        <th>Event Name</th>
        <th>Pick Up Address</th>
        <th>Fuel Return</th>
    </tr>
    <%
        String userName = (String)request.getSession().getAttribute("userName");
        ServerClient client = Users.getUserByFullName(userName);
        HashMap<String, HitchhikerRide> rides = client.getHitchhikingEvents();
        for (HitchhikerRide ride : rides.values()) {
            String currEventName = ride.getEventName();
    %>
    <tr>
        <td><%= currEventName %></td>
        <td><%= ride.getPickupCity() %></td>
        <td><%= ride.getFuelMoney() %></td>
        <td>
            <a href="editRideHitchhiker.jsp?eventName=<%= currEventName %>" class="button edit-button">Edit</a>
        </td>
        <td>
            <a href="cancelRideHitchhiker.jsp?eventName=<%= currEventName %>" class="button delete-button">Cancel</a>
        </td>
    </tr>
    <% } %>
</table>
<div class="button-container">
    <a href="hitchhikerOptionsMenu.jsp" class="button">Back to Hitchhiker Options Menu</a>
</div>
</body>
</html>
