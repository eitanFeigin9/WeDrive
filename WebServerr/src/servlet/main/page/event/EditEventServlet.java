package servlet.main.page.event;

import database.DriverRideDAO;
import database.EventsDAO;
import database.HitchhikerRideDAO;
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
        DriverRideDAO.deleteDriverRidesByEventName(eventOldName);
        HitchhikerRideDAO.deleteHitchhikerRidesByEventName(eventOldName);
        Users.getDriversRideByEvents().remove(eventOldName);
        Users.getHitchhikersRideByEvents().remove(eventOldName);
        String guestListString = String.join(",", guestList);

         //String eventLink = "http://localhost:8080/weDrive/welcome.jsp?id=" + eventNewName+  "&owner=" + userName;
        String eventLink = "http://wedriveco.com:8080/users/welcome.jsp?id=" + eventNewName +  "&owner=" + userName;
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


        }

}
