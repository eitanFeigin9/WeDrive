<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Event Owner Menu</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .menu-container {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            text-align: center;
            max-width: 400px;
            width: 100%;
        }

        h1 {
            color: #4CAF50;
            font-size: 32px;
            margin-bottom: 30px;
        }

        .menu-container button {
            width: 100%;
            padding: 15px;
            margin: 10px 0;
            font-size: 18px;
            color: #333;
            background-color: #F8EBE8;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .menu-container button:hover {
            background-color: #F8EBE8;
        }

        .back-button {
            color: #4CAF50;
            background-color: #4CAF50;
        }

        .back-button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
<div class="menu-container">
    <h1>Event Owner Menu</h1>
    <button onclick="window.location.href='newEvent.jsp'">New Event</button>
    <button onclick="window.location.href='history.jsp'">History</button>
    <button onclick="window.location.href='editDeleteEvent.jsp'">Edit/Delete Event</button>
    <button class="back-button" onclick="window.location.href='mainPage.jsp'">Back to Main Menu</button>
</div>
</body>
</html>
