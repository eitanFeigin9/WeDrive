<%@ page import="servlet.main.page.event.NewEventServlet" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Welcome to WeDrive</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f0f0f0;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .container {
            text-align: center;
            background-color: #ffffff;
            border-radius: 15px;
            padding: 40px 30px;
            box-shadow: 0 10px 15px rgba(0, 0, 0, 0.1);
            max-width: 500px;
            width: 100%;
        }
        h1 {
            color: #2a3f54;
            font-size: 32px;
            margin-bottom: 15px;
        }
        h2 {
            color: #333;
            margin-bottom: 25px;
        }
        .btn {
            display: inline-block;
            padding: 15px 30px;
            background-color: #9a9093;
            color: #fff;
            text-decoration: none;
            border-radius: 8px;
            margin: 10px 0;
            transition: background-color 0.3s ease, transform 0.3s ease;
            font-size: 16px;
            cursor: pointer;
        }
        .btn:hover {
            background-color: #9a9093;
            transform: translateY(-2px);
        }
        .btn.main-menu {
            display: block;
            width: 80%; /* Full width for the main menu button */
            margin-top: 30px; /* Add space between other buttons */
            background-color: #5d6d7e;
        }
        .btn.main-menu:hover {
            background-color: #4e5a6c;
        }
        .invalid-url {
            color: #d9534f;
            font-size: 18px;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>WeDrive</h1>
    <%
        String eventId = request.getParameter("id");
        String eventOwner = request.getParameter("owner");
        if (eventId != null && eventOwner != null) {
    %>
    <button class="btn" onclick="window.location.href='createRide.jsp?id=<%= java.net.URLEncoder.encode(eventId, "UTF-8") %>&owner=<%= java.net.URLEncoder.encode(eventOwner, "UTF-8") %>'">
        Interested in driving other guests and saving on fuel?
    </button>
    <button class="btn" onclick="window.location.href='joinRide.jsp?id=<%= java.net.URLEncoder.encode(eventId, "UTF-8") %>&owner=<%= java.net.URLEncoder.encode(eventOwner, "UTF-8") %>'">
        Need transportation to the event?
    </button>
    <a href="mainPage.jsp" class="btn main-menu">Main Menu</a>
    <%
    } else {
    %>
    <p class="invalid-url">Error: Event ID not found.</p>
    <%
        }
    %>
</div>
</body>
</html>

