package servlet.main.page.event;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import database.Users;
import entity.ServerClient;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet(name = "NewEventServlet", urlPatterns = {"/createEvent"})
@MultipartConfig
public class NewEventServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form data
        ServerClient eventOwner = getEventOwnerFromSessionOrDatabase(request);
        String eventName = request.getParameter("eventName");
        String eventDate = request.getParameter("eventDate");
        String eventKind = request.getParameter("eventKind");
        String eventLocation = request.getParameter("eventLocation");
        HashSet<String> guestList = createGuestList(request,response);

        if(!eventOwner.addNewEvent(eventName,eventDate,eventKind,guestList, eventLocation, getFileName(request.getPart("guestList")))){
            response.sendRedirect("eventExistsError.jsp");
        }
        else {
            response.sendRedirect("thankForNewEvent.jsp");
        }


    }

    private ServerClient getEventOwnerFromSessionOrDatabase(HttpServletRequest request) {
        String userName = (String)request.getSession().getAttribute("userName");
        return Users.getUserByFullName(userName);

    }

    private HashSet<String> createGuestList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashSet<String> guestNames = new HashSet<>();

        Part filePart = request.getPart("guestList");
        InputStream fileContent = filePart.getInputStream();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(fileContent))) {
            String line;
            String[] headers = reader.readLine().split(",");
            int guestListIndex = -1;

            for (int i = 0; i < headers.length; i++) {
                String cleanedHeader = headers[i].replaceAll("[^a-zA-Z0-9]", "");
                if ("guestlist".equalsIgnoreCase(cleanedHeader.trim())) {
                    guestListIndex = i;
                    break;
                }
            }

            if (guestListIndex == -1) {
                response.getWriter().println("Error: 'guest-list' column not found.");
                //להעביר לעמוד תקלה
                return null;
            }

            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length > guestListIndex) {
                    String guestName = columns[guestListIndex].trim();
                    if (!guestName.isEmpty()) {
                        guestNames.add(guestName);
                    }
                }
            }
        }

        for (String name : guestNames) {
            System.out.println(name);
        }

        return guestNames;
    }

    private String getFileName(Part part) {
        String contentDispositionHeader = part.getHeader("content-disposition");
        for (String header : contentDispositionHeader.split(";")) {
            if (header.trim().startsWith("filename")) {
                return header.substring(header.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}