<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.temporal.ChronoUnit" %>
<%@ page import="entity.ServerClient" %>
<%@ page import="event.EventData" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="database.Users" %>
<%@ page import="ride.DriverRide" %>
<%@ page import="java.util.Map" %>
<%@ page import="ride.HitchhikerDetails" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Driver Menu</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f2f2f2;
        }
        h2 {
            text-align: center;
            color: #333;
            margin-top: 20px;
        }
        table {
            width: 80%;
            margin: 20px auto;
            border-collapse: collapse;
            background-color: #fff;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
        }
        th, td {
            padding: 12px 15px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #f2f2f2;
            font-weight: bold;
            color: #333;
        }
        tr:hover {
            background-color: #f5f5f5;
        }
        tr:nth-child(even) {
            background-color: #fafafa;
        }
        .button-container {
            text-align: center;
            margin-top: 20px;
        }
        .button {
            padding: 8px 15px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            border-radius: 5px;
            cursor: pointer;
            background-color: #4CAF50;
            color: white;
            border: none;
            margin-right: 10px;
        }
        .button.delete-button {
            background-color: #f44336;
        }
        .button.edit-button {
            background-color: #4CAF50;
        }
        .button:hover {
            opacity: 0.8;
        }
    </style>
</head>
<body>
<h2>Driver Current Rides</h2>
<table>
    <tr>
        <th>Event Name</th>
        <th>Event Address</th>
        <th>Starting Point Address</th>
        <th>Current Amount Of Hitchhikers</th>
        <th>Max Capacity</th>
        <th>Fuel Return Per Hitchhiker</th>
        <th>Total fuel returns</th>
        <th>Maximum KM for Pickup</th>
        <th>Hitchhiker Name</th>
        <th>Hitchhiker Phone</th>
        <th>Hitchhiker Address</th>
    </tr>
    <%
        String userName = (String)request.getSession().getAttribute("userName");
        ServerClient client = Users.getUserByFullName(userName);
        HashMap<String, DriverRide> rides = client.getDrivingEvents();
        for (DriverRide ride : rides.values()) {
            String currEventName = ride.getEventName();
            String currEventAddress = ride.getEventAddress();
            HashMap<String, HitchhikerDetails> hitchhikers = ride.getCurrentHitchhikers();
            int numHitchhikers = hitchhikers.size();
    %>
    <tr>
        <td><%= currEventName %></td>
        <td><%= currEventAddress %></td>
        <td><%= ride.getPickupCity() %></td>
        <td><%= ride.getCurrNumOfHitchhikers() %></td>
        <td><%= ride.getMaxCapacity() %></td>
        <td><%= ride.getFuelReturnsPerHitchhiker() %></td>
        <td><%= ride.getTotalFuelReturns() %></td>
        <td><%= ride.getMaxPickupDistance() %></td>
        <td>
            <%
                for (HitchhikerDetails hitchhiker : hitchhikers.values()) {
                    out.println(hitchhiker.getName() + "<br>");
                }
            %>
        </td>
        <td>
            <%
                for (HitchhikerDetails hitchhiker : hitchhikers.values()) {
                    out.println(hitchhiker.getPhone() + "<br>");
                }
            %>
        </td>
        <td>
            <%
                for (HitchhikerDetails hitchhiker : hitchhikers.values()) {
                    out.println(hitchhiker.getAddress() + "<br>");
                }
            %>
        </td>
        <td>
            <button class="button"
                    onclick="openMapPopup(
                            '<%= String.valueOf(ride.getLatitude()) %>',
                            '<%= String.valueOf(ride.getLongitude()) %>',
                            '<%= ride.getCurrentHitchhikers() %>',
                            '<%= String.valueOf(ride.getEventLatitude()) %>',
                            '<%= String.valueOf(ride.getEventLongitude()) %>'
                            )">Show Map</button>
        </td>
        <td>
            <a href="editRide.jsp?eventName=<%= currEventName %>" class="button edit-button">Edit</a>
        </td>
        <td>
            <a href="cancelRide.jsp?eventName=<%= currEventName %>" class="button delete-button">Cancel</a>
        </td>
    </tr>
    <% } %>
</table>
<div id="mapModal" style="display:none;">
    <div>
        <span onclick="closeMapPopup()" style="cursor:pointer;">&times; Close</span>
        <div id="map" style="height: 500px; width: 100%;"></div>
    </div>
</div>
<div class="button-container">
    <a href="driverOptionsMenu.jsp" class="button">Back to Driver Menu</a>
</div>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA84fzc-D-45OeGPHqeJ1e_F7kRgTBEASg"></script>
<script src="web/js/mapPopup.js"></script>
</body>
</html>
