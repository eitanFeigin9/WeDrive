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
<html>
<head>
    <title>Success</title>
</head>
<body>
<h2>Insertion Successful</h2>
<p>Your name has been successfully added to the list.</p>
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
<img src="data:image/png;base64, <%= qrImageBase64 %>" alt="QR Code">

<p>Scan the QR code or click the following link:<br/><a href="<%= qrUrl %>"><%= qrUrl %></a></p>
<% } %>
</body>
</html>
