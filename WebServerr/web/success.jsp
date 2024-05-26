<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.google.zxing.EncodeHintType" %>
<%@ page import="com.google.zxing.WriterException" %>
<%@ page import="com.google.zxing.qrcode.QRCodeWriter" %>
<%@ page import="com.google.zxing.common.BitMatrix" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.io.ByteArrayOutputStream" %>
<%@ page import="javax.imageio.ImageIO" %>
<%@ page import="java.awt.image.BufferedImage" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Event Created Successfully</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f2f5;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            color: #333;
        }
        .container {
            background-color: #fff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            text-align: center;
            max-width: 500px;
            width: 100%;
        }
        h2 {
            color: #4CAF50;
        }
        img {
            max-width: 100%;
            height: auto;
            margin: 20px 0;
        }
        p {
            color: #555;
            line-height: 1.6;
        }
        a {
            color: #4CAF50;
            text-decoration: none;
            font-weight: bold;
        }
        a:hover {
            text-decoration: underline;
        }
        .qr-wrapper {
            margin: 20px 0;
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
    <h2>Your Event Was Created Successfully</h2>
    <%
        String qrUrl = (String) request.getAttribute("qrUrl");
        if (qrUrl != null) {
            // Generate QR code image dynamically
            int qrCodeSize = 250;
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.MARGIN, 0);
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrUrl, com.google.zxing.BarcodeFormat.QR_CODE, qrCodeSize, qrCodeSize, hints);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            BufferedImage qrImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    qrImage.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }

            // Convert BufferedImage to base64
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(qrImage, "png", baos);
            String qrImageBase64 = java.util.Base64.getEncoder().encodeToString(baos.toByteArray());

            // Display QR code image
    %>
    <div class="qr-wrapper">
        <img src="data:image/png;base64,<%= qrImageBase64 %>" alt="QR Code">
    </div>
    <p>Scan the QR code or click the following link:<br/><a href="<%= qrUrl %>"><%= qrUrl %></a></p>
    <a href="eventOwnerMenu.jsp">Back to Event Owner Menu</a>
    <% } %>
</div>
</body>
</html>
