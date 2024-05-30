package servlet.main.page.ride;

import database.Users;
import entity.ServerClient;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import jakarta.servlet.http.HttpServlet;

import java.io.IOException;

@WebServlet(name = "CancelRideServlet", urlPatterns = {"/cancelRide"})
public class CancelRideServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String eventName = request.getParameter("eventName");

        String userName = (String)request.getSession().getAttribute("userName");
        ServerClient client = Users.getUserByFullName(userName);

        client.getDrivingEvents().remove(eventName);

        response.sendRedirect("driverOptionsMenu.jsp");
    }
}