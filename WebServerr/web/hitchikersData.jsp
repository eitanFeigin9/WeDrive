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
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            color: #333;
            margin: 0;
            padding: 20px;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }

        .container {
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            max-width: 800px;
            width: 100%;
            margin: 0 auto;
        }

        h2 {
            font-size: 24px;
            color: #0b3f60;
            text-align: center;
            margin-bottom: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }

        th, td {
            text-align: left;
            padding: 12px;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #f2f2f2;
            color: #555;
        }

        tr:hover {
            background-color: #f1f1f1;
        }

        .button-container {
            text-align: center;
            margin-top: 20px;
        }

        .button {
            background-color: #0b3f60;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            font-size: 16px;
            transition: background-color 0.3s ease;
        }

        .button:hover {
            background-color: #0f5b8c;
        }
    </style>
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
</body>
</html>

