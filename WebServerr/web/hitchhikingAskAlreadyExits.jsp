<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.net.URLEncoder" %>
<%
    String eventName = request.getParameter("eventName");
    String userName = request.getParameter("userName");
    //  String encodedEventName = URLEncoder.encode(eventName, "UTF-8");
    //String encodedUserName = URLEncoder.encode(userName, "UTF-8");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ride Already Created</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background: linear-gradient(135deg, #ff7e5f, #feb47b);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            color: white;
        }
        .container {
            background-color: rgba(255, 255, 255, 0.1);
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            text-align: center;
            max-width: 500px;
        }
        h1 {
            font-size: 2.5rem;
            margin-bottom: 20px;
        }
        p {
            font-size: 1.2rem;
            margin-bottom: 30px;
        }
        .button {
            background-color: #fff;
            color: #ff7e5f;
            border: none;
            padding: 15px 25px;
            border-radius: 5px;
            font-size: 1rem;
            cursor: pointer;
            margin: 10px;
            text-decoration: none;
            display: inline-block;
            transition: all 0.3s ease;
        }
        .button:hover {
            background-color: #ff7e5f;
            color: white;
        }
        .button-container {
            display: flex;
            justify-content: center;
            flex-wrap: wrap;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Ride Already Created!</h1>
    <p>You already created a hitchhiker request to the <strong><%= eventName %></strong> event!</p>
    <div class="button-container">
        <a href="hitchhikerMenu.jsp?eventName=<%= eventName %>&userName=<%= userName %>" class="button">
            For details on your next driving events, press here!
        </a>
    </div>
</div>
</body>
</html>
