package servlet.main.page.ride;

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
        if (!client.addNewDrivingEvent(eventName, maxCapacityInInt, pickupCity, fuelReturnsInInt, driverLatitude, driverLongitude, maxPickupDistance)) {
            response.sendRedirect("eventExistsError.jsp"); //change to you are alreadt regiter as a driver to this event
        }
        else {
            //drive was added successfuly redirection
            DriverRide newRide = client.getDrivingEventByName(eventName);
            if (newRide.isTherePlace()) { //is there a place for a new hitchhiker
                Users userManager = ServletUtils.getUserManager(getServletContext());
                HashMap<String, ServerClient> webUsers = userManager.getWebUsers();
                for (Map.Entry<String, ServerClient> user : webUsers.entrySet()) {
                    if (user.getValue().checkHitchhikingEventExists(eventName) && !user.getKey().equals(userName) && newRide.isTherePlace()) {
                        HitchhikerRide hitchhikerRide = user.getValue().getHitchhikingEventByName(eventName);
                        if (hitchhikerRide.getFreeForPickup()) { //The hitchhiker is free for pickup
                            double hitchhikerLatitude = hitchhikerRide.getLatitude(); // Assuming these methods exist
                            double hitchhikerLongitude = hitchhikerRide.getLongitude();
                            double distance = calculateDistance(driverLatitude, driverLongitude, hitchhikerLatitude, hitchhikerLongitude);
                            if (distance <= maxPickupDistance) {
                                if (hitchhikerRide.getFuelMoney() >= newRide.getFuelReturnsPerHitchhiker()) {
                                    if (newRide.addNewHitchhiker(user.getValue().getFullName(), user.getValue().getPhoneNumber())){
                                        newRide.addToTotalFuelReturns(hitchhikerRide.getFuelMoney()); //ask eitan if it should be the hitchhiker or driver money
                                        hitchhikerRide.setFreeForPickup(false); //The hitchhiker is no longer free for pickup
                                        hitchhikerRide.setDriverName(client.getFullName());
                                        hitchhikerRide.setDriverPhone(client.getPhoneNumber());
                                    }
                                }
                            }

                        }

                    }
                }
            }
            response.sendRedirect("thankForNewRide.jsp?id=" + java.net.URLEncoder.encode(eventName, "UTF-8") + "&owner=" + java.net.URLEncoder.encode(eventOwner, "UTF-8"));
        }
    }

    private static final int EARTH_RADIUS = 6371; // Earth radius in kilometers

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c; // Distance in kilometers
    }
}


