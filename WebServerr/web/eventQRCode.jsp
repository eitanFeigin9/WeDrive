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
            max-width: 400px;
            margin: 20px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .qr-code {
            margin-top: 20px;
        }
    </style>
</head>
<%

    String eventUrl = (String) request.getSession().getAttribute("eventURL");
    String qrCode = (String) request.getSession().getAttribute("qrCodeURL");
%>
<body>
<div class="container">
    <h2>Event Created Successfully!</h2>
    <div class="qr-code">
        <img src="<%= qrCode %>" alt="QR Code">
        <p>Scan the QR code or <a href="<%= eventUrl %>">click here</a> to join the event.</p>
    </div>
    <a href="eventGuestsLink.jsp" class="button">View Event Details</a> </div>
</body>
</html>
