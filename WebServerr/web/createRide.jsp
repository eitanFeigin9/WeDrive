<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Event</title>
    <link rel="stylesheet" type="text/css" href="newEvent/newEventStyle.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        #map {
            height: 400px;
            width: 100%;
        }
        h1 {
            color: white; /* Set title color to white */
        }

        /* Style for the input placeholders */
        .event-form__input::placeholder {
            color: white; /* Set placeholder text color to white */
            opacity: 0.8; /* Optional: slightly transparent */
        }

        /* Style for the Back button */
        .back-button {
            background-color: #f3e7e7; /* Red background */
            color: #2a2727; /* White text */
            border: none; /* No border */
            padding: 10px 20px; /* Padding */
            font-size: 16px; /* Font size */
            cursor: pointer; /* Pointer cursor on hover */
            border-radius: 5px; /* Rounded corners */
            margin-bottom: 20px; /* Space below the button */
            text-decoration: none; /* No underline */
        }

        .back-button:hover {
            background-color: #f3e5e5; /* Darker red on hover */
        }

    </style>
</head>
<body>
<div class="container">
    <div class="screen">
        <div class="screen__content">
            <%
                String eventId = request.getParameter("id");
                String eventOwner = request.getParameter("owner");
            %>
            <%
                String errorMessage = (String) request.getAttribute("errorMessage");
                if (errorMessage != null) {
            %>
            <div class="error-message">
                <p style="color: red;"><strong><%= errorMessage %></strong></p>
            </div>
            <%
                }
            %>
            <h1>Create New Ride For <%= eventId %></h1>

            <form action="createRide" method="post" enctype="multipart/form-data" class="event-form">
                <input type="hidden" id="eventName" name="eventName" value="<%= eventId %>">
                <input type="hidden" id="eventOwner" name="eventOwner" value="<%= eventOwner %>">
                <div class="event-form__field">
                    <i class="event-form__icon fas fa-car"></i>
                    <input type="range" id="maxCapacity" name="maxCapacity" min="1" max="50" value="1" oninput="updateCapacityValue(this.value)" class="event-form__input" required>
                    <span class="slider-label"><strong>Free seats: </strong><strong id="capacityValue">1</strong></span>
                </div>
                <div class="event-form__field">
                    <i class="event-form__icon fas fa-gas-pump"></i>
                    <input type="number" id="fuelReturns" name="fuelReturns" class="event-form__input" placeholder="Fuel Returns per passenger in $" min="0" required>
                </div>
                <div class="event-form__field">
                    <i class="event-form__icon fas fa-map-marker-alt"></i>
                    <input type="text" id="eventLocation" name="sourceLocation" class="event-form__input" placeholder="Source Address (your starting point)" required>
                </div>
                <div class="event-form__field">
                    <i class="event-form__icon fas fa-road"></i>
                    <input type="number" id="maxPickupDistance" name="maxPickupDistance" class="event-form__input" placeholder="Maximum KM for pickup from starting point" min="0" step="0.1" required>
                </div>
                <div id="map"></div>
                <input type="hidden" id="latitude" name="sourceLatitude">
                <input type="hidden" id="longitude" name="sourceLongitude">
                <button type="submit" class="button event-form__submit">
                    <span class="button__text">Create new ride for the event!</span>
                    <i class="button__icon fas fa-check"></i>
                </button>
            </form>
        </div>
        <div class="screen__background">
            <span class="screen__background__shape screen__background__shape4"></span>
            <span class="screen__background__shape screen__background__shape3"></span>
            <span class="screen__background__shape screen__background__shape2"></span>
            <span class="screen__background__shape screen__background__shape1"></span>
        </div>
    </div>
</div>
<script>
    function updateCapacityValue(value) {
        document.getElementById('capacityValue').innerText = value;
    }
</script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA84fzc-D-45OeGPHqeJ1e_F7kRgTBEASg&libraries=places"></script>
<script src="newEvent/map2.js"></script>
</body>
</html>
