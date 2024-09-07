package servlet.main.page.event;

import database.EventsDAO;
import database.Users;
import entity.ServerClient;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "DeleteEventServlet", urlPatterns = {"/deleteEvent"})
public class DeleteEventServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String eventName = request.getParameter("eventName");

        String userName = (String) request.getSession().getAttribute("userName");
        ServerClient client = Users.getUserByUserName(userName);

        // Remove the event from the client's local map
        client.getOwnedEvents().remove(eventName);

        // Remove the event from the database
        EventsDAO eventsDAO = new EventsDAO();
        boolean isDeleted = eventsDAO.deleteEvent(eventName, userName);

        // Redirect to the appropriate page based on success/failure
        if (isDeleted) {
            response.sendRedirect("editOrDeleteEvent.jsp");
        } else {
            // You could add error handling here if necessary
            response.sendRedirect("deleteEventError.jsp");
        }
    }
}
