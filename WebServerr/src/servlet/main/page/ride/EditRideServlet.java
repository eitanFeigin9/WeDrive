package servlet.main.page.ride;

import database.Users;
import entity.ServerClient;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import jakarta.servlet.http.HttpServlet;

import java.io.IOException;

@WebServlet(name = "EditRideServlet", urlPatterns = {"/editRide"})
public class EditRideServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String eventName = request.getParameter("eventName");

        String userName = (String)request.getSession().getAttribute("userName");
        ServerClient client = Users.getUserByFullName(userName);

        String maxCapacity = request.getParameter("maxCapacity");
        String pickupCity = request.getParameter("pickupCity");
        String fuelReturns = request.getParameter("fuelReturns");
        int maxCapacityInInt;
        int fuelReturnsInInt;

        try {
            maxCapacityInInt = Integer.parseInt(maxCapacity);
            fuelReturnsInInt = Integer.parseInt(fuelReturns);
        } catch (NumberFormatException e) {
            response.sendRedirect("editRideError.jsp");
            return;
        }
        client.deleteDrivingEvent(eventName);
        client.addNewDrivingEvent(eventName, maxCapacityInInt, pickupCity, fuelReturnsInInt);
        response.sendRedirect("driverOptionsMenu.jsp");
    }
}
