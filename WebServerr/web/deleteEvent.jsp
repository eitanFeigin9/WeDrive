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
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f2f2f2;
        }
        .container {
            max-width: 400px;
            margin: 0 auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            background-color: #fff;
            text-align: center;
        }
        .confirmation-message {
            margin-bottom: 20px;
        }
        .button-container {
            text-align: center;
            margin-top: 20px;
        }
        .button {
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            border-radius: 5px;
            cursor: pointer;
            background-color: #f44336;
            color: white;
            border: none;
        }
        .button:hover {
            opacity: 0.8;
        }
    </style>
</head>
<body>
<%
    String eventName = request.getParameter("eventName");
%>
<div class="container">
    <p class="confirmation-message">Are you sure you want to delete the event "<%= eventName %>"?</p>
    <form action="deleteEvent" method="post">
        <input type="hidden" name="eventName" value="<%= eventName %>">
        <button type="submit" class="button">Yes, Delete Event</button>
    </form>
    <div class="button-container">
        <a href="editOrDeleteEvent.jsp" class="button">Cancel</a>
    </div>
</div>
</body>
</html>
