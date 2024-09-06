<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.temporal.ChronoUnit" %>
<%@ page import="entity.ServerClient" %>
<%@ page import="event.EventData" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="database.Users" %>
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
    <table>
        <tr>
            <th>Event Name</th>
            <th>Event Kind</th>
            <th>Date</th>
            <th>Location</th>
            <th>Days Till Event</th>
        </tr>
        <%
            String userName = (String) request.getSession().getAttribute("userName");
            ServerClient client = Users.getUserByUserName(userName);
            HashMap<String, EventData> events = client.getOwnedEvents();
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
        </tr>
        <% } %>
    </table>
    <div class="button-container">
        <a href="eventOwnerMenu.jsp" class="button">Back to Event Owner Menu</a>
    </div>
</div>
</body>
</html>
