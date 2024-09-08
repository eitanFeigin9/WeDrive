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
<%@ page import="java.awt.Color" %>
<%@ page import="java.awt.Font" %>
<%@ page import="java.awt.Graphics2D" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.FileOutputStream" %>
<%@ page import="java.io.OutputStream" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.util.Base64" %>
<%@ page import="database.Users" %>
<%@ page import="entity.ServerClient" %>
<%@ page import="event.EventData" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="database.EventsDAO" %>
<%@ page import="utils.ServletUtils" %>
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
        String eventName = (String) request.getAttribute("eventName");
        String eventDate = (String) request.getAttribute("eventDate");
        String eventLocation = (String) request.getAttribute("eventLocation");
        String eventKind = (String) request.getAttribute("eventKind");
        String qrImagePath = (String) request.getAttribute("qrImagePath");
        String qrUrl = (String) request.getAttribute("qrUrl");
        String eventOwnerUserName=(String) request.getAttribute("userName");
        String latitudeStr=(String) request.getAttribute("latitude");
        String longitudeStr=(String) request.getAttribute("longitude");
        ServerClient owner = Users.getUserByUserName(eventOwnerUserName);
        String fileName =(String) request.getAttribute("fileName");
        HashSet<String>  guestList = (HashSet<String>) request.getAttribute("guestList");

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


            // Save QR code image to file
           // String qrCodeFileName = eventName.replaceAll("\\s+", "_") + ".png";
          //  String basePath = application.getInitParameter("basePath");
         //   String qrCodeFilePath = basePath+("WebServerr/web/QRCodes/"+qrCodeFileName);
         //   try (OutputStream os = new FileOutputStream(qrCodeFilePath)) {
         //       ImageIO.write(qrImage, "png", os);
         //   } catch (IOException e) {
         //       e.printStackTrace(); // Handle the exception as needed
         //   }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(qrImage, "png", baos);
            String qrImageBase64 = java.util.Base64.getEncoder().encodeToString(baos.toByteArray());
            EventData newEvent =new EventData(eventName,eventOwnerUserName,eventDate,eventKind,guestList,eventLocation,fileName,Double.parseDouble(latitudeStr),Double.parseDouble(longitudeStr),qrImageBase64,qrUrl);
            owner.addOwnedEvent(newEvent);
            Users.addEventToMap(newEvent);
            EventsDAO eventsDAO = new EventsDAO();

            String guestListString = String.join(",", guestList);

            eventsDAO.addNewEvent(eventName,eventDate,eventKind,guestListString,eventLocation,fileName,Double.parseDouble(latitudeStr),Double.parseDouble(longitudeStr), eventOwnerUserName, qrImageBase64, qrUrl);

            // Decode Base64 string and save to file
         /*   byte[] qrImageBytes = Base64.getDecoder().decode(qrImageBase64);
            String qrCodeFileName = eventName.replaceAll("\\s+", "_") + ".png";
            String basePath = application.getInitParameter("basePath");
               String qrCodeFilePath = basePath+("WebServerr/web/QRCodes/"+qrCodeFileName);
          //  String qrCodeFilePath = "C:/Users/איתן/Desktop/WeDriveWithDB/WebServerr/web/QRCodes/" + qrCodeFileName;
            try (OutputStream os = new FileOutputStream(qrCodeFilePath)) {
                os.write(qrImageBytes);
            } catch (IOException e) {
                e.printStackTrace(); // Handle the exception as needed
            }

          */
            // For wedding invitation
            String weddingInvitationImagePath = application.getRealPath("/WeddingInvitation.png");
            File weddingInvitationFile = new File(weddingInvitationImagePath);
            BufferedImage weddingInvitationImage = ImageIO.read(weddingInvitationFile);

            Graphics2D g2d = weddingInvitationImage.createGraphics();

            g2d.setFont(new Font("Brush Script MT", Font.PLAIN, 170));
            g2d.setColor(new Color(216, 165, 71));
            g2d.drawString(eventName, 340, 1170);

            g2d.setFont(new Font("Brush Script MT", Font.PLAIN, 80));
            g2d.setColor(new Color(216, 165, 71));
            g2d.drawString(eventDate, 535, 1420);

            g2d.setFont(new Font("David Libre", Font.PLAIN, 50));
            g2d.setColor(new Color(216, 165, 71));
            g2d.drawString(eventLocation, 540, 1525);

            g2d.drawImage(javax.imageio.ImageIO.read(new java.io.ByteArrayInputStream(Base64.getDecoder().decode(qrImageBase64))), 580, 1610, null);
            g2d.dispose();

            ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
            ImageIO.write(weddingInvitationImage, "png", baos2);
            String finalImageBase64 = Base64.getEncoder().encodeToString(baos2.toByteArray());
            //For brit Invitation
            String britInvitationImagePath = application.getRealPath("/brit.png");
            File britInvitationFile = new File(britInvitationImagePath);
            BufferedImage britInvitationImage = ImageIO.read(britInvitationFile);

            Graphics2D britG2d = britInvitationImage.createGraphics();

            britG2d.setFont(new Font("Brush Script MT", Font.PLAIN, 80));
            britG2d.setColor(new Color(42, 70, 154));
            britG2d.drawString(eventDate, 535, 1050);

            britG2d.setFont(new Font("David Libre", Font.PLAIN, 50));
            britG2d.setColor(new Color(42, 70, 154));
            britG2d.drawString(eventLocation, 540, 1200);

            britG2d.drawImage(javax.imageio.ImageIO.read(new java.io.ByteArrayInputStream(Base64.getDecoder().decode(qrImageBase64))), 580, 1400, null);
            britG2d.dispose();

            ByteArrayOutputStream britBaos2 = new ByteArrayOutputStream();
            ImageIO.write(britInvitationImage, "png", britBaos2);
            String britImageBase64 = Base64.getEncoder().encodeToString(britBaos2.toByteArray());

            //For Conference Invitation
            String conferenceInvitationImagePath = application.getRealPath("/conference.png");
            File conferenceInvitationFile = new File(conferenceInvitationImagePath);
            BufferedImage conferenceInvitationImage = ImageIO.read(conferenceInvitationFile);

            Graphics2D conferenceG2d = conferenceInvitationImage.createGraphics();

            conferenceG2d.setFont(new Font("Brush Script MT", Font.PLAIN, 70));
            conferenceG2d.setColor(new Color(75, 56, 53));
            conferenceG2d.drawString(eventDate, 250, 1500);

            conferenceG2d.setFont(new Font("David Libre", Font.PLAIN, 50));
            conferenceG2d.setColor(new Color(75, 56, 53));
            conferenceG2d.drawString(eventLocation, 250, 1600);

            conferenceG2d.drawImage(javax.imageio.ImageIO.read(new java.io.ByteArrayInputStream(Base64.getDecoder().decode(qrImageBase64))), 275, 1700, null);
            conferenceG2d.dispose();

            ByteArrayOutputStream conferenceBaos2 = new ByteArrayOutputStream();
            ImageIO.write(conferenceInvitationImage, "png", conferenceBaos2);
            String conferenceImageBase64 = Base64.getEncoder().encodeToString(conferenceBaos2.toByteArray());
