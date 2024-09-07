<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="loginSignupPage/login.css">
    <link href="https://fonts.googleapis.com/css2?family=Jost:wght@500&display=swap" rel="stylesheet">
    <script>
        // Function to show error popup
        function showError(message) {
            alert(message); // Simple alert for now, can replace with a custom popup later
        }

        // Show error if provided in URL
        window.onload = function() {
            const params = new URLSearchParams(window.location.search);
            if (params.has('error')) {
                showError(params.get('error'));
            }
        }
    </script>
</head>
<body>
<div class="main">
    <div class="login">
        <form action="loginFromLink" method="post">
            <%
                String eventId = request.getParameter("id");
                String eventOwner = request.getParameter("owner");
                //String eventOwner = request.getAttribute("eventOwner").toString();
                if (eventId != null && eventOwner != null) {
            %>
            <input type="hidden" name="id" value="<%= eventId %>">
            <input type="hidden" name="owner" value="<%= eventOwner %>">
            <label aria-hidden="true">Login</label>
            <input type="text" name="username" placeholder="Username" required>
            <input type="password" name="password" placeholder="Password" required>
            <button type="submit">Login</button>
            <a href="ForgotPasswordFromLink.jsp?id=<%= eventId %>&owner=<%= eventOwner %>" class="forgot-password">Forgot your password?</a>
            <%
            } else {
            %>
            <p>Error: Event ID not found.</p>
            <%
                }
            %>
        </form>
    </div>
</div>
</body>
</html>
