<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Join A Ride</title>
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
            max-width: 400px; /* Keeping the current width */
            width: 100%;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 10px;
            background-color: #f9f9f9;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        h1 {
            color: #4CAF50;
            margin-bottom: 20px;
            font-size: 20px;
            text-align: center;
        }

        label {
            display: block;
            margin-bottom: 6px;
            font-weight: 500;
            font-size: 14px;
        }

        input[type="text"],
        select {
            width: 100%;
            padding: 8px 10px;
            margin: 6px 0 14px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
            font-size: 14px;
        }

        input[type="submit"],
        button[type="button"] {
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

        input[type="submit"]:hover,
        button[type="button"]:hover {
            background-color: #45a049;
        }

        .form-group {
            margin-bottom: 15px;
            width: 100%;
        }

        .error-message {
            color: red;
        }
    </style>
</head>
<body>
<div class="form-container">
    <form action="joinRide" method="post" enctype="multipart/form-data">
        <h1>Join A Ride</h1>
        <div class="form-group">
            <label for="eventName">Event Name:</label>
            <input type="text" id="eventName" name="eventName" required>
        </div>
        <div class="form-group">
            <label for="pickupCity">Pickup City:</label>
            <input type="text" id="pickupCity" name="pickupCity" required>
        </div>
        <div class="form-group">
            <label for="fuelMoney">Maximum Fuel Price You Are Willing To Pay:</label>
            <input type="text" id="fuelMoney" name="fuelMoney" required>
        </div>
        <input type="submit" value="Confirm">
    </form>
</div>
</body>
</html>

