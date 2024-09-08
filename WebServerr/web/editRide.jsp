<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Pickup</title>
    <link rel="stylesheet" type="text/css" href="newEvent/newEventStyle.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        #map {
            height: 400px;
            width: 100%;
        }
        .container {
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            background-color: #f2f2f2;
            padding: 20px;
        }
        .screen {
            display: flex;
            flex-direction: column;
            align-items: center;
            width: 100%;
            max-width: 800px;
            background: white;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
            padding: 20px;
            position: relative;
        }
        .screen__content {
            width: 100%;
            text-align: center;
        }
        .screen__content h1 {
            color: #333;
            margin-bottom: 20px;
        }
        .event-form__field {
            margin-bottom: 15px;
            position: relative;
        }
        .event-form__input {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 16px;
        }
        .event-form__icon {
            position: absolute;
            left: 10px;
            top: 50%;
            transform: translateY(-50%);
            color: #999;
        }
        .button.event-form__submit {
            background-color: #4CAF50;
            color: white;
            padding: 12px 24px;
            border-radius: 5px;
            border: none;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s;
            display: inline-block;
            margin-top: 10px;
        }
        .button.event-form__submit:hover {
            background-color: #45a049;
        }
        .error-message {
            color: red;
            margin-bottom: 15px;
        }
        .screen__background {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            overflow: hidden;
            z-index: -1;
        }
        .screen__background__shape {
            position: absolute;
            border-radius: 50%;
            background: rgba(0, 0, 0, 0.1);
        }
    </style>
</head>
<body>
<div class="container">
    <div class="screen">
        <div class="screen__content">
            <%
                String eventName = request.getParameter("eventName");
                String errorMessage = (String) request.getAttribute("errorMessage");
            %>
            <%
                if (errorMessage != null) {
            %>
            <div class="error-message">
                <p><strong><%= errorMessage %></strong></p>
            </div>
            <%
                }
            %>
            <h1>Edit your ride For <%= eventName %> Event</h1>

            <form action="editRide" method="post" class="event-form">
                <input type="hidden" id="eventName" name="eventName" value="<%= eventName %>">
                <div class="event-form__field">
                    <input type="number" id="maxCapacity" name="maxCapacity" class="event-form__input" placeholder="Max free seats capacity" required>
                </div>
                <div class="event-form__field">
                    <input type="text" id="eventLocation" name="sourceLocation" class="event-form__input" placeholder="Starting point address" required>
                </div>
                <div class="event-form__field">
                    <input type="number" id="fuelReturns" name="fuelReturns" class="event-form__input" placeholder="Fuel Returns (per hitchhiker) in $" required>
                </div>
                <div class="event-form__field">
                    <input type="number" id="maxPickupDistance" name="maxPickupDistance" class="event-form__input" placeholder="Maximum KM to pick up a passenger" min="0" required>
                </div>
                <div id="map"></div>
                <input type="hidden" id="latitude" name="sourceLatitude">
                <input type="hidden" id="longitude" name="sourceLongitude">
                <button type="submit" class="button event-form__submit">
                    Edit your driving ride!
                </button>
            </form>
        </div>
        <div class="screen__background">
            <span class="screen__background__shape screen__background__shape1"></span>
            <span class="screen__background__shape screen__background__shape2"></span>
            <span class="screen__background__shape screen__background__shape3"></span>
            <span class="screen__background__shape screen__background__shape4"></span>
        </div>
    </div>
</div>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA84fzc-D-45OeGPHqeJ1e_F7kRgTBEASg&libraries=places"></script>
<script src="newEvent/map2.js"></script>
</body>
</html>
