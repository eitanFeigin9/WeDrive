<%@ page import="entity.ServerClient" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="database.Users" %>
<%@ page import="event.EventData" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit or Delete Event</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/editDelEvent/style.css">
</head>
<body>
<div class="container">
    <h2>Edit or Delete Event</h2>
    <table>
        <tr>
            <th>Event Name</th>
            <th>Actions</th>
        </tr>
        <%
            String userName = (String) request.getSession().getAttribute("userName");
            ServerClient client = Users.getUserByUserName(userName);
            HashMap<String, EventData> events = client.getOwnedEvents();
            for (String eventName : events.keySet()) {
        %>
        <tr>
            <td><%= eventName %></td>
            <td>
                <form action="editEvent.jsp" method="post" style="display:inline;">
                    <input type="hidden" name="eventName" value="<%= eventName %>">
                    <button type="submit" class="button edit-button">Edit</button>
                </form>
                <form action="deleteEvent.jsp" method="post" style="display:inline;">
                    <input type="hidden" name="eventName" value="<%= eventName %>">
                    <button type="submit" class="button delete-button">Delete</button>
                </form>
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
