package servlet.main.page.event;
import database.Users;
import entity.ServerClient;
import event.EventData;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import jakarta.servlet.http.HttpServlet;

import java.io.IOException;

@WebServlet("/eventDetails")
public class EventDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String eventName = request.getParameter("eventName");
        String userName = (String) request.getSession().getAttribute("userName");

        if (eventName == null || userName == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Event name or user name is missing");
            return;
        }

        ServerClient client = Users.getUserByFullName(userName);
        EventData event = client.getOwnedEventByName(eventName);

        if (event == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Event not found");
            return;
        }

        request.setAttribute("event", event);
        request.getRequestDispatcher("/eventDetails.jsp").forward(request, response);
    }
}
