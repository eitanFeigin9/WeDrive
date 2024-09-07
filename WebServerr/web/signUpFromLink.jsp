<!DOCTYPE html>
<html>
<head>
    <title>Sign Up</title>
    <link rel="stylesheet" type="text/css" href="loginSignupPage/signup.css">
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
    <div class="signup">
        <form action="signupFromLink" method="post">
            <input type="hidden" name="id" value="<%= request.getParameter("id") %>">
            <input type="hidden" name="owner" value="<%= request.getParameter("owner") %>">
            <%
                String eventId = request.getParameter("id");
                String eventOwner = request.getParameter("owner");
                //String eventOwner = request.getAttribute("eventOwner").toString();
                if (eventId != null && eventOwner != null) {
            %>
            <label for="chk" aria-hidden="true">Sign up</label>
            <input type="text" name="fullName" placeholder="Full Name" required>
            <input type="text" name="userName" placeholder="User Name" required>
            <input type="email" name="email" placeholder="Email" required>
            <input type="tel" name="phone" placeholder="Phone Number" required>
            <input type="password" name="password" placeholder="Password" required>
            <input type="text" name="securityAnswer" placeholder="What city were you born in?" required>
            <button type="submit">Sign up</button>
        </form>
        <% } else { %>
        <div class="container">
            <p class="invalid-url">Invalid URL</p>
        </div>
        <% } %>
    </div>
</div>
</body>
</html>
