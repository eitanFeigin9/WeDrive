package servlet.main.page.event;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

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

@WebServlet(name = "NewEventServlet", urlPatterns = {"/createEvent"})
@MultipartConfig
public class NewEventServlet extends HttpServlet {
    private static final String QR_CODE_IMAGE_PATH = "WebServerr/web/QRCodes"; // Path relative to the web context root
    private static Map<String, String> nameMap = new HashMap<>();
    private static Map<String, String> ownerMap = new HashMap<>();

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
        String fileName =request.getParameter("fileName");

        double latitude, longitude;

        try {
            latitude = Double.parseDouble(latitudeStr);
            longitude = Double.parseDouble(longitudeStr);
        } catch (NumberFormatException e) {
            // Redirect to error page
            response.sendRedirect("error.jsp");
            return;
        }

        // Check if the user uploaded a guest list
        Part guestListPart = request.getPart("guestList");
        HashSet<String> guestList = new HashSet<>();
        String guestListFileName = null;
        if (guestListPart != null && guestListPart.getSize() > 0) {
            guestList = ServletUtils.createGuestList(request, response);
            guestListFileName = ServletUtils.getFileName(guestListPart);
        }

        // Check if event name already exists
        if (!eventOwner.ifEventExists(eventName)) {
            request.setAttribute("errorMessage", "Event Name already exists.");
            request.getRequestDispatcher("/newEvent.jsp").forward(request, response);
            return;
        }

        String guestListString = String.join(",", guestList);
        String eventOwnerUserName = eventOwner.getUserName();


        //String eventLink = "http://localhost:8080/weDrive/welcome.jsp?id=" + eventName+  "&owner=" + eventOwnerUserName;
        String eventLink = "http://wedriveco.com:8080/users/welcome.jsp?id=" + eventName +  "&owner=" + eventOwnerUserName;


        // Store the event name and owner in maps for future reference
        nameMap.put(eventName, eventName);
        ownerMap.put(eventName, eventOwnerUserName);

        // Set attributes for success page
        request.setAttribute("eventName", eventName);
        request.setAttribute("eventDate", eventDate);
        request.setAttribute("eventLocation", eventLocation);
        request.setAttribute("eventKind", eventKind);
        request.setAttribute("userName", eventOwnerUserName);
        request.setAttribute("latitude", latitudeStr);
        request.setAttribute("longitude", longitudeStr);
        request.setAttribute("fileName", guestListFileName);
        request.setAttribute("guestList", guestList);

        // request.setAttribute("qrUrl", "QRCodes/" + eventName + "_QR.png"); // Pass the QR code path
        request.setAttribute("qrUrl",eventLink );
        request.getRequestDispatcher("success.jsp").forward(request, response);
    }

    private ServerClient getEventOwnerFromSessionOrDatabase(HttpServletRequest request) {
        String userName = (String) request.getSession().getAttribute("userName");
        return Users.getUserByUserName(userName);
    }

}
