package servlet.main.page.event;
import database.Users;
import entity.ServerClient;
import event.EventData;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import jakarta.servlet.http.HttpServlet;
import utils.ServletUtils;

import java.io.IOException;
import java.util.HashSet;

@WebServlet(name = "EditEventServlet", urlPatterns = {"/editEvent"})
@MultipartConfig
public class EditEventServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = (String) request.getSession().getAttribute("userName");
        ServerClient client = Users.getUserByUserName(userName);
        String eventOldName = (String) request.getSession().getAttribute("eventOldName");
        EventData event = client.getOwnedEventByName(eventOldName);
        String eventNewName = request.getParameter("eventName");
        if (!eventNewName.equals(eventOldName)) {
            if (client.getOwnedEvents().containsKey(eventNewName)) {
                response.sendRedirect("editEventError.jsp");
            }
        }
        HashSet<String> oldGuestList = event.getGuestList();
        String oldFileName = event.getFileName();
        client.deleteOwnedEvent(eventOldName);
        String eventDate = request.getParameter("eventDate");
        String eventKind = request.getParameter("eventKind");
        String location = request.getParameter("eventLocation");
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
        String fileName = ServletUtils.getFileName(request.getPart("guestList"));
        assert fileName != null;
        if (fileName.isEmpty()) {
            client.addNewOwnedEvent(eventNewName, userName,eventDate, eventKind, oldGuestList, location, oldFileName,latitude, longitude);
        }
        else {
            HashSet<String> guestList = ServletUtils.createGuestList(request,response);
            client.addNewOwnedEvent(eventNewName, userName,eventDate, eventKind, guestList, location, fileName,latitude, longitude);
        }
        response.sendRedirect("updateEventSuccess.jsp");
    }
}
