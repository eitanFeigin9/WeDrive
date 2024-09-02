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
        .error-message {
            color: red;
            text-align: center;
            margin-top: 10px;
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
    <h1>Edit Pickup For The <%= request.getParameter("eventName") %> Event</h1>
    <form action="editRide" method="post" class="form-container">
        <label for="maxCapacity">Max Capacity:</label><br>
        <input type="number" id="maxCapacity" name="maxCapacity" required><br>
        <label for="pickupCity">Pickup City:</label><br>
        <input type="text" id="pickupCity" name="pickupCity" required><br>
        <label for="fuelReturns">Fuel Returns (per hitchhiker):</label><br>
        <input type="number" id="fuelReturns" name="fuelReturns" required><br>
        <label for="maxPickupDistance">Maximum KM you are willing to drive to pick up a passenger:</label><br>
        <input type="number" id="maxPickupDistance" name="maxPickupDistance" min="0" required><br>
        <div class="notice">Notice: If you have lowered the max pickup distance - your hitchhikers might be affected</div>
        <input type="hidden" id="latitude" name="latitude">
        <input type="hidden" id="longitude" name="longitude">
        <div id="map"></div>
        <form action="editRide" method="post" style="display:inline;">
            <input type="hidden" name="eventName" value="<%= request.getParameter("eventName") %>">
            <button type="submit" class="button update-button">Edit the Pickup</button>
        </form>
        <span class="error-message">Max Capacity and Fuel Returns Need To Be A Number</span>
    </form>
</div>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA84fzc-D-45OeGPHqeJ1e_F7kRgTBEASg&libraries=places"></script>
<script src="web/js/map.js"></script>
</body>
</html>
