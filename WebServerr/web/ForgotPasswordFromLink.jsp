<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Recover Password</title>
    <link rel="stylesheet" type="text/css" href="ForgotPassword/forgotStyle.css">
    <!-- Include FontAwesome for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body>
<div class="container">
    <div class="screen">
        <div class="screen__content">
            <form action="recoverFromLink" method="post" class="login">
                <%
                    String eventId = request.getParameter("id");
                    String eventOwner = request.getParameter("owner");
                    //String eventOwner = request.getAttribute("eventOwner").toString();
                    if (eventId != null && eventOwner != null) {

                %>
                <input type="hidden" name="id" value="<%= eventId %>">
                <input type="hidden" name="owner" value="<%= eventOwner %>">
                <!-- Username input field -->
                <div class="login__field">
                    <i class="login__icon fas fa-user"></i>
                    <input type="text" id="userName" name="userName" class="login__input" placeholder="Username" required>
                </div>
                <!-- Security answer input field -->
                <div class="login__field">
                    <i class="login__icon fas fa-question-circle"></i>
                    <input type="text" id="securityAnswer" name="securityAnswer" class="login__input" placeholder="Your birth city:" required>
                </div>
                <!-- Hidden fields for event ID and owner -->

                <button type="submit" class="button login__submit">
                    <span class="button__text">Recover password</span>
                    <i class="button__icon fas fa-chevron-right"></i>
                </button>
                <%
                } else {
                %>
                <p>Error: Event ID not found.</p>
                <%
                    }
                %>
            </form>
        </div>
        <div class="screen__background">
            <span class="screen__background__shape screen__background__shape4"></span>
            <span class="screen__background__shape screen__background__shape3"></span>
            <span class="screen__background__shape screen__background__shape2"></span>
            <span class="screen__background__shape screen__background__shape1"></span>
        </div>
    </div>
</div>
</body>
</html>
