
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>New Event</title>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@400;500&display=swap');

        body {
            font-family: 'Roboto', sans-serif;
            background-color: #e0e0e0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        form {
            max-width: 350px;
            width: 90%;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 10px;
            background-color: #ffffff;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        label {
            display: block;
            margin-bottom: 6px;
            font-weight: 500;
            font-size: 14px;
        }

        input[type="text"],
        input[type="email"],
        input[type="tel"],
        input[type="password"],
        input[type="date"],
        input[type="file"] {
            width: 100%;
            padding: 8px 10px;
            margin: 6px 0 14px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
            font-size: 14px;
        }

        input[type="submit"] {
            width: 100%;
            padding: 10px 0;
            background-color: #4CAF50;
            color: #ffffff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            font-weight: 500;
            transition: background-color 0.3s ease;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }

        .form-container {
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        h1 {
            color: #4CAF50;
            margin-bottom: 20px;
            font-size: 20px;
        }

        .form-group {
            margin-bottom: 15px;
            width: 100%;
        }
    </style>
</head>
<body>
<div class="form-container">
    <form action="createEvent" method="post" enctype="multipart/form-data">
        <h1>Create New Event</h1>
        <div class="form-group">
            <label for="eventName">Event Name:</label>
            <input type="text" id="eventName" name="eventName" required>
        </div>
        <div class="form-group">
            <label for="eventDate">Date:</label>
            <input type="date" id="eventDate" name="eventDate" required>
        </div>
        <div class="form-group">
            <label for="eventKind">Event Kind:</label>
            <input type="text" id="eventKind" name="eventKind" required>
        </div>
        <div class="form-group">
            <label for="guestList">Upload Guest List:</label>
            <input type="file" id="guestList" name="guestList" accept=".csv">
        </div>
        <div class="form-group">
            <label for="eventLocation">Location:</label>
            <input type="text" id="eventLocation" name="eventLocation" required>
        </div>
        <input type="submit" value="Confirm">
    </form>
</div>
</body>
</html>
