<%@ page import="servlet.main.page.event.NewEventServlet" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Welcome to WeDrive</title>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap');

        body {
            font-family: 'Roboto', sans-serif;
            background-color: #e0e0e0;
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
            border-radius: 10px;
            padding: 40px 20px;
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
            max-width: 500px;
            width: 100%;
        }

        h1 {
            color: #4CAF50;
            margin-bottom: 20px;
            font-weight: 700;
        }

        .event-message {
            color: #333;
            margin-bottom: 10px;
            font-weight: 500;
            font-size: 18px;
        }

        .instructions {
            color: #333;
            margin-bottom: 20px;
            font-weight: 500;
            font-size: 16px;
        }

        .btn {
            display: inline-block;
            padding: 15px 25px;
            background-color: #4CAF50;
            color: #fff;
            text-decoration: none;
            border-radius: 5px;
            margin: 10px 10px;
            transition: background-color 0.3s ease, transform 0.3s ease;
            font-size: 16px;
            font-weight: 500;
            border: none;
            cursor: pointer;
        }

        .btn:hover {
            background-color: #45A049;
            transform: translateY(-2px);
        }

        .invalid-url {
            color: #d9534f;
            font-size: 18px;
        }

        .btn-container {
            margin-top: 30px;
        }
    </style>
</head>
<body>
<%
    String uuid = request.getParameter("id");
    String name = NewEventServlet.getNameMap().get(uuid);
    request.setAttribute("currId", uuid);
    if (name != null) {
%>
<div class="container">
    <h1>WeDrive</h1>
    <h2 class="event-message">Glad to hear you are going to attend the "<%= name %>" event</h2>
    <h2 class="instructions">Just One More Step...</h2>
    <h2 class="instructions">Please log in or register on the site</h2>
    <div class="btn-container">
        <button class="btn" onclick="window.location.href='signUpFromLink.jsp'">Sign Up</button>
        <button class="btn" onclick="window.location.href='loginFromLink.jsp'">Login</button>
    </div>
</div>
<% } else { %>
<div class="container">
    <p class="invalid-url">Invalid URL</p>
</div>
<% } %>
</body>
</html>
