<%@ page import="servlet.main.page.event.NewEventServlet" %>
<%@ page import="servlet.main.page.event.EditEventServlet" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="keywords" content="​We love ambitious wedding plans, ​Our events innovate, rather than follow trends, ​We create experiences that touch the heart, Contact Us">
    <meta name="description" content="">
    <title>Welcome to WeDrive</title>
    <link rel="stylesheet" href="indexPage/css/nicepage.css" media="screen">
    <link rel="stylesheet" href="indexPage/css/index.css" media="screen">
    <script class="u-script" type="text/javascript" src="indexPage/css/jquery-1.9.1.min.js" defer=""></script>
    <script class="u-script" type="text/javascript" src="indexPage/css/nicepage.js" defer=""></script>
    <meta name="generator" content="Nicepage 6.16.15, nicepage.com">
    <link id="u-theme-google-font" rel="stylesheet" href="https://fonts.googleapis.com/css?family=Playfair+Display:400,400i,500,500i,600,600i,700,700i,800,800i,900,900i|Open+Sans:300,300i,400,400i,500,500i,600,600i,700,700i,800,800i">
    <meta name="theme-color" content="#d4b6be">
    <meta property="og:title" content="Welcome">
    <meta property="og:type" content="website">
</head>
<body class="u-body u-xl-mode" data-lang="en">
<section class="u-clearfix u-valign-middle-xs u-section-1" id="sec-fa06">
    <div class="custom-expanded u-palette-1-light-2 u-shape u-shape-rectangle u-shape-1" data-animation-name="customAnimationIn" data-animation-duration="1500" data-animation-delay="500"></div>
    <img class="u-expanded-width u-image u-image-1" src="indexPage/images/5675-min.jpg" data-image-width="1980" data-image-height="1321" data-animation-name="customAnimationIn" data-animation-duration="1500">

    <div class="u-align-left u-container-style u-group u-white u-group-2" data-animation-name="customAnimationIn" data-animation-duration="1750" data-animation-delay="500">
        <div class="u-container-layout u-container-layout-2">
            <%
                String uuid = request.getParameter("id");
                String eventOwnerName = request.getParameter("owner");

                if (uuid != null && eventOwnerName != null) {
            %>
            <h1 class="u-text u-text-1">WeDrive</h1>
            <p class="u-text u-text-variant u-text-2">Glad to hear you are going to attend the "<%= uuid %>" event!</p>
            <p class="u-text u-text-variant u-text-3">Just One More Step...</p>
            <p class="u-text u-text-variant u-text-4">Please log in or register on the site.</p>
            <a href="signUpFromLink.jsp?id=<%= java.net.URLEncoder.encode(uuid, "UTF-8") %>&owner=<%= java.net.URLEncoder.encode(eventOwnerName, "UTF-8") %>" class="u-btn u-btn-1">Sign Up</a>
            <a href="loginFromLink.jsp?id=<%= java.net.URLEncoder.encode(uuid, "UTF-8") %>&owner=<%= java.net.URLEncoder.encode(eventOwnerName, "UTF-8") %>" class="u-btn u-btn-2">Login</a>
            <% } else { %>
            <p class="u-text u-text-variant u-text-danger">Invalid URL</p>
            <% } %>
        </div>
    </div>
</section>

<footer class="u-align-center u-footer u-grey-80 u-footer">
    <div class="u-clearfix u-sheet u-valign-middle u-sheet-1">
        <h3 class="u-align-center u-text u-text-default u-text-1">
            <a href="indexPage/css/WeDrive.pdf" class="u-active-none u-btn u-button-link u-text-palette-1-base">WeDrive - 2024 - SHARON, TOMER, EITAN</a>
        </h3>
    </div>
</footer>
</body>
</html>
