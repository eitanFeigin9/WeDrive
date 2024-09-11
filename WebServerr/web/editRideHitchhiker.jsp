<%@ page import="entity.ServerClient" %>
<%@ page import="database.Users" %>
<%@ page import="ride.DriverRide" %>
<%@ page import="ride.HitchhikerRide" %>
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
        label {
            display: block;
            text-align: left;
            margin-bottom: 8px;
            font-weight: bold;
            color: #333;
        }
        input[type="text"], input[type="number"] {
            width: calc(100% - 20px);
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 16px;
        }
        .notice {
            color: #ff0000;
            font-size: 12px;
            margin-top: -10px;
            margin-bottom: 10px;
            text-align: left;
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
            background-color: #44386c;
            color: white;
            padding: 12px 24px;
            border-radius: 5px;
            border: none;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s;
        }
        .update-button:hover {
            background-color: #6754a8;
        }
        .error-message {
            color: #ff0000;
            margin-bottom: 10px;
            font-size: 14px;
        }
        #map {
            width: 100%;
            height: 300px;
            margin-top: 20px;
            border-radius: 5px;
        }
    </style>
</head>
<%
    String userName = (String) request.getSession().getAttribute("userName");
    String eventName = request.getParameter("eventName");
    ServerClient client = Users.getUserByUserName(userName);
    HitchhikerRide ride = client.getHitchhikingEventByName(eventName);
%>
<body>
<div class="container">
    <%
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null) {
    %>
    <div class="error-message">
        <strong><%= errorMessage %></strong>
    </div>
    <%
        }
    %>
    <h1>Edit Transportation Request for <%= eventName %></h1>
    <form action="editRideHitchhiker" method="post" class="form-container">
        <label for="pickupCity">Pickup Address:</label>
        <input type="text" id="pickupCity" name="pickupCity" value="<%= ride.getPickupLocation() %>" required>
        <div class="notice">Notice: Changing the pickup address may cancel your current ride.</div>

        <label for="fuelMoney">Maximum Fuel Price:</label>
        <input type="number" id="fuelMoney" name="fuelMoney" min="0" value="<%= ride.getFuelMoney() %>" required>
        <div class="notice">Notice: Lowering the price may lead to cancellation of your current ride.</div>

        <input type="hidden" id="latitude" name="latitude" value="<%= ride.getLatitude() %>">
        <input type="hidden" id="longitude" name="longitude" value="<%= ride.getLongitude() %>">

        <div id="map"></div>

        <div class="button-container">
            <input type="hidden" name="eventName" value="<%= eventName %>">
            <input type="hidden" name="userName" value="<%= userName %>">
            <button type="submit" class="update-button">Update Pickup</button>
        </div>
        <button onclick="window.history.back()" class="update-button">Back</button>
    </form>
</div>

<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA84fzc-D-45OeGPHqeJ1e_F7kRgTBEASg&libraries=places"></script>
<script src="web/js/map.js"></script>
</body>
</html>

