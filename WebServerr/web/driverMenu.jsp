<%@ page import="java.util.HashMap" %>
<%@ page import="ride.DriverRide" %>
<%@ page import="entity.ServerClient" %>
<%@ page import="database.Users" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Your Driving Events</title>
    <link rel="stylesheet" href="editDelEvent/style.css"> <!-- Link to your CSS file -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body>
<div class="container">
    <h2>Your Driving Events</h2>
    <table>
        <thead>
        <tr>
            <th>Event Name</th>
            <th>Event Address</th>
            <th>Free Seats</th>
            <th>Max Capacity</th>
            <th>Starting Point</th>
            <th>Fuel Returns per Hitchhiker ($)</th>
            <th>Max Pickup Distance</th>
            <th>Is a Match</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <%
            String userName = (String) request.getSession().getAttribute("userName");
            ServerClient client = Users.getUserByUserName(userName);
            HashMap<String, DriverRide> rides = client.getDrivingEvents();

            if (rides == null || rides.isEmpty()) {
        %>
        <tr>
            <td colspan="9">You don't have driving events yet.</td>
        </tr>
        <%
        } else {
            for (DriverRide ride : rides.values()) {
                String eventName = ride.getEventName();
                String eventAddress = ride.getEventAddress();
                int freeSeats = ride.getFreeCapacity();
                int maxCapacity = ride.getMaxCapacity();
                String startingPoint = ride.getSourceLocation();
                int fuelReturns = ride.getFuelReturnsPerHitchhiker();
                double maxPickupDistance = ride.getMaxPickupDistance();
                boolean isMatch = ride.isMatch();
        %>
        <tr>
            <td><%= eventName %></td>
            <td><%= eventAddress %></td>
            <td><%= freeSeats %></td>
            <td><%= maxCapacity %></td>
            <td><%= startingPoint %></td>
            <td><%= fuelReturns %></td>
            <td><%= maxPickupDistance %></td>
            <td>
                <%
                    if (isMatch) {
                %>
                <a href="hitchikersData.jsp?eventName=<%= eventName %>&userName=<%= userName %>">Yes</a>
                <%
                } else {
                %>
                <span>No</span>
                <%
                    }
                %>
            </td>
            <td>
                <form action="editRide.jsp?eventName=<%= eventName %>&userName=<%= userName %>" method="get" style="display:inline;">
                    <input type="hidden" name="eventName" value="<%= eventName %>">
                    <button type="submit" class="button edit-button">Edit</button>
                </form>
                <form action="cancelRide.jsp?eventName=<%= eventName %>&userName=<%= userName %>" method="get" style="display:inline;">
                    <input type="hidden" name="eventName" value="<%= eventName %>">
                    <button type="submit" class="button delete-button">Delete</button>
                </form>
            </td>
        </tr>
        <%
                }
            }
        %>
        </tbody>
    </table>
    <div class="button-container">
        <a href="driverOptionsMenu.jsp" class="button">Back to Driver Options Menu</a>
    </div>
</div>
</body>
</html>