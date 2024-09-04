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
import static utils.ServletUtils.matchDriverToHitchhiker;

@WebServlet(name = "JoinRideServlet", urlPatterns = {"/joinRide"})
@MultipartConfig
public class JoinRideServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = (String) request.getSession().getAttribute("userName");
        ServerClient client = Users.getUserByFullName(userName);
        String eventName = request.getParameter("eventName");
        String eventOwner = request.getParameter("eventOwner");
        String pickupCity = request.getParameter("pickupCity");
        String fuelMoneyStr = request.getParameter("fuelMoney");
        String latitudeStr = request.getParameter("latitude");   // Get latitude
        String longitudeStr = request.getParameter("longitude"); // Get longitude
        int fuelMoneyInInt;
        double hitchhikerLatitude;
        double hitchhikerLongitude;

        try {
            fuelMoneyInInt = Integer.parseInt(fuelMoneyStr);
            hitchhikerLatitude = Double.parseDouble(latitudeStr);   // Parse latitude
            hitchhikerLongitude = Double.parseDouble(longitudeStr); // Parse longitude
        } catch (NumberFormatException e) {
            //redirect to error page
            return;
        }
        if (!client.addNewHitchhikingEvent(eventName,pickupCity,fuelMoneyInInt,hitchhikerLatitude, hitchhikerLongitude)) {
            response.sendRedirect("eventExistsError.jsp"); //change to you are already register as a hitchhiker to this event
        }
        else {
            //add to the database
            HitchhikerRideDAO hitchhikerRideDAO = new HitchhikerRideDAO();
            hitchhikerRideDAO.addNewHitchhikerRide(eventName, pickupCity, fuelMoneyInInt, true, "", "", latitudeStr, longitudeStr, userName);
            HitchhikerRide newRide = client.getHitchhikingEventByName(eventName);
            //Users userManager = ServletUtils.getUserManager(getServletContext());
            if (hitchhikerRideDAO.matchDriverToHitchhiker(newRide,userName,hitchhikerLatitude,hitchhikerLongitude)) {
                response.sendRedirect("thankYouHitchhiker.jsp");
            }
            response.sendRedirect("thankYouHitchhiker.jsp?id=" + java.net.URLEncoder.encode(eventName, "UTF-8") + "&owner=" + java.net.URLEncoder.encode(eventOwner, "UTF-8"));
        }

    }
}
