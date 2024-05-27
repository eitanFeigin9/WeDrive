package servlet.main.page.ride;

import database.Users;
import entity.ServerClient;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servlet.main.page.event.NewEventServlet;
import utils.ServletUtils;

import java.io.IOException;

@WebServlet(name = "CreateDriverRideServlet", urlPatterns = {"/createRide"})
@MultipartConfig
public class CreateDriverRideServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = (String) request.getSession().getAttribute("userName");
        String eventName2 = (String) request.getSession().getAttribute("eventName");
        ServerClient client = Users.getUserByFullName(userName);
        String eventName = request.getParameter("eventName");
        String maxCapacityInStr = request.getParameter("maxCapacity");
        String pickupCity = request.getParameter("pickupCity");
        String fuelReturnsPerPersonStr = request.getParameter("fuelReturns");
        int maxCapacityInInt;
        int fuelReturnsInInt;

        try {
            maxCapacityInInt = Integer.parseInt(maxCapacityInStr);
            fuelReturnsInInt = Integer.parseInt(fuelReturnsPerPersonStr);
        } catch (NumberFormatException e) {
            //redirect to error page
            return;
        }
        if (!client.addNewDrivingEvent(eventName, maxCapacityInInt,pickupCity,fuelReturnsInInt)) {
            response.sendRedirect("eventExistsError.jsp"); //change to you are alreadt regiter as a driver to this event
        }
        else {
            //drive was added successfuly redirection
            response.sendRedirect("thankForNewRide.jsp");
        }
    }
}
