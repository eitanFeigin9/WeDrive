<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Join A Ride</title>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@400;500&display=swap');

        body {
            font-family: 'Roboto', sans-serif;
            background-color: #f3ecec; /* Light background color */
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .form-container {
            max-width: 450px;
            width: 100%;
            padding: 20px;
            border-radius: 15px;
            background-color: #625896; /* Form background color */
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
        }

        h1 {
            color: #eceaea; /* Title color */
            margin-bottom: 20px;
            font-size: 24px;
            text-align: center;
        }

        label {
            display: block;
            margin-bottom: 8px;
            font-weight: 500;
            font-size: 15px;
            color: #f5f3f3; /* Label color */
        }

        input[type="text"],
        select {
            width: 100%;
            padding: 10px;
            margin: 6px 0 14px;
            border: 1px solid #C39898; /* Input border color */
            border-radius: 8px;
            box-sizing: border-box;
            font-size: 15px;
        }

        /* Style for the fuelMoney input */
        .fuel-money-input {
            display: flex;
            align-items: center; /* Center the icon and text */
            border: 1px solid #C39898; /* Input border color */
            border-radius: 8px;
            overflow: hidden; /* Prevents border-radius overflow */
            margin: 6px 0 14px;
        }

        .fuel-money-input input[type="number"] {
            flex: 1; /* Takes remaining space */
            padding: 10px;
            border: none; /* No border for the input */
            font-size: 15px;
            outline: none; /* Removes default outline */
        }

        .fuel-money-input .fuel-icon {
            padding: 10px; /* Icon padding */
            background-color: #f8f7f7; /* Icon background color */
            border-right: 1px solid #C39898; /* Right border */
            display: flex;
            align-items: center; /* Center the icon vertically */
            justify-content: center; /* Center the icon horizontally */
            color: #2a2727; /* Icon color */
        }

        input[type="submit"],
        button[type="button"] {
            width: 100%;
            padding: 10px 0;
            background-color: #f8f7f7; /* Submit button color */
            color: #2a2727;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-size: 16px;
            font-weight: 500;
            transition: background-color 0.3s ease;
        }

        input[type="submit"]:hover,
        button[type="button"]:hover {
            background-color: #6754a8; /* Submit button hover color */
        }

        .form-group {
            margin-bottom: 15px;
            width: 100%;
        }

        .error-message {
            color: red;
            font-size: 14px;
            text-align: center;
        }

        #map {
            width: 100%;
            height: 200px; /* Smaller map height */
            margin-top: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
        }
    </style>
</head>

<body>
<div class="form-container">
    <%
        String eventId = request.getParameter("id");
        String eventOwner = request.getParameter("owner");
        if (eventId != null && eventOwner != null) {
    %>
    <form action="joinRide" method="post" enctype="multipart/form-data">
        <h1>Join A Ride To The <%= eventId %> Event</h1>
        <input type="hidden" id="eventName" name="eventName" value="<%= eventId %>">
        <input type="hidden" id="eventOwner" name="eventOwner" value="<%= eventOwner %>">
        <div class="form-group">
            <label for="eventLocation">Pickup Address:</label>
            <input type="text" id="eventLocation" name="eventLocation" placeholder="Enter a pickup address:" required>
        </div>
        <div class="form-group">
            <label for="fuelMoney">Maximum Fuel Price You Are Willing To Pay (in $):</label>
            <div class="fuel-money-input">
                <div class="fuel-icon">
                    <i class="fas fa-gas-pump"></i> <!-- Fuel icon -->
                </div>
                <input type="number" id="fuelMoney" name="fuelMoney" placeholder="Enter Maximum Fuel Price" min="0" required>
            </div>
        </div>
        <input type="hidden" id="latitude" name="latitude">
        <input type="hidden" id="longitude" name="longitude">
        <div id="map"></div>
        <input type="submit" value="Confirm">
    </form>
    <%
    } else {
    %>
    <p class="error-message">Error: Event ID or owner not found.</p>
    <%
        }
    %>
</div>

<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA84fzc-D-45OeGPHqeJ1e_F7kRgTBEASg&libraries=places"></script>
<script src="newEvent/map2.js"></script>
<script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script> <!-- Font Awesome for icons -->
</body>

</html>




