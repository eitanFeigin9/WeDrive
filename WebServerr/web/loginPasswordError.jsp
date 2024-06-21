<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registration Form</title>
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
            max-width: 400px;
            width: 90%;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            background-color: #f9f9f9;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        label {
            display: block;
            margin-bottom: 6px;
            font-weight: 500;
            font-size: 14px;
        }

        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
            margin: 5px 0;
            border: 1px solid #ccc;
            border-radius: 3px;
            box-sizing: border-box;
            font-size: 14px;
        }

        input[type="submit"] {
            width: 100%;
            background-color: #4CAF50;
            color: white;
            padding: 10px 0;
            border: none;
            border-radius: 3px;
            cursor: pointer;
            font-size: 14px;
            font-weight: 500;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }

        .forgot-password {
            width: 100%;
            background-color: #4CAF50;
            color: white;
            padding: 10px 0;
            border: none;
            border-radius: 3px;
            cursor: pointer;
            text-align: center;
            margin-top: 10px;
            font-size: 14px;
            font-weight: 500;
        }

        .forgot-password:hover {
            background-color: #45a049;
        }

        .error-message {
            color: red;
            text-align: center;
            margin-top: 10px;
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
            text-align: center;
        }
    </style>
</head>
<body>
<div class="form-container">
    <form action="login" method="post">
        <h1>Login</h1>
        <label for="fullname">Full Name:</label>
        <input type="text" id="fullname" name="fullname" required>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>

        <input type="submit" value="Login">

        <button type="button" class="forgot-password" onclick="window.location.href='forgotPassword.jsp'">Forgot Password?</button>

        <span class="error-message">Password is incorrect</span>
    </form>
</div>
</body>
</html>