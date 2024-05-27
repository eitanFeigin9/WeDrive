<%@ page import="servlet.main.page.event.NewEventServlet" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create New Ride</title>
    <style>
        form {
            max-width: 400px;
            margin: 50px auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            background-color: #f9f9f9;
        }
        h1 {
            color: #4CAF50;
            margin-bottom: 20px;
        }
        input[type="text"],
        select {
            width: 100%;
            padding: 10px;
            margin: 5px 0;
            border: 1px solid #ccc;
            border-radius: 3px;
            box-sizing: border-box;
        }
        input[type="submit"],
        button[type="button"] {
            width: 100%;
            background-color: #4CAF50;
            color: white;
            padding: 10px 0;
            border: none;
            border-radius: 3px;
            cursor: pointer;
        }
        input[type="submit"]:hover,
        button[type="button"]:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
<form action="createRide" method="post" enctype="multipart/form-data">
    <h1>Create New Ride</h1>
    <label for="eventName">Event Name:</label>
    <input type="text" id="eventName" name="eventName" required>

    <label for="maxCapacity">Max Capacity:</label>
    <input type="text" id="maxCapacity" name="maxCapacity" required>

    <label for="pickupCity">Pickup City:</label>
    <input type="text" id="pickupCity" name="pickupCity" required>

    <label for="fuelReturns">Fuel Returns:</label>
    <input type="text" id="fuelReturns" name="fuelReturns" required>

    <input type="submit" value="Confirm">

</form>
</body>
</html>
