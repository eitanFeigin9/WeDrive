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
            background-color: #fff;
            border-radius: 10px;
            padding: 40px 20px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            max-width: 500px;
            width: 100%;
        }
        h1 {
            color: #4CAF50;
            margin-bottom: 20px;
        }
        h2 {
            color: #333;
            margin-bottom: 30px;
        }
        .btn {
            display: inline-block;
            padding: 15px 25px;
            background-color: #4CAF50;
            color: #fff;
            text-decoration: none;
            border-radius: 5px;
            margin: 10px 0;
            transition: background-color 0.3s ease, transform 0.3s ease;
            font-size: 16px;
        }
        .btn:hover {
            background-color: #45a049;
            transform: translateY(-2px);
        }
        .btn.main-menu {
            margin-top: 30px; /* Add extra margin to push it further down */
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
    <a href="createRide.jsp" class="btn">Are you interested in driving other guests and saving on fuel costs?</a>
    <a href="joinRide.jsp" class="btn">Do you need transportation to the event?</a>
    <a href="mainPage.jsp" class="btn main-menu">Main Menu</a>
</div>
</body>
</html>
