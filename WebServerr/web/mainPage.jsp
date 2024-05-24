<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>weDrive</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
            color: #333;
        }
        header {
            background-color: #4CAF50;
            color: white;
            text-align: center;
            padding: 1em 0;
        }
        .container {
            width: 80%;
            margin: 20px auto;
        }
        .section {
            background: white;
            border-radius: 10px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            margin: 20px 0;
            padding: 20px;
            text-align: center;
        }
        .section h2 {
            margin-top: 0;
        }
        .section p {
            font-size: 1.1em;
        }
        .button {
            display: inline-block;
            padding: 15px 25px;
            font-size: 16px;
            margin: 20px 10px;
            cursor: pointer;
            border: none;
            border-radius: 5px;
            color: white;
        }
        .button-green { background-color: #4CAF50; }
        .button-blue { background-color: #2196F3; }
        .button-orange { background-color: #FF9800; }
        footer {
            background-color: #333;
            color: white;
            text-align: center;
            padding: 1em 0;
            position: fixed;
            width: 100%;
            bottom: 0;
        }
        footer a {
            color: #ddd;
            margin: 0 10px;
            text-decoration: none;
        }
    </style>
</head>
<body>
<header>
    <h1>weDrive</h1>
    <p>Simplifying Your Travel Experience</p>
</header>

<div class="container">
    <div class="section">
        <h2>As an Event Owner</h2>
        <p>Organize and manage your event's transportation needs.</p>
        <a href="eventOwnerMenu.jsp" class="button button-green">Get Started</a>
    </div>

    <div class="section">
        <h2>As a Driver</h2>
        <p>Offer rides and connect with passengers.</p>
        <button class="button button-blue">Start Driving</button>
    </div>

    <div class="section">
        <h2>As a Hitchhiker</h2>
        <p>Find rides to your favorite events.</p>
        <button class="button button-orange">Find a Ride</button>
    </div>
</div>

<footer>
    <p>&copy; 2024 weDrive. All Rights Reserved.</p>
    <a href="#">About Us</a> |
    <a href="#">Contact</a> |
    <a href="#">Terms of Service</a> |
    <a href="#">Privacy Policy</a> |
    <a href="#">Help Center</a>
</footer>
</body>
</html>
