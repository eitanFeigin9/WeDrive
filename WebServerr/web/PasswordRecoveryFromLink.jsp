<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Password Recovery</title>
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
        }
        .container {
            max-width: 400px;
            width: 100%;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            background-color: #fff;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            text-align: center;
        }
        .container h1 {
            margin-top: 0;
            font-size: 24px;
            color: #4CAF50;
        }
        .container p {
            font-size: 16px;
            color: #333;
            margin: 10px 0;
        }
        .container .password {
            font-weight: bold;
            color: #000;
            background-color: #eee;
            padding: 10px;
            border-radius: 3px;
            margin: 10px 0;
        }
        .container a {
            display: block;
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 3px;
            text-align: center;
        }
        .container a:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Password Recovery</h1>
    <%
        String eventId = (String)request.getAttribute("id");
        String eventOwner = (String) request.getAttribute("owner");
        //String eventOwner = request.getAttribute("eventOwner").toString();
        if (eventId != null && eventOwner != null) {
    %>
    <input type="hidden" name="id" value="<%= eventId %>">
    <input type="hidden" name="owner" value="<%= eventOwner %>">
    <p>Your password is:</p>
    <p class="password">${password}</p>
    <a href="loginFromLink.jsp?id=<%= eventId %>&owner=<%= eventOwner %>">Back to Login</a>
    <%
    } else {
    %>
    <p>Error: Event ID not found.</p>
    <%
        }
    %>
</div>
</body>
</html>
