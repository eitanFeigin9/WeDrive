<%@ page import="servlet.main.page.event.NewEventServlet" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create New Ride</title>
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
            color: #4CAF50;
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
        input[type="submit"], button {
            padding: 12px 24px;
            margin: 10px 5px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            color: white;
            background-color: #4CAF50;
            transition: background-color 0.3s;
        }
        input[type="submit"]:hover, button:hover {
            background-color: #45a049;
        }
        #map {
            width: 100%;
            height: 300px;
            margin-top: 20px;
            border-radius: 5px;
        }
    </style>
</head>
<body>
<div class="container">
    <%
        String eventId = request.getParameter("id");
        String eventOwner = request.getParameter("owner");
        if (eventId != null && eventOwner != null) {
    %>
    <h1>Create New Ride For <%= eventId %></h1>
    <form action="createRide" method="post" enctype="multipart/form-data">
        <input type="hidden" id="eventName" name="eventName" value="<%= eventId %>">
        <input type="hidden" id="eventOwner" name="eventOwner" value="<%= eventOwner %>">
        <label for="pickupCity">Pickup Address (your starting point):</label><br>
        <input type="text" id="pickupCity" name="pickupCity" placeholder="Enter an address" required><br>
        <label for="maxCapacity">Number of available seats in your car:</label><br>
        <input type="number" id="maxCapacity" name="maxCapacity" min="0" max="40" required><br>
        <label for="fuelReturns">Fuel Returns per passenger:</label><br>
        <input type="number" id="fuelReturns" name="fuelReturns" min="0" required><br>
        <label for="maxPickupDistance">Maximum KM you are willing to drive to pick up a passenger:</label><br>
        <input type="number" id="maxPickupDistance" name="maxPickupDistance" min="0" required><br>
        <input type="hidden" id="latitude" name="latitude">
        <input type="hidden" id="longitude" name="longitude">
        <div id="map"></div>
        <input type="submit" value="Confirm">
    </form>
    <%
    } else {
    %>
    <p>Error: Event ID or owner not found.</p>
    <%
        }
    %>
</div>

<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA84fzc-D-45OeGPHqeJ1e_F7kRgTBEASg&libraries=places"></script>
<script src="web/js/map.js"></script>
</body>
</html>


