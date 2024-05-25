
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>New Event</title>
    <style>
        form {
            max-width: 400px;
            margin: 50px auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            background-color: #f9f9f9;
        }
        input[type="text"],
        input[type="email"],
        input[type="tel"],
        input[type="password"],
        input[type="file"] {
            width: 100%;
            padding: 10px;
            margin: 5px 0;
            border: 1px solid #ccc;
            border-radius: 3px;
            box-sizing: border-box;
        }
        input[type="submit"] {
            width: 100%;
            background-color: #4CAF50;
            color: white;
            padding: 10px 0;
            border: none;
            border-radius: 3px;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
<form action="createEvent" method="post" enctype="multipart/form-data">
    <label for="eventName">Event Name:</label>
    <input type="text" id="eventName" name="eventName" required>

    <label for="eventDate">Date:</label>
    <input type="date" id="eventDate" name="eventDate" required>

    <label for="eventKind">Event Kind:</label>
    <input type="text" id="eventKind" name="eventKind" required>

    <label for="guestList">Upload Guest List:</label>
    <input type="file" id="guestList" name="guestList" accept=".csv">

    <label for="eventLocation">Location:</label>
    <input type="text" id="eventLocation" name="eventLocation" required>

    <input type="submit" value="Confirm">
</form>
</body>
</html>
