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
            max-width: 600px; /* Increased max-width */
            width: 100%; /* Increased width */
            padding: 30px; /* Increased padding */
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
            display: block;
            text-align: center;
            margin-top: 10px;
            text-decoration: none;
            color: white;
            background-color: #4CAF50;
            padding: 10px 0;
            border-radius: 3px;
            border: 1px solid #ccc;
            cursor: pointer;
        }

        .forgot-password:hover {
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
            text-align: center;
        }

        .form-group {
            margin-bottom: 15px;
            width: 100%;
        }

        .error-message {
            color: red;
        }
        .btn {
            display: inline-block;
            padding: 15px 25px;
            background-color: #4CAF50;
            color: #fff;
            text-decoration: none;
            border-radius: 5px;
            margin: 10px 10px;
            transition: background-color 0.3s ease, transform 0.3s ease;
            font-size: 16px;
            font-weight: 500;
            border: none;
            cursor: pointer;
        }

        .btn:hover {
            background-color: #45A049;
            transform: translateY(-2px);
        }
        .error-message {
            color: red;
            text-align: center;
            margin-top: 10px;
        }
    </style>
</head>
<body>
<div class="form-container">
    <form action="loginFromLink" method="post">
        <h1>Login</h1>
        <%
            String eventId = request.getParameter("id");
            String eventOwner = request.getParameter("owner");
            //String eventOwner = request.getAttribute("eventOwner").toString();
            if (eventId != null && eventOwner != null) {
        %>
        <input type="hidden" name="id" value="<%= eventId %>">
        <input type="hidden" name="owner" value="<%= eventOwner %>">
        <div class="form-group">
            <label for="fullname">Full Name:</label>
            <input type="text" id="fullname" name="fullname" required>
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
        </div>
        <button class="btn" type="submit">Login</button>
        <a class="forgot-password" href="ForgotPasswordFromLink.jsp?id=<%= eventId %>&owner=<%= eventOwner %>">Forgot Password?</a>
        <span class="error-message">Password is incorrect</span>
        <%
        } else {
        %>
        <p>Error: Event ID not found.</p>
        <%
            }
        %>
    </form>
</div>
</body>
</html>
