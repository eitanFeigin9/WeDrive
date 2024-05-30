<%@ page import="entity.ServerClient" %>
<%@ page import="database.Users" %>
<%@ page import="ride.DriverRide" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Pickup</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .container {
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
            text-align: center;
            max-width: 400px;
        }
        h1 {
            color: #333;
            font-size: 22px;
            margin-bottom: 20px;
        }
        form {
            margin-bottom: 20px;
        }
        input[type="text"], input[type="number"] {
            width: calc(100% - 20px);
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 16px;
        }
        .button-container {
            text-align: center;
        }
        button, a.button {
            padding: 10px 20px;
            margin: 10px 5px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            text-decoration: none;
            display: inline-block;
            width: 45%;
        }
        .update-button {
            background-color: #4CAF50;
            color: white;
            padding: 12px 24px;
            border-radius: 5px;
            border: none;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s;
        }
        .update-button:hover {
            background-color: #45a049;
        }
        .notice {
            color: #ff0000;
            font-size: 12px;
            margin-top: -10px;
            margin-bottom: 10px;
            text-align: left;
        }
    </style>
</head>
<%
    String userName = (String) request.getSession().getAttribute("userName");
    String eventName = request.getParameter("eventName");
    ServerClient client = Users.getUserByFullName(userName);
    DriverRide ride = client.getDrivingEventByName(eventName);
%>
<body>
<div class="container">
    <h1>Edit Pickup For The <%= request.getParameter("eventName") %> Event</h1>
    <form action="editRide" method="post" class="form-container">
        <label for="maxCapacity">Max Capacity:</label><br>
        <input type="number" id="maxCapacity" name="maxCapacity" min="0" max="40" value="<%= ride.getMaxCapacity() %>" required><br>
        <div class="notice">Notice: If you have lowered the number of passengers - the last hitchhikers added will be affected</div>
        <label for="pickupCity">Pickup City:</label><br>
        <input type="text" id="pickupCity" name="pickupCity" value="<%= ride.getPickupCity() %>" required><br>
        <label for="fuelReturns">Fuel Returns (per hitchhiker):</label><br>
        <input type="number" id="fuelReturns" name="fuelReturns" min="0" value="<%= ride.getFuelReturnsPerHitchhiker() %>" required><br>
        <div class="notice">Notice: If you raise the fuel costs - the current passengers will pay the amount that agreed at the beginning</div>
        <form action="editRide" method="post" style="display:inline;">
            <input type="hidden" name="eventName" value="<%= request.getParameter("eventName") %>">
            <button type="submit" class="button update-button">Edit the Pickup</button>
        </form>
    </form>
</div>
</body>
</html>
