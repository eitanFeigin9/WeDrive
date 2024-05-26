<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Montserrat', sans-serif;
            background-color: #f2f2f2;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background-image: url('weDrivePoster.jpeg');
            background-size: cover;
            background-position: center;
        }
        .overlay {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.5);
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .login-container {
            background-color: #fff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            text-align: center;
            max-width: 400px;
            width: 90%;
        }
        h1 {
            margin-top: 0;
            color: #16CEE3;
            font-size: 2em;
        }
        h2 {
            margin-top: 10px;
            color: #333;
            font-size: 1.2em;
        }
        input[type="text"],
        input[type="password"],
        input[type="submit"] {
            width: calc(100% - 20px);
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 3px;
            box-sizing: border-box;
            font-size: 16px;
        }
        input[type="submit"] {
            background-color: #16CEE3;
            color: #fff;
            border: none;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        input[type="submit"]:hover {
            background-color: #16CEE3;
        }
        .btn-container {
            display: flex;
            justify-content: space-between;
            margin-top: 20px;
        }
        .btn-container button {
            width: 48%;
            padding: 10px;
            background-color: #16CEE3;
            color: #fff;
            border: none;
            border-radius: 3px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        .btn-container button:hover {
            background-color: #16CEE3;
        }
    </style>
</head>
<body>
<div class="overlay">
    <div class="login-container">
        <h1>Welcome to WeDrive</h1>
        <h2>Creating a transportation experience for guests</h2>
        <div class="btn-container">
            <button onclick="window.location.href='signup.jsp'">Sign Up</button>
            <button onclick="window.location.href='login.jsp'">Login</button>
        </div>
    </div>
</div>
</body>
</html>