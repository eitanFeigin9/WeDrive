<%@ page import="ride.DriverRide, database.Users, java.util.Map" %>
<%@ page import="event.EventData" %>
<%@ page import="ride.HitchhikerRide" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    String userName = request.getParameter("userName");
    String eventName = request.getParameter("eventName");

    // Fetch event and ride details
    DriverRide currRide = Users.findSpecificDriver(userName, eventName);
    EventData currEvent = Users.getEventsMap().get(eventName);

    String eventDate = currEvent.getEventDate();
    String eventLocation = currEvent.getLocation();

    // Fetch hitchhiker details
    Map<String, HitchhikerRide> hitchhikerRideMap = currRide.getHitchhikersForThisRide();
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hitchhikers Data</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/hitchhikerMatch/style.css">
</head>
<body>
<div class="container">
    <h2>Event Details</h2>

    <table>
        <tr>
            <th>Event Name</th>
            <th>Event Address</th>
            <th>Event Date</th>
        </tr>
        <tr>
            <td><%= eventName %></td>
            <td><%= eventLocation %></td>
            <td><%= eventDate %></td>
        </tr>
    </table>

    <h2>Hitchhikers</h2>

    <table>
        <tr>
            <th>Hitchhiker Name</th>
            <th>Phone Number</th>
            <th>Pickup Address</th>
        </tr>
        <%
            for (Map.Entry<String, HitchhikerRide> entry : hitchhikerRideMap.entrySet()) {
                String hitchhikerName = entry.getKey();
                String hitchhikerPhoneNumber = Users.getUserByFullName(entry.getKey()).getPhoneNumber();
                String hitchhikerAddress = entry.getValue().getPickupLocation();
        %>
        <tr>
            <td><%= hitchhikerName %></td>
            <td><%= hitchhikerPhoneNumber %></td>
            <td><%= hitchhikerAddress %></td>
        </tr>
        <% } %>
    </table>

    <div class="button-container">
        <a href="mainPage.jsp" class="button">Back to Main Page</a>
    </div>
</div>

<script src="${pageContext.request.contextPath}/hitchhikerMatch/script.js"></script>
</body>
</html>
