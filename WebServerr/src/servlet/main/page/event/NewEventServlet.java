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
import utils.ServletUtils;

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
        HashSet<String> guestList = ServletUtils.createGuestList(request,response);

        if(!eventOwner.addNewEvent(eventName,eventDate,eventKind,guestList, eventLocation, ServletUtils.getFileName(request.getPart("guestList")))){
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


}