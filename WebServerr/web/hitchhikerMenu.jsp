<%@ page import="java.util.HashMap" %>
<%@ page import="entity.ServerClient" %>
<%@ page import="ride.HitchhikerRide" %>
<%@ page import="database.Users" %>
<%@ page import="ride.DriverRide" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hitchhiker Menu</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f4f9;
            color: #333;
            margin: 0;
            padding: 20px;
        }

        h1 {
            text-align: center;
            color: #2c3e50;
            margin-bottom: 30px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            background-color: #fff;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            margin-bottom: 40px;
        }

        th, td {
            padding: 15px;
            text-align: center;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #2c3e50;
            color: white;
            font-size: 16px;
        }

        td {
            font-size: 14px;
            color: #555;
        }

        tr:hover {
            background-color: #f1f1f1;
        }

        .edit-button, .delete-button {
            padding: 8px 16px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            transition: background-color 0.3s ease;
        }

        .edit-button {
            background-color: #27ae60;
            color: white;
        }

        .edit-button:hover {
            background-color: #2ecc71;
        }

        .delete-button {
            background-color: #e74c3c;
            color: white;
        }

        .delete-button:hover {
            background-color: #c0392b;
        }

        .x-sign {
            color: #e74c3c;
            font-size: 18px;
            font-weight: bold;
        }

        .driver-details {
            color: #2980b9;
            font-size: 14px;
        }

        .no-events {
            font-size: 20px;
            color: #e74c3c;
            text-align: center;
            margin-top: 30px;
        }

        footer {
            text-align: center;
            font-size: 12px;
            color: #aaa;
            margin-top: 50px;
        }
    </style>
</head>
<body>

<h1>Hitchhiker's Events</h1>

<%
    String userName = (String) request.getSession().getAttribute("userName");
    ServerClient client = Users.getUserByUserName(userName);
    HashMap<String, HitchhikerRide> rides = client.getHitchhikingEvents();

    if (rides == null || rides.isEmpty()) {
%>
<p class="no-events">You don't have any events that you need transportation for.</p>
<%
} else {
%>
<table>
    <tr>
        <th>Event Name</th>
        <th>Pickup Location</th>
        <th>Fuel Contribution ($)</th>
        <th>Edit</th>
        <th>Delete</th>
        <th>Is a Match?</th>
    </tr>
    <%
        for (HitchhikerRide ride : rides.values()) {
            String currEventName = ride.getEventName();
            String pickupLocation = ride.getPickupLocation();
            double fuelMoney = ride.getFuelMoney();
            String driverName=null;
            if(ride.getDriverName()!=null)
            {
                 driverName =Users.getUserByUserName(ride.getDriverName()).getFullName();

            }
            boolean hasMatch = driverName != null && !driverName.isEmpty();
    %>
    <tr>
        <td><%= currEventName %></td>
        <td><%= pickupLocation %></td>
        <td><%= fuelMoney %></td>
        <td>
            <a href="editRideHitchhiker.jsp?eventName=<%= currEventName %>&userName=<%= userName %>" class="edit-button">Edit</a>
        </td>
        <td>
            <a href="cancelRideHitchhiker.jsp?eventName=<%= currEventName %>&userName=<%= userName %>" class="delete-button">Delete</a>
        </td>
        <td>
            <%
                if (hasMatch) {
                    ServerClient driver = Users.getUserByUserName(ride.getDriverName());
                    String driverPhone = driver.getPhoneNumber();
                    DriverRide driverRide = driver.getDrivingEvents().get(currEventName);
                    double driverFuelCosts = driverRide.getFuelReturnsPerHitchhiker();
            %>
            <div class="driver-details">
                Driver: <%= driverName %><br>
                Phone: <%= driverPhone %><br>
                Fuel Costs: $<%= driverFuelCosts %>
            </div>
            <%
            } else {
            %>
            <span class="x-sign">X</span>
            <%
                }
            %>
        </td>
    </tr>
    <%
        }
    %>
</table>
<%
    }
%>

<footer>
    &copy; <%= new java.util.Date().getYear() + 1900 %> WeDrive | All rights reserved
</footer>

</body>
</html>
