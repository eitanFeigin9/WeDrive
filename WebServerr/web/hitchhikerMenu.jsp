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
    <link rel="stylesheet" href="editDelEvent/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        @import url('https://fonts.googleapis.com/css?family=Raleway:400,700');

        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-family: Raleway, sans-serif;
        }

        body {
            background: linear-gradient(90deg, #C7C5F4, #776BCC);
            display: flex;
            align-items: center;
            justify-content: center;
            min-height: 100vh;
            padding: 20px;
        }

        .container {
            width: 100%;
            max-width: 800px;
            background: linear-gradient(90deg, #5D54A4, #7C78B8);
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0px 0px 24px rgba(0, 0, 0, 0.3);
        }

        h2 {
            text-align: center;
            color: #FFF;
            margin-bottom: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            background-color: #FFF;
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0px 0px 12px rgba(0, 0, 0, 0.1);
        }

        th, td {
            padding: 12px 15px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #5D54A4;
            color: #FFF;
        }

        tr:nth-child(even) {
            background-color: #F5F5F5;
        }

        tr:hover {
            background-color: #E0E0E0;
        }

        .button-container {
            text-align: center;
            margin-top: 20px;
        }

        .button {
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            border-radius: 26px;
            cursor: pointer;
            background-color: #FFF;
            color: #4C489D;
            border: 1px solid #D4D3E8;
            font-weight: 700;
            box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.2);
            transition: background-color 0.2s, border-color 0.2s;
        }

        .button:hover {
            background-color: #E8E8E8;
        }

        .button.edit-button {
            background-color: #4CAF50;
            color: #FFF;
            border: none;
        }

        .button.edit-button:hover {
            background-color: #45a049;
        }

        .button.delete-button {
            background-color: #f44336;
            color: #FFF;
            border: none;
        }

        .button.delete-button:hover {
            background-color: #e53935;
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

        /* Media Queries */
        @media (max-width: 1199px) {
            .container {
                padding: 20px;
            }

            h2 {
                font-size: 1.5em;
            }

            table {
                font-size: 0.9em;
            }

            .button {
                font-size: 0.9em;
            }
        }

        @media (max-width: 768px) {
            table, th, td {
                font-size: 0.8em;
            }

            .button {
                font-size: 0.8em;
            }
        }
        .driver-card {
            background-color: #f8f9fa;
            padding: 15px;
            border: 1px solid #ccc;
            border-radius: 8px;
            box-shadow: 0px 2px 6px rgba(0, 0, 0, 0.1);
            margin-top: 10px;
        }

        .driver-info {
            display: flex;
            justify-content: space-between;
            margin-bottom: 10px;
        }

        .driver-label {
            display: inline-block;
            width: 100px;
            font-weight: 700;
            color: #2980b9;
        }

        .driver-value {
            display: inline-block;
            font-weight: 400;
            color: #555;
        }

        /* For spacing and responsiveness */
        @media (max-width: 768px) {
            .driver-info {
                flex-direction: column;
                text-align: left;
            }
        }

    </style>
</head>
<body>
<div class="container">
    <h2>Hitchhiker's Events</h2>

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
        <thead>
        <tr>
            <th>Event Name</th>
            <th>Pickup Location</th>
            <th>Fuel Contribution ($)</th>
            <th>Edit</th>
            <th>Delete</th>
            <th>Is a Match?</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (HitchhikerRide ride : rides.values()) {
                String currEventName = ride.getEventName();
                String pickupLocation = ride.getPickupLocation();
                double fuelMoney = ride.getFuelMoney();
                String driverName = null;
                if (ride.getDriverName() != null) {
                    driverName = Users.getUserByUserName(ride.getDriverName()).getFullName();
                }
                boolean hasMatch = driverName != null && !driverName.isEmpty();
        %>
        <tr>
            <td><%= currEventName %></td>
            <td><%= pickupLocation %></td>
            <td><%= fuelMoney %></td>
            <td>
                <a href="editRideHitchhiker.jsp?eventName=<%= currEventName %>&userName=<%= userName %>" class="button edit-button">Edit</a>
            </td>
            <td>
                <a href="cancelRideHitchhiker.jsp?eventName=<%= currEventName %>&userName=<%= userName %>" class="button delete-button">Delete</a>
            </td>
            <td>
                <% if (hasMatch) {
                    ServerClient driver = Users.getUserByUserName(ride.getDriverName());
                    String driverPhone = driver.getPhoneNumber();
                    DriverRide driverRide = driver.getDrivingEvents().get(currEventName);
                    double driverFuelCosts = driverRide.getFuelReturnsPerHitchhiker();
                %>
                <div class="driver-card">
                    <div class="driver-info">
                        <div class="driver-label"><strong>Driver Name:</strong></div>
                        <div class="driver-value"><%= driverName %></div>
                    </div>
                    <div class="driver-info">
                        <div class="driver-label"><strong>Phone:</strong></div>
                        <div class="driver-value"><%= driverPhone %></div>
                    </div>
                    <div class="driver-info">
                        <div class="driver-label"><strong>Fuel Costs:</strong></div>
                        <div class="driver-value">$<%= driverFuelCosts %></div>
                    </div>
                </div>
                <% } else { %>
                <span class="x-sign">X</span>
                <% } %>
            </td>

        </tr>
        <%
            }
        %>
        </tbody>
    </table>
    <%
        }
    %>
    <div class="button-container">
        <a href="hitchhikerOptionsMenu.jsp" class="button">Back to Hitchhiker Options Menu</a>
    </div>
</div>

</body>
</html>

