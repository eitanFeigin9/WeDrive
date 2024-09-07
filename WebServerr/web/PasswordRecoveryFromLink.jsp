<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html style="font-size: 16px;" lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
    <meta name="keywords" content="987-654-321 or 456-789-321, Taxi transfers from and to Airport, Yellow Cab NYC, Book your trip online now">
    <meta name="description" content="">
    <title>Password Recovery Success</title>
    <link rel="stylesheet" href="passwordRecoverSuccess/nicepage.css" media="screen">
    <link rel="stylesheet" href="passwordRecoverSuccess/recover-password.css" media="screen">
    <script class="u-script" type="text/javascript" src="passwordRecoverSuccess/jquery-1.9.1.min.js" defer=""></script>
    <script class="u-script" type="text/javascript" src="passwordRecoverSuccess/nicepage.js" defer=""></script>
    <meta name="generator" content="Nicepage 6.16.15, nicepage.com">
    <meta name="referrer" content="origin">
    <link id="u-theme-google-font" rel="stylesheet" href="https://fonts.googleapis.com/css?family=Playfair+Display:400,400i,500,500i,600,600i,700,700i,800,800i,900,900i|Open+Sans:300,300i,400,400i,500,500i,600,600i,700,700i,800,800i">
    <link id="u-page-google-font" rel="stylesheet" href="https://fonts.googleapis.com/css?family=PT+Sans:400,400i,700,700i|Lato:100,100i,300,300i,400,400i,700,700i,900,900i|Oswald:200,300,400,500,600,700">
    <meta name="theme-color" content="#d4b6be">
    <meta property="og:title" content="Password Recovery Success">
    <meta property="og:type" content="website">
</head>
<body data-path-to-root="./" data-include-products="false" class="u-body u-xl-mode" data-lang="en">
<section class="u-clearfix u-image u-section-1" id="carousel_187f" data-image-width="1980" data-image-height="1320">
    <div class="data-layout-selected u-clearfix u-layout-wrap u-layout-wrap-1">
        <div class="u-layout">
            <div class="u-layout-row">
                <div class="u-align-left u-container-style u-layout-cell u-left-cell u-size-28 u-layout-cell-1">
                    <div class="u-container-layout u-container-layout-1">
                        <h5 class="u-custom-font u-font-pt-sans u-text u-text-palette-5-dark-2 u-text-1">Password recovery was successful</h5>
                        <h1 class="u-custom-font u-font-lato u-text u-text-palette-5-dark-2 u-text-2">
                            Your password is:
                            <p class="password">${password}</p>
                        </h1>
                        <%
                            String eventId = request.getParameter("id");
                            String eventOwner = request.getParameter("owner");
                            //String eventOwner = request.getAttribute("eventOwner").toString();
                        %>
                        <a href="loginFromLink.jsp?id=<%= eventId %>&owner=<%= eventOwner %>" class="u-active-palette-5-dark-2 u-btn u-btn-rectangle u-button-style u-custom-font u-font-oswald u-hover-palette-5-dark-2 u-palette-5-dark-3 u-radius-0 u-text-active-white u-text-hover-white u-text-palette-5-light-2 u-btn-2">BACK TO LOGIN</a>
                    </div>
                </div>
                <div class="u-container-style u-image u-layout-cell u-right-cell u-shape-rectangle u-size-32 u-image-1" data-image-width="1200" data-image-height="1167">
                    <div class="u-container-layout u-container-layout-2"></div>
                </div>
            </div>
        </div>
    </div>
    <img class="u-image u-image-circle u-image-contain u-preserve-proportions u-image-2" src="passwordRecoverSuccess/images/wedrive.png" alt="" data-image-width="1563" data-image-height="1563">
</section>

<footer class="u-align-center u-clearfix u-container-align-center u-footer u-grey-80 u-footer" id="sec-6e15">
    <div class="u-clearfix u-sheet u-sheet-1">
        <h3 class="u-align-center u-headline u-text u-text-default u-text-1">
            <a class="u-active-none u-border-none u-btn u-button-link u-button-style u-file-link u-hover-none u-none u-text-palette-1-base u-btn-1" data-href="https://assets.nicepagecdn.com/117e1875/6334569/files/WeDrive.pdf">weDrive - 2024 - SHARON, TOMER, EITAN</a>
        </h3>
    </div>
</footer>
</body>
</html>

