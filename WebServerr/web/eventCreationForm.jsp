<!DOCTYPE html>
<html>
<head>
    <title>Create Event</title>
</head>
<body>
<h1>Create Event</h1>
<form action="http://localhost:8080/weDrive/EventCreationServlet" method="post">
    Event Name: <input type="text" name="eventName"><br>
    Event Date: <input type="date" name="eventDate"><br>
    <input type="submit" value="Create Event">
</form>
</body>
</html>