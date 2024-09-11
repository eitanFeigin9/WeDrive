<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="entity.ServerClient" %>
<%@ page import="event.EventData" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="database.Users" %>
<%@ page import="ride.HitchhikerRide" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Organize Events Transportation</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: flex-start;
            min-height: 100vh;
        }
        .container {
            background-color: #ffffff;
            border-radius: 15px;
            padding: 40px 30px;
            box-shadow: 0 10px 15px rgba(0, 0, 0, 0.1);
            max-width: 1200px;
            width: 100%;
            margin-top: 30px;
        }
        h2 {
            text-align: center;
            color: #333;
            font-size: 36px;
            margin-bottom: 30px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        th, td {
            padding: 12px 15px;
            text-align: center;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #9a9093;
            color: white;
        }
        td {
            background-color: #f9f9f9;
            color: #333;
        }
        .area-row {
            background-color: #e0f7fa;
        }
        ul {
            list-style-type: none;
            padding-left: 0;
        }
        li {
            margin-bottom: 10px;
        }
        .button-container {
            text-align: center;
            margin-top: 20px;
        }
        .button {
            padding: 12px 30px;
            background-color: #9a9093;
            color: white;
            text-decoration: none;
            border-radius: 8px;
            transition: background-color 0.3s ease, transform 0.3s ease;
        }
        .button:hover {
            background-color: #b08787;
            transform: translateY(-2px);
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Event Details</h2>

    <%
        String userName = (String) request.getSession().getAttribute("userName");
        ServerClient client = Users.getUserByUserName(userName);
        request.getSession().setAttribute("client", client);
        HashMap<String, EventData> events = client.getOwnedEvents();
        HashMap<String, HashMap<String, List<HitchhikerRide>>> hitchhikersWithoutRide= new HashMap<>();

        // Process the hitchhikers for each event
        for (EventData eventData : events.values()) {
            List<HitchhikerRide> hitchhikerRides = Users.getHitchhikersRideBySpecificEvent(eventData.getEventName());
            if (hitchhikerRides != null && !hitchhikerRides.isEmpty()) {
                for (HitchhikerRide ride : hitchhikerRides) {
                    if (ride.getFreeForPickup()) {
                        hitchhikersWithoutRide
                                .computeIfAbsent(eventData.getEventName(), k -> new HashMap<>())
                                .computeIfAbsent(ride.getArea(), k -> new ArrayList<>())
                                .add(ride);
                    }
                }
            }
        }
    %>

    <table>
        <tr>
            <th>Event Name</th>
            <th>Event Kind</th>
            <th>Event Date</th>
            <th>Event Location</th>
            <th>Hitchhikers Area</th>
            <th>Number of Guests Without a Ride</th>
            <th>Transportation Company</th>
            <th>Company Phone</th>
            <th>Guests Details (Full Name, Phone Number)</th>
        </tr>

        <%
            // Display the events and hitchhikers by area
            for (EventData eventData : events.values()) {
                HashMap<String, List<HitchhikerRide>> hitchhikersForEvent = hitchhikersWithoutRide.get(eventData.getEventName());
                int rowCount = (hitchhikersForEvent != null) ? hitchhikersForEvent.size() + 1 : 1;
        %>
        <tr>
            <td rowspan="<%= rowCount %>"><%= eventData.getEventName() %></td>
            <td rowspan="<%= rowCount %>"><%= eventData.getEventKind() %></td>
            <td rowspan="<%= rowCount %>" style="width: 150px;"><%= eventData.getEventDate() %></td>
            <td rowspan="<%= rowCount %>"><%= eventData.getLocation() %></td>
        </tr>

        <%
            HashMap<String, List<HitchhikerRide>> areasMap = hitchhikersWithoutRide.get(eventData.getEventName());
            if (areasMap != null) {
                for (Map.Entry<String, List<HitchhikerRide>> areaEntry : areasMap.entrySet()) {
                    String area = areaEntry.getKey();
                    List<HitchhikerRide> hitchhikersInArea = areaEntry.getValue();
                    int numberOfGuests = hitchhikersInArea.size();
                    String transportationCompany = "";
                    String transportationCompanyPhone = "";
                    if (area.equals("Upper Galilee")) {
                        transportationCompany = "הסעות המוביל";
                        transportationCompanyPhone ="076-8607197";
                    } else if (area.equals("Lower Galilee")) {
                        transportationCompany = "רותם האירוסים";
                        transportationCompanyPhone ="04-6561993";
                    } else if (area.equals("Sharon")) {
                        transportationCompany = "דורון הסעות";
                        transportationCompanyPhone ="052-4499807";
                    } else if (area.equals("Shfela")) {
                        transportationCompany = "גמבורג הסעות";
                        transportationCompanyPhone ="*9074";
                    } else if (area.equals("Negev")) {
                        transportationCompany = "רון טורס";
                        transportationCompanyPhone ="072-2646928";
                    } else if (area.equals("Arava")) {
                        transportationCompany = "מטיילי קשת אילת";
                        transportationCompanyPhone ="08-6330333";
                    } else if (area.equals("Judea and Samaria")) {
                        transportationCompany = "הסעות המלכים";
                        transportationCompanyPhone ="054-6378422";
                    } else if (area.equals("Unknown Region")) {
                        transportationCompany = "מסיעי שאשא";
                        transportationCompanyPhone ="03-5584044";
                    }
        %>

        <tr class="area-row">
            <td><%= area %></td>
            <td><%= numberOfGuests %></td>
            <td><%= transportationCompany %></td>
            <td><%= transportationCompanyPhone %></td>
            <td>
                <ul>
                    <%
                        for (HitchhikerRide hitchhiker : hitchhikersInArea) {
                            String hitchhikerUserName = hitchhiker.getHitchhikerUserName();
                            String fullName = Users.getUserByUserName(hitchhikerUserName).getFullName();
                            String phoneNumber = Users.getUserByUserName(hitchhikerUserName).getPhoneNumber();
                    %>
                    <li><%= fullName %> <br> <%= phoneNumber %></li>
                    <%
                        }
                    %>
                </ul>
            </td>
        </tr>

        <%
                    }
                }
            }
        %>

    </table>

    <div class="button-container">
        <a href="eventOwnerMenu.jsp" class="button">Back to Event Owner Menu</a>
    </div>
</div>
</body>
</html>


