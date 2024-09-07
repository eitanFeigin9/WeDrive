package servlet.main.page.event;

import database.EventsDAO;
import database.Users;
import entity.ServerClient;
import event.EventData;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import utils.ServletUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

@WebServlet(name = "EditEventServlet", urlPatterns = {"/editEvent"})
@MultipartConfig

public class EditEventServlet extends HttpServlet {
    private static Map<String, String> nameMap = new HashMap<>();
    private static Map<String, String> ownerMap = new HashMap<>();
    private static final String QR_CODE_DIRECTORY = "QRCodes"; // Directory to save QR codes

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = (String) request.getSession().getAttribute("userName");
        ServerClient client = Users.getUserByUserName(userName);
        String eventOldName = ((String) request.getSession().getAttribute("eventOldName")).trim();
        EventData event = client.getOwnedEventByName(eventOldName);
        String eventNewName = request.getParameter("eventName").trim();

        EventsDAO eventsDAO = new EventsDAO();

        if (!eventNewName.equals(eventOldName) && client.getOwnedEvents().containsKey(eventNewName)) {
            request.setAttribute("errorMessage", "The event name already exists in your events. Please choose a different name.");
            request.getRequestDispatcher("/editEvent.jsp").forward(request, response);
            return;
        }

        HashSet<String> oldGuestList = event.getGuestList();
        String oldFileName = event.getFileName();

        // Retrieve new parameters
        String eventDate = request.getParameter("eventDate");
        String eventKind = request.getParameter("eventKind");
        String location = request.getParameter("eventLocation");
        String latitudeStr = request.getParameter("latitude");
        String longitudeStr = request.getParameter("longitude");

        double latitude;
        double longitude;

        try {
            latitude = Double.parseDouble(latitudeStr);
            longitude = Double.parseDouble(longitudeStr);
        } catch (NumberFormatException e) {
            response.sendRedirect("editEventError.jsp");
            return;
        }

        String fileName = ServletUtils.getFileName(request.getPart("guestList"));
        HashSet<String> guestList = oldGuestList;

        if (!fileName.isEmpty()) {
            guestList = ServletUtils.createGuestList(request, response);
        } else {
            fileName = oldFileName;
        }

        // Update the event in the local map
        client.deleteOwnedEvent(eventOldName);
        boolean isDeleted = eventsDAO.deleteEvent(eventOldName, userName);
        String guestListString = String.join(",", guestList);
/*
        // Update the event in the database
        //if (isUpdated) {
            // Generate the QR code
            String link = "http://localhost:8080/weDrive/eventDetails.jsp?eventName=" + eventNewName;
            String qrCodeFileName = eventNewName + ".png";
            String qrCodeFilePath = getServletContext().getRealPath("/") + QR_CODE_DIRECTORY + "/" + qrCodeFileName;
            generateQRCodeImage(link, qrCodeFilePath, 350, 350);


 */
            // Set attributes for JSP
       // String eventLink = "http://localhost:8080/weDrive/welcome.jsp?id=" + eventNewName;
        String eventLink = "http://localhost:8080/weDrive/welcome.jsp?id=" + eventNewName +  "&owner=" + userName;
        request.setAttribute("eventName", eventNewName);
        request.setAttribute("eventDate", eventDate);
        request.setAttribute("eventLocation", location);
        request.setAttribute("eventKind", eventKind);
        request.setAttribute("userName", userName);
        request.setAttribute("latitude", latitudeStr);
        request.setAttribute("longitude", longitudeStr);
        request.setAttribute("fileName", fileName);
        request.setAttribute("guestList", guestList);
        request.setAttribute("qrUrl",eventLink );
        nameMap.put(eventNewName, eventNewName);
        ownerMap.put(eventNewName, userName);
        request.getRequestDispatcher("editEventSuccess.jsp").forward(request, response);

        /*request.setAttribute("eventName", eventNewName);
            request.setAttribute("eventDate", eventDate);
            request.setAttribute("eventLocation", location);
            request.setAttribute("eventKind", eventKind);
            request.setAttribute("qrImagePath", QR_CODE_DIRECTORY + "/" + qrCodeFileName);
            request.setAttribute("invitationLink", link);
            client.addNewOwnedEvent(eventNewName, userName, eventDate, eventKind, guestList, location, fileName, latitude, longitude,QR_CODE_DIRECTORY + "/" + qrCodeFileName,link);
             eventsDAO.updateEvent(eventOldName, eventNewName, eventDate, eventKind, guestListString, location, fileName, latitude, longitude, userName,QR_CODE_DIRECTORY + "/" + qrCodeFileName,link);

            request.getRequestDispatcher("updateEventSuccess.jsp").forward(request, response);

         */
        }// else {
         //   response.sendRedirect("editEventError.jsp");
        //}



  /*  private void generateQRCodeImage(String text, String filePath, int width, int height) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.MARGIN, 1);
            BitMatrix bitMatrix = qrCodeWriter.encode(text, com.google.zxing.BarcodeFormat.QR_CODE, width, height, hints);

            // Write the QR code to a PNG file
            File file = new File(filePath);
            file.getParentFile().mkdirs(); // Create directories if they don't exist
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", file.toPath());
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
    }

   */

    public static Map<String, String> getNameMap() {
        return nameMap;
    }

    public static Map<String, String> getOwnerMap() {
        return ownerMap;
    }
    public static String getEventOwnerName(String eventName) {
        return ownerMap.get(eventName);
    }
}
