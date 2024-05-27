package servlet.main.page.event;
import database.Users;
import entity.ServerClient;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import jakarta.servlet.http.HttpServlet;

import java.io.IOException;

@WebServlet(name = "DeleteEventServlet", urlPatterns = {"/deleteEvent"})
public class DeleteEventServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String eventName = request.getParameter("eventName");

        String userName = (String)request.getSession().getAttribute("userName");
        ServerClient client = Users.getUserByFullName(userName);

        client.getOwnedEvents().remove(eventName);

        response.sendRedirect("editOrDeleteEvent.jsp");
    }
}
