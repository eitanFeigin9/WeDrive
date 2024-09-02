<%@ page import="event.EventData" %>
<%@ page import="entity.ServerClient" %>
<%@ page import="database.Users" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Event</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f0f2f5;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .form-container {
            background-color: #fff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 500px;
            box-sizing: border-box;
        }
        .form-container h1 {
            text-align: center;
            margin-bottom: 20px;
            color: #333;
            font-size: 24px;
        }
        .form-container label {
            display: block;
            margin-bottom: 8px;
            color: #333;
            font-weight: bold;
        }
        .form-container input[type="text"],
        .form-container input[type="date"],
        .form-container .file-upload {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 16px;
            box-sizing: border-box;
            transition: border-color 0.3s;
        }
        .form-container input[type="text"]:focus,
        .form-container input[type="date"]:focus {
            border-color: #4CAF50;
            outline: none;
        }
        .file-upload {
            position: relative;
            display: inline-block;
            overflow: hidden;
            background-color: #f9f9f9;
            border: 1px solid #ccc;
            border-radius: 5px;
            height: 40px;
            line-height: 40px;
            cursor: pointer;
        }
        .file-upload input[type="file"] {
            width: 100%;
            height: 100%;
            position: absolute;
            top: 0;
            left: 0;
            opacity: 0;
            cursor: pointer;
        }
        .file-upload .file-info {
            padding: 0 10px;
            height: 40px;
            line-height: 30px; /* Adjusted line height */
            display: inline-block;
            color: #666;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            box-sizing: border-box;
            position: relative;
            top: -5px; /* Adjusted position */
        }
        .form-container input[type="submit"] {
            width: 100%;
            background-color: #4CAF50;
            color: #fff;
            padding: 12px 0;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        .form-container input[type="submit"]:hover {
            background-color: #45a049;
        }
        .error-message {
            color: red;
            text-align: center;
            margin-top: 10px;
        }
        #map {
            width: 100%;
            height: 300px;
            margin-top: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
    </style>
</head>
<%
    String userName = (String) request.getSession().getAttribute("userName");
    ServerClient client = Users.getUserByFullName(userName);
    String eventNamePar = request.getParameter("eventName");
    EventData event = client.getOwnedEventByName(eventNamePar);
    request.getSession().setAttribute("eventOldName", eventNamePar);
    String uploadedFileName = event.getFileName();
    if (uploadedFileName == null || uploadedFileName.isEmpty()) {
        uploadedFileName = ""; // Set empty string for no initial file
    }
%>
<body>
<div class="form-container">
    <h1>Edit Event</h1>
    <form action="editEvent" method="post" enctype="multipart/form-data">
        <label for="eventName">Event Name:</label>
        <input type="text" id="eventName" name="eventName" value="<%= event.getEventName() %>" required>

        <label for="eventDate">Date:</label>
        <input type="date" id="eventDate" name="eventDate" value="<%= event.getEventDate() %>" required>

        <label for="eventKind">Event Kind:</label>
        <input type="text" id="eventKind" name="eventKind" value="<%= event.getEventKind() %>" required>

        <label for="guestList">Upload Guest List:</label>
        <div class="file-upload">
            <input type="file" id="guestList" name="guestList" accept=".csv" onchange="updateFileName(this)">
            <div class="file-info"><%= uploadedFileName %></div>
        </div>

        <label for="eventLocation">Location:</label>
        <input type="text" id="eventLocation" name="eventLocation" value="<%= event.getLocation() %>" required>

        <input type="hidden" id="latitude" name="latitude" value="<%= event.getLatitude() %>">
        <input type="hidden" id="longitude" name="longitude" value="<%= event.getLongitude() %>">
        <div id="map"></div>

        <input type="submit" value="Update">

        <span class="error-message">Event With This Name Already Exists</span>
    </form>
</div>
<script>
    function updateFileName(input) {
        var fileName = input.files[0] ? input.files[0].name : "No file chosen";
        document.querySelector('.file-info').textContent = fileName;
    }
</script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA84fzc-D-45OeGPHqeJ1e_F7kRgTBEASg&libraries=places"></script>
<script src="web/js/map2.js"></script>
</body>
</html>
