<%@ page import="ride.DriverRide, database.Users" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    String hitchhikerName = request.getParameter("hitchhikerName");
    String eventName = request.getParameter("eventName");

    // Get the driver username and current ride
    String driverUserName = Users.getUserByUserName(hitchhikerName).getHitchhikingEventByName(eventName).getDriverName();
    DriverRide currRide = Users.findSpecificDriver(driverUserName, eventName);

    // Fetch driver details
    String driverFullName = currRide.getDriverFullName();
    String driverPhone = currRide.getDriverPhoneNumber();
    String eventAddress = currRide.getEventAddress();
    int fuelReturns = currRide.getFuelReturnsPerHitchhiker();

    // Set the username in the session
    request.getSession().setAttribute("userName", hitchhikerName);
    request.getSession().setAttribute("eventName", eventName);

%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Driver Match</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/hitchhikerMatch/style.css">
</head>
<body>
<div class="container">
    <h2>Driver Match for Event: <%= eventName %></h2>

    <table>
        <tr>
            <th>Driver Name</th>
            <th>Driver Phone Number</th>
            <th>Event Address</th>
            <th>Fuel Money Contribution</th>
        </tr>
        <tr>
            <td><%= driverFullName %></td>
            <td><%= driverPhone %></td>
            <td><%= eventAddress %></td>
            <td>$<%= fuelReturns %></td>
        </tr>
    </table>

    <div class="button-container">
        <!-- Button that redirects to mainPage.jsp -->
        <a href="mainPage.jsp" class="button">Back to Main Page</a>
    </div>
</div>

<script src="${pageContext.request.contextPath}/hitchhikerMatch/script.js"></script>
</body>
</html>
