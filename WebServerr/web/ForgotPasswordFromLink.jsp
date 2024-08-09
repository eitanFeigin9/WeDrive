<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Password Recovery</title>
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
            max-width: 600px; /* Increased max-width for consistency */
            width: 80%; /* Adjusted width for consistency */
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 10px;
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
        input[type="submit"] {
            width: 100%;
            padding: 8px 10px;
            margin: 6px 0 14px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
            font-size: 14px;
        }

        input[type="submit"] {
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

        .form-group {
            margin-bottom: 15px;
            width: 100%;
        }

        .error-message {
            color: red;
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
    <form action="recoverFromLink" method="post">
        <h1>Password Recovery</h1>
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
            <label for="securityAnswer">What city were you born in?</label>
            <input type="text" id="securityAnswer" name="securityAnswer" required>
        </div>
        <input type="submit" value="Recover Password">
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
