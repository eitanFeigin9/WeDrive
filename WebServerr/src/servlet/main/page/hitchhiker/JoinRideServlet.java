package servlet.main.page.hitchhiker;

import database.HitchhikerRideDAO;
import database.Users;
import entity.ServerClient;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ride.DriverRide;
import ride.HitchhikerRide;
import servlet.main.page.event.NewEventServlet;
import utils.ServletUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static utils.ServletUtils.calculateDistance;
//import static utils.ServletUtils.matchDriverToHitchhiker;

@WebServlet(name = "JoinRideServlet", urlPatterns = {"/joinRide"})
@MultipartConfig
public class JoinRideServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String hitchhikerName = (String) request.getSession().getAttribute("userName");
        ServerClient hitchhiker = Users.getUserByFullName(hitchhikerName);
        String eventName = request.getParameter("eventName");
        String eventOwner = request.getParameter("eventOwner");
        String pickupLocation = request.getParameter("eventLocation");
        String fuelMoneyStr = request.getParameter("fuelMoney");
        String latitudeStr = request.getParameter("latitude");   // Get latitude
        String longitudeStr = request.getParameter("longitude"); // Get longitude
        int fuelMoneyInInt;

        try {
            fuelMoneyInInt = Integer.parseInt(fuelMoneyStr);
        } catch (NumberFormatException e) {
            // Redirect to error page for invalid input
            response.sendRedirect("invalidInputError.jsp");
            return;
        }

        HitchhikerRideDAO hitchhikerRideDAO = new HitchhikerRideDAO();

        if (!hitchhikerRideDAO.insert(hitchhikerName, eventName, pickupLocation, latitudeStr, longitudeStr, fuelMoneyInInt, true)) {
            // Redirect if hitchhiker is already registered for this event
            response.sendRedirect("eventExistsError.jsp");
        } else {
            // Check if there is a matching driver for the hitchhiker
            if (hitchhikerRideDAO.matchDriverToHitchhiker(hitchhikerName, eventName)) {
                // Redirect to hitchhiker match page
                response.sendRedirect("hitchhikerFindMatch.jsp?hitchhikerName=" + hitchhikerName + "&eventName=" + eventName);
            } else {
                // Redirect to thank you page if no match is found
                response.sendRedirect("thankYouHitchhiker.jsp?id=" + java.net.URLEncoder.encode(eventName, "UTF-8") + "&owner=" + java.net.URLEncoder.encode(eventOwner, "UTF-8"));
            }
        }
    }
}
