
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cancel Pickup</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .container {
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
            text-align: center;
            max-width: 400px;
        }
        h1 {
            color: #333;
            font-size: 22px;
            margin-bottom: 20px;
        }
        p {
            color: #333;
            font-size: 16px;
            margin-bottom: 20px;
        }
        .button {
            padding: 10px 20px;
            margin: 10px 5px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
        }
        .cancel-button {
            background-color: #f44336;
            color: white;
        }
        .back-button {
            background-color: #4CAF50;
            color: white;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Cancel the pickup for the <%= request.getParameter("eventName") %> event</h1>
    <p>Your passengers will be in trouble,<br>are you sure you want to cancel the pickup for the <%= request.getParameter("eventName") %> event?</p>
    <form action="cancelRide" method="post" style="display:inline;">
        <input type="hidden" name="eventName" value="<%= request.getParameter("eventName") %>">
        <input type="hidden" name="userName" value="<%= request.getParameter("userName") %>">
        <button type="submit" class="button cancel-button">Cancel the Pickup</button>
    </form>
    <button onclick="window.history.back()" class="button back-button">Back</button>
</div>
</body>
</html>
