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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/editDelEvent/style.css">


</head>
<body>
<div class="container">
    <h2>Event Details</h2>

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
