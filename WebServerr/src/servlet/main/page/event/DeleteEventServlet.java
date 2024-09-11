package servlet.main.page.event;

import database.DriverRideDAO;
import database.EventsDAO;
import database.HitchhikerRideDAO;
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

        // Remove the event from the database
        EventsDAO eventsDAO = new EventsDAO();
        boolean isDeleted = eventsDAO.deleteEvent(eventName, userName);
        DriverRideDAO driverRideDAO = new DriverRideDAO();
        DriverRideDAO.deleteDriverRidesByEventName(eventName);
        HitchhikerRideDAO hitchhikerRideDAO = new HitchhikerRideDAO();
        HitchhikerRideDAO.deleteHitchhikerRidesByEventName(eventName);
        Users.getDriversRideByEvents().remove(eventName);
        Users.getHitchhikersRideByEvents().remove(eventName);

        if (isDeleted) {
            response.sendRedirect("editOrDeleteEvent.jsp");
        } else {
            response.sendRedirect("deleteEventError.jsp");
        }
    }
}
