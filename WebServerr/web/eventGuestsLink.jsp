<%@ page import="entity.ServerClient" %>
<%@ page import="database.Users" %>
<%@ page import="event.EventData" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>WeDrive - Event Details</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            margin: 0;
            padding: 0;
            text-align: center;
        }
        .container {
            max-width: 600px;
            margin: 20px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .button {
            display: inline-block;
            margin: 10px;
            padding: 10px 20px;
            background-color: #4CAF50;
            color: #fff;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
        .button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<%
    String userName = (String) request.getSession().getAttribute("userName");
    ServerClient client = Users.getUserByFullName(userName);
    String eventNamePar = request.getParameter("eventName");

%>
<body>
<div class="container">
    <h2>Glad to hear you are going to attend in the “<%= eventNamePar %>” event</h2>
    <a href="#" class="button">Are you interested in driving other guests and save on fuel costs?</a>
    <a href="#" class="button">Do you need transportation to the event?</a>
</div>
</body>
</html>
