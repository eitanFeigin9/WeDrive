<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.temporal.ChronoUnit" %>
<%@ page import="entity.ServerClient" %>
<%@ page import="event.EventData" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="database.Users" %>
<%@ page import="java.io.File" %>
<%@ page import="java.nio.file.Files" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Event Details</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #9a9093; /* Antique pink */
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: flex-start; /* Change to flex-start to avoid cutting off */
            min-height: 100vh; /* Use min-height instead of height */
        }
        .container {
            text-align: center;
            background-color: #ffffff;
            border-radius: 15px;
            padding: 40px 30px;
            box-shadow: 0 10px 15px rgba(0, 0, 0, 0.1);
            max-width: 900px;
            width: 100%;
            margin-top: 30px; /* Add margin at the top */
        }
        h1 {
            color: #cb9f9f;
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
            background-color: #cb9f9f;
            color: white;
        }
        td {
            color: #333;
        }
        td img {
            max-width: 100px;
            max-height: 100px;
        }
        td a {
            text-decoration: none;
            color: #cb9f9f;
        }
        td a:hover {
            text-decoration: underline;
        }
        /* Make the Date column wider */
        td:nth-child(3) {
            white-space: nowrap;
            width: 150px;
        }
        .qr-wrapper {
            margin-bottom: 10px;
        }
        .button-container {
            margin-top: 20px;
        }
        .button {
            padding: 12px 30px;
            background-color: #cb9f9f;
            color: #fff;
            text-decoration: none;
            border-radius: 8px;
            transition: background-color 0.3s ease, transform 0.3s ease;
            font-size: 16px;
        }
        .button:hover {
            background-color: #b08787;
            transform: translateY(-2px);
        }
    </style>
</head>
<body>
<div class="container">
    <!-- Big Title Header -->
    <h1>Event Details</h1>

    <%
        String userName = (String) request.getSession().getAttribute("userName");
        ServerClient client = Users.getUserByUserName(userName);
        request.getSession().setAttribute("client", client);  // Update session with the latest client object
        HashMap<String, EventData> events = client.getOwnedEvents();
    %>

    <table>
        <tr>
            <th>Event Name</th>
            <th>Event Kind</th>
            <th>Date</th>
            <th>Location</th>
            <th>Days Till Event</th>
            <th>QR Code</th>
            <th>Invitation Link</th>
        </tr>
        <%
            for (EventData event : events.values()) {
                LocalDate eventDate = LocalDate.parse(event.getEventDate());
                long daysTillEvent = ChronoUnit.DAYS.between(LocalDate.now(), eventDate);
        %>
        <tr>
            <td><%= event.getEventName() %></td>
            <td><%= event.getEventKind() %></td>
            <td><%= event.getEventDate() %></td>
            <td><%= event.getLocation() %></td>
            <td><%= daysTillEvent %></td>
            <td>
                <div class="qr-wrapper">
                    <img src="data:image/png;base64,<%= event.getQrCodeFilePath() %>" alt="QR Code">
                </div>
                <a href="data:image/png;base64,<%= event.getQrCodeFilePath() %>" download="event-qr-code.png">Download QR Code</a>
            </td>
            <td>
                <%
                    String invitationLink = event.getInvitationLink(); // Get the invitation link
                    if (invitationLink != null && !invitationLink.isEmpty()) {
                %>
                <a href="<%= invitationLink %>" target="_blank">Invitation Link</a>
                <%
                    } else {
                        out.println("No Invitation Link");
                    }
                %>
            </td>
        </tr>
        <% } %>
    </table>

    <div class="button-container">
        <a href="eventOwnerMenu.jsp" class="button">Back to Event Owner Menu</a>
    </div>
</div>
</body>
</html>




