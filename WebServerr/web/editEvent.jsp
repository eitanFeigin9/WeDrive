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
    <link rel="stylesheet" type="text/css" href="newEvent/newEventStyle.css">
    <!-- Include FontAwesome for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<%
    String userName = (String) request.getSession().getAttribute("userName");
    ServerClient client = Users.getUserByUserName(userName);
    String eventNamePar = request.getParameter("eventName");
    EventData event = client.getOwnedEventByName(eventNamePar);
    request.getSession().setAttribute("eventOldName", eventNamePar);
    String uploadedFileName = event.getFileName();
    if (uploadedFileName == null || uploadedFileName.isEmpty()) {
        uploadedFileName = ""; // Set empty string for no initial file
    }
%>
<body>
<div class="container">
    <div class="screen">
        <div class="screen__content">
            <h1>Edit Event</h1>
            <form action="editEvent" method="post" enctype="multipart/form-data" class="event-form">
                <!-- Display error message if passed from servlet -->
                <%
                    String errorMessage = (String) request.getAttribute("errorMessage");
                    if (errorMessage != null) {
                %>
                <div class="error-message">
                    <p style="color: red;"><strong><%= errorMessage %></strong></p>
                </div>
                <%
                    }
                %>

                <div class="event-form__field">
                    <i class="event-form__icon fas fa-calendar-day"></i>
                    <input type="text" id="eventName" name="eventName" class="event-form__input" placeholder="Event Name" value="<%= event.getEventName() %>" required>
                </div>
                <div class="event-form__field">
                    <i class="event-form__icon fas fa-calendar-alt"></i>
                    <input type="date" id="eventDate" name="eventDate" class="event-form__input" value="<%= event.getEventDate() %>" required>
                </div>
                <div class="event-form__field">
                    <i class="event-form__icon fas fa-map-marker-alt"></i>
                    <input type="text" id="eventLocation" name="eventLocation" class="event-form__input" placeholder="Event Location" value="<%= event.getLocation() %>" required>
                </div>
                <div class="event-form__field">
                    <i class="event-form__icon fas fa-tag"></i>
                    <select id="eventKind" name="eventKind" class="event-form__input" placeholder="Event Kind" value="<%= event.getEventKind() %>" required>
                        <option value="" disabled selected>Select event kind</option>
                        <option value="Wedding">Wedding</option>
                        <option value="Bar Mitzvah">Bar Mitzvah</option>
                        <option value="Bat Mitzvah">Bat Mitzvah</option>
                        <option value="Birthday party">Birthday party</option>
                        <option value="Ritual circumcision">Ritual circumcision</option>
                        <option value="Conference">Conference</option>
                        <option value="Other">Other</option>
                    </select>
                </div>
                <div class="event-form__field">
                    <i class="event-form__icon fas fa-file-upload"></i>
                    <input type="file" id="guestList" name="guestList" class="event-form__input" accept=".csv" onchange="updateFileName(this)">
                    <div class="file-info"><%= event.getFileName() %></div>
                </div>
                <div id="map"></div>
                <input type="hidden" id="latitude" name="latitude" value="<%= event.getLatitude() %>">
                <input type="hidden" id="longitude" name="longitude" value="<%= event.getLongitude() %>">
                <button type="submit" class="button event-form__submit">
                    <span class="button__text">Update Event</span>
                    <i class="button__icon fas fa-check"></i>
                </button>
                <button onclick="window.history.back()" class="button event-form__submit">Back</button>
            </form>
        </div>
        <div class="screen__background">
            <span class="screen__background__shape screen__background__shape4"></span>
            <span class="screen__background__shape screen__background__shape3"></span>
            <span class="screen__background__shape screen__background__shape2"></span>
            <span class="screen__background__shape screen__background__shape1"></span>
        </div>
    </div>
</div>

<script>
    function updateFileName(input) {
        var fileName = input.files[0] ? input.files[0].name : "No file chosen";
        document.querySelector('.file-info').textContent = fileName;
    }
</script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA84fzc-D-45OeGPHqeJ1e_F7kRgTBEASg&libraries=places"></script>
<script src="newEvent/map2.js"></script>
</body>
</html>
