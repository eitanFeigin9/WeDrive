package servlet.main.page.ride;

import database.DriverRideDAO;
import database.Users;
import entity.ServerClient;
import event.EventData;
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
import static utils.ServletUtils.matchHitchhikersToDriver;

@WebServlet(name = "CreateDriverRideServlet", urlPatterns = {"/createRide"})
@MultipartConfig
public class CreateDriverRideServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = (String) request.getSession().getAttribute("userName");
        ServerClient client = Users.getUserByFullName(userName);
        String eventName = request.getParameter("eventName");
        String eventOwner = request.getParameter("eventOwner");
        ServerClient owner = Users.getUserByFullName(eventOwner);
        EventData event = owner.getOwnedEventByName(eventName);
        double eventLatitude = event.getLatitude();
        String eventAddress = event.getLocation();
        double eventLongitude = event.getLongitude();
        String maxCapacityInStr = request.getParameter("maxCapacity");
        String pickupCity = request.getParameter("pickupCity");
        String fuelReturnsPerPersonStr = request.getParameter("fuelReturns");
        String latitudeStr = request.getParameter("latitude");
        String longitudeStr = request.getParameter("longitude");
        String maxPickupDistanceStr = request.getParameter("maxPickupDistance");
        int maxCapacityInInt;
        int fuelReturnsInInt;
        double driverLatitude;
        double driverLongitude;
        double maxPickupDistance;

        try {
            maxCapacityInInt = Integer.parseInt(maxCapacityInStr);
            fuelReturnsInInt = Integer.parseInt(fuelReturnsPerPersonStr);
            driverLatitude = Double.parseDouble(latitudeStr);   // Parse latitude
            driverLongitude = Double.parseDouble(longitudeStr); // Parse longitude
            maxPickupDistance = Double.parseDouble(maxPickupDistanceStr);
        } catch (NumberFormatException e) {
            //redirect to error page
            return;
        }
        if (!client.addNewDrivingEvent(eventName,eventAddress, maxCapacityInInt, pickupCity, fuelReturnsInInt, driverLatitude, driverLongitude, maxPickupDistance,eventLatitude,eventLongitude)) {
            response.sendRedirect("eventExistsError.jsp"); //change to you are alreadt regiter as a driver to this event
        }
        else {
            //add to database
            DriverRideDAO driverRideDAO = new DriverRideDAO();
            driverRideDAO.addNewDriverRide(eventName, eventAddress, Double.toString(eventLatitude), Double.toString(eventLongitude), maxCapacityInInt, pickupCity, fuelReturnsInInt, 0, 0, latitudeStr,longitudeStr,maxPickupDistance);
            //drive was added successfuly redirection
            Users userManager = ServletUtils.getUserManager(getServletContext());
            DriverRide newRide = client.getDrivingEventByName(eventName);
            matchHitchhikersToDriver(client, newRide, userManager, eventName, userName, driverLatitude, driverLongitude, maxPickupDistance);
            response.sendRedirect("thankForNewRide.jsp?id=" + java.net.URLEncoder.encode(eventName, "UTF-8") + "&owner=" + java.net.URLEncoder.encode(eventOwner, "UTF-8"));
        }
    }


}


