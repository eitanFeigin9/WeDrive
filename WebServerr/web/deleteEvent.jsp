<%@ page import="entity.ServerClient" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="database.Users" %>
<%@ page import="event.EventData" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Delete Event</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/editDelEvent/style.css">
</head>
<body>
<div class="container">
    <h2>Delete Event</h2>
    <p class="confirmation-message">Are you sure you want to delete the event "<%= request.getParameter("eventName") %>"?</p>
    <form action="deleteEvent" method="post">
        <input type="hidden" name="eventName" value="<%= request.getParameter("eventName") %>">
        <button type="submit" class="button delete-button">Yes, Delete Event</button>
    </form>
    <div class="button-container">
        <a href="editOrDeleteEvent.jsp" class="button">Cancel</a>
    </div>
</div>
</body>
</html>
