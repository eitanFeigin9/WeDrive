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
    <link rel="stylesheet" href="https://unpkg.com/leaflet/dist/leaflet.css" />
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
        <label for="pickupCity">Pickup City or Address:</label><br>
        <input type="text" id="pickupCity" name="pickupCity" placeholder="Enter a city or address" required><br>
        <label for="maxCapacity">Max Capacity:</label><br>
        <input type="number" id="maxCapacity" name="maxCapacity" min="0" max="40" required><br>
        <label for="fuelReturns">Fuel Returns:</label><br>
        <input type="number" id="fuelReturns" name="fuelReturns" min="0" required><br>
        <input type="hidden" id="latitude" name="latitude">
        <input type="hidden" id="longitude" name="longitude">
        <input type="submit" value="Confirm">
    </form>
    <div id="map"></div>
    <%
    } else {
    %>
    <p>Error: Event ID or owner not found.</p>
    <%
        }
    %>
</div>

<script src="https://unpkg.com/leaflet/dist/leaflet.js"></script>
<script>
    // Initialize the map centered on Israel
    var map = L.map('map').setView([31.0461, 34.8516], 8); // Coordinates for Israel

    // Load OSM tiles
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(map);

    var marker;

    // Event listener for map clicks
    map.on('click', function(e) {
        var lat = e.latlng.lat;
        var lon = e.latlng.lng;

        // Place or move marker on the map
        if (marker) {
            marker.setLatLng([lat, lon]);
        } else {
            marker = L.marker([lat, lon]).addTo(map);
        }

        // Set the hidden fields for lat/lon
        document.getElementById("latitude").value = lat;
        document.getElementById("longitude").value = lon;
    });

    // Geocode the address entered by the user
    document.getElementById("pickupCity").addEventListener("blur", function() {
        var address = this.value;

        fetch('https://nominatim.openstreetmap.org/search?format=json&q=' + encodeURIComponent(address) + '&countrycodes=IL&addressdetails=1&limit=1')
            .then(response => response.json())
            .then(data => {
                if (data.length > 0) {
                    var lat = data[0].lat;
                    var lon = data[0].lon;

                    // Center the map on the found location
                    map.setView([lat, lon], 13);

                    // Place or move marker on the map
                    if (marker) {
                        marker.setLatLng([lat, lon]);
                    } else {
                        marker = L.marker([lat, lon]).addTo(map);
                    }

                    // Set the hidden fields for lat/lon
                    document.getElementById("latitude").value = lat;
                    document.getElementById("longitude").value = lon;
                } else {
                    alert("Address not found. Please try again or select the location on the map.");
                }
            })
            .catch(error => console.error('Error:', error));
    });
</script>
</body>
</html>

