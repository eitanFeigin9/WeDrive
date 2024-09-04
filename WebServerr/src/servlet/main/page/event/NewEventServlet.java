package servlet.main.page.event;

import java.io.File;
import java.sql.SQLException;
import java.util.*;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import database.EventsDAO;
import database.Users;
import database.UsersDAO;
import entity.ServerClient;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import utils.ServletUtils;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "NewEventServlet", urlPatterns = {"/createEvent"})
@MultipartConfig
public class NewEventServlet extends HttpServlet {
    private static Map<String, String> nameMap = new HashMap<>();
    private static Map<String, String> ownerMap = new HashMap<>();

    public static Map<String, String> getNameMap() {
        return nameMap;
    }
    public static String getEventOwnerName(String eventName) {
        return ownerMap.get(eventName);
    }
    private static final String QR_CODE_IMAGE_PATH = "QRCodes"; // Set the path to save the QR code image
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve form data
        ServerClient eventOwner = getEventOwnerFromSessionOrDatabase(request);
        String eventName = request.getParameter("eventName");
        String eventDate = request.getParameter("eventDate");
        String eventKind = request.getParameter("eventKind");
        String eventLocation = request.getParameter("eventLocation");
        String latitudeStr = request.getParameter("latitude");
        String longitudeStr = request.getParameter("longitude");
        double latitude;
        double longitude;

        try {
            latitude = Double.parseDouble(latitudeStr);   // Parse latitude
            longitude = Double.parseDouble(longitudeStr); // Parse longitude
        } catch (NumberFormatException e) {
            //redirect to error page
            return;
        }
        //check if user uploaded a guest list
        Part guestListPart = request.getPart("guestList");
        HashSet<String> guestList = new HashSet<>();
        String guestListFileName = null;
        if (guestListPart != null && guestListPart.getSize() > 0) {
            guestList = ServletUtils.createGuestList(request, response);
            guestListFileName = ServletUtils.getFileName(guestListPart);
        }
        //add new event to the event owner
        if (!eventOwner.addNewOwnedEvent(eventName, eventDate, eventKind, guestList, eventLocation, guestListFileName,latitude, longitude)) {
            response.sendRedirect("eventExistsError.jsp");
            return; // Exit if event creation fails
        }

        String guestListString = String.join(",", guestList);

        //add event to database to Events table
        String eventOwnerName = eventOwner.getFullName();
        EventsDAO eventsDAO = new EventsDAO();
        eventsDAO.addNewEvent(eventName, eventDate, eventKind, guestListString, eventLocation, guestListFileName, latitudeStr, longitudeStr, eventOwnerName);

        nameMap.put(eventName, eventName);
        ownerMap.put(eventName, eventOwner.getFullName());
        String url = request.getContextPath() + "/welcome.jsp?id=" + eventName;
        String link = "http://wedriveco.com:8080/WebServer_Web exploded/welcome.jsp?id=" + eventName;

        // Generate the QR code
        generateQRCodeImage(link, QR_CODE_IMAGE_PATH, 350, 350);
        request.setAttribute("qrUrl", link);

        // Redirect to a success page
        request.getRequestDispatcher("success.jsp").forward(request, response);

    }
        private ServerClient getEventOwnerFromSessionOrDatabase (HttpServletRequest request){
            String userName = (String) request.getSession().getAttribute("userName");
            UsersDAO usersDAO = new UsersDAO();
            return usersDAO.getUserByFullName(userName);
        }

    private void generateQRCodeImage(String text, String filePath, int width, int height) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.MARGIN, 1);
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);

            // Write the QR code to a PNG file
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", new File(filePath).toPath());
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
    }

}