//For Bar Mitzvah Invitation
            String barMitzvahInvitationImagePath = application.getRealPath("/barMitzvah.png");
            File barMitzvahInvitationFile = new File(barMitzvahInvitationImagePath);
            BufferedImage barMitzvahInvitationImage = ImageIO.read(barMitzvahInvitationFile);

            Graphics2D barMitzvahG2d = barMitzvahInvitationImage.createGraphics();

            barMitzvahG2d.setFont(new Font("Brush Script MT", Font.PLAIN, 170));
            barMitzvahG2d.setColor(new Color(35, 33, 54));
            barMitzvahG2d.drawString(eventName, 555, 1100);

            barMitzvahG2d.setFont(new Font("Brush Script MT", Font.PLAIN, 80));
            barMitzvahG2d.setColor(new Color(33, 33, 54));
            barMitzvahG2d.drawString(eventDate, 555, 1300);

            barMitzvahG2d.setFont(new Font("David Libre", Font.PLAIN, 50));
            barMitzvahG2d.setColor(new Color(33, 33, 54));
            barMitzvahG2d.drawString(eventLocation, 560, 1400);

            barMitzvahG2d.setFont(new Font("Playfair Display", Font.PLAIN, 50));
            barMitzvahG2d.setColor(new Color(33, 33, 54));
            barMitzvahG2d.drawString("Catch a ride by scanning the QR code", 285, 1535);

            barMitzvahG2d.drawImage(javax.imageio.ImageIO.read(new java.io.ByteArrayInputStream(Base64.getDecoder().decode(qrImageBase64))), 595, 1600, null);
            barMitzvahG2d.dispose();

            ByteArrayOutputStream barMitzvahBaos2 = new ByteArrayOutputStream();
            ImageIO.write(barMitzvahInvitationImage, "png", barMitzvahBaos2);
            String barMitzvahImageBase64 = Base64.getEncoder().encodeToString(barMitzvahBaos2.toByteArray());

            //For Bat Mitzvah Invitation
            String batMitzvahInvitationImagePath = application.getRealPath("/batMitzvah.png");
            File batMitzvahInvitationFile = new File(batMitzvahInvitationImagePath);
            BufferedImage batMitzvahInvitationImage = ImageIO.read(batMitzvahInvitationFile);

            Graphics2D batMitzvahG2d = batMitzvahInvitationImage.createGraphics();

            batMitzvahG2d.setFont(new Font("Brush Script MT", Font.PLAIN, 170));
            batMitzvahG2d.setColor(new Color(68, 56, 108));
            batMitzvahG2d.drawString(eventName, 555, 1100);

            batMitzvahG2d.setFont(new Font("Brush Script MT", Font.PLAIN, 80));
            batMitzvahG2d.setColor(new Color(68, 56, 108));
            batMitzvahG2d.drawString(eventDate, 555, 1200);

            batMitzvahG2d.setFont(new Font("David Libre", Font.PLAIN, 50));
            batMitzvahG2d.setColor(new Color(33, 33, 54));
            batMitzvahG2d.drawString(eventLocation, 560, 1300);

            batMitzvahG2d.setFont(new Font("Playfair Display", Font.PLAIN, 50));
            batMitzvahG2d.setColor(new Color(33, 33, 54));
            batMitzvahG2d.drawString("Catch a ride by scanning the QR code", 285, 1435);

            batMitzvahG2d.drawImage(javax.imageio.ImageIO.read(new java.io.ByteArrayInputStream(Base64.getDecoder().decode(qrImageBase64))), 595, 1500, null);
            batMitzvahG2d.dispose();

            ByteArrayOutputStream batMitzvahBaos2 = new ByteArrayOutputStream();
            ImageIO.write(batMitzvahInvitationImage, "png", batMitzvahBaos2);
            String batMitzvahImageBase64 = Base64.getEncoder().encodeToString(batMitzvahBaos2.toByteArray());
            // For birthday Invitation
            String birthdayInvitationImagePath = application.getRealPath("/birthdayInvitation.png");
            File birthdayInvitationFile = new File(birthdayInvitationImagePath);
            BufferedImage birthdayInvitationImage = ImageIO.read(birthdayInvitationFile);

            Graphics2D birthdayG2d = birthdayInvitationImage.createGraphics();

            birthdayG2d.setFont(new Font("Brush Script MT", Font.PLAIN, 170));
            birthdayG2d.setColor(new Color(216, 165, 71));
            birthdayG2d.drawString(eventName, 340, 700);

            birthdayG2d.setFont(new Font("Brush Script MT", Font.PLAIN, 80));
            birthdayG2d.setColor(new Color(216, 165, 71));
            birthdayG2d.drawString(eventDate, 535, 900);

            birthdayG2d.setFont(new Font("David Libre", Font.PLAIN, 50));
            birthdayG2d.setColor(new Color(216, 165, 71));
            birthdayG2d.drawString(eventLocation, 540, 1000);

            birthdayG2d.setFont(new Font("Playfair Display", Font.PLAIN, 50));
            birthdayG2d.setColor(new Color(216, 165, 71));
            birthdayG2d.drawString("Catch a ride by scanning the QR code", 235, 1335);

            birthdayG2d.drawImage(javax.imageio.ImageIO.read(new java.io.ByteArrayInputStream(Base64.getDecoder().decode(qrImageBase64))), 580, 1480, null);
            birthdayG2d.dispose();

            ByteArrayOutputStream birthdayBaos2 = new ByteArrayOutputStream();
            ImageIO.write(birthdayInvitationImage, "png", birthdayBaos2);
            String birthdayImageBase64 = Base64.getEncoder().encodeToString(birthdayBaos2.toByteArray());
    %>
    <div class="qr-wrapper">
        <img src="data:image/png;base64,<%= qrImageBase64 %>" alt="QR Code">
    </div>
    <p>Scan the QR code or click the following link:<br/><a href="<%= qrUrl %>"><%= qrUrl %></a></p>
    <a href="data:image/png;base64,<%= qrImageBase64 %>" download="event-qr-code.png">Download QR Code</a>
    <%

        if ("Wedding".equalsIgnoreCase(eventKind)) {
    %>
    <a href="data:image/png;base64,<%= finalImageBase64 %>" download="wedding-invitation.png">Download Wedding Invitation</a>
    <%
    } else if("Ritual circumcision".equalsIgnoreCase(eventKind)){
    %>
    <a href="data:image/png;base64,<%= britImageBase64 %>" download="brit-invitation.png">Download Ritual Circumcision Invitation</a>
    <%
    } else if("Conference".equalsIgnoreCase(eventKind)){
    %>
    <a href="data:image/png;base64,<%= conferenceImageBase64 %>" download="conference-invitation.png">Download Conference Invitation</a>
    <%
    }
    else if("Bar Mitzvah".equalsIgnoreCase(eventKind)){
    %>
    <a href="data:image/png;base64,<%= barMitzvahImageBase64 %>" download="barMitzvah-invitation.png">Download Bar Mitzvah Invitation</a>
    <%
    }
    else if("Bat Mitzvah".equalsIgnoreCase(eventKind)){
    %>
    <a href="data:image/png;base64,<%= batMitzvahImageBase64 %>" download="batMitzvah-invitation.png">Download Bat Mitzvah Invitation</a>
    <%
    }
    else if("Birthday party".equalsIgnoreCase(eventKind)){
    %>
    <a href="data:image/png;base64,<%= birthdayImageBase64 %>" download="birthday-invitation.png">Download Birthday Invitation</a>
    <%
        }
    %>
    <a href="eventOwnerMenu.jsp">Back to Event Owner Menu</a>
    <% } %>
</div>
</body>
</html>
