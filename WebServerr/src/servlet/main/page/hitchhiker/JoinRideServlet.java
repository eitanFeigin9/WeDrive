package servlet.main.page.hitchhiker;

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

@WebServlet(name = "JoinRideServlet", urlPatterns = {"/joinRide"})
@MultipartConfig
public class JoinRideServlet extends HttpServlet {
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
            response.sendRedirect("eventExistsError.jsp"); //change to you are already regiter as a hitchhiker to this event
        }
        else {
            HitchhikerRide newRide = client.getHitchhikingEventByName(eventName);
            Users userManager = ServletUtils.getUserManager(getServletContext());
            HashMap<String, ServerClient> webUsers = userManager.getWebUsers();
            for (Map.Entry<String, ServerClient> user : webUsers.entrySet()) {
                if (user.getValue().checkDrivingEventExists(eventName) && !user.getKey().equals(userName)) {
                    DriverRide driverRide = user.getValue().getDrivingEventByName(eventName);
                    double driverLatitude = driverRide.getLatitude();
                    double driverLongitude = driverRide.getLongitude();
                    double distance = calculateDistance(driverLatitude, driverLongitude, hitchhikerLatitude, hitchhikerLongitude);
                    if (driverRide.isTherePlace() && distance <= 5.0) {
                        if (newRide.getFuelMoney() >= driverRide.getFuelReturnsPerHitchhiker()) {
                            if (driverRide.addNewHitchhiker(client.getFullName(), client.getPhoneNumber())){
                                driverRide.addToTotalFuelReturns(newRide.getFuelMoney()); //ask eitan if it should be the hitchhiker or driver money
                                newRide.setFreeForPickup(false); //The hitchhiker is no longer free for pickup
                                newRide.setDriverName(user.getValue().getFullName());
                                newRide.setDriverPhone(user.getValue().getPhoneNumber());
                                response.sendRedirect("thankYouHitchhiker.jsp");
                                return;
                            }
                        }
                    }
                }
            }
            response.sendRedirect("thankYouHitchhiker.jsp?id=" + java.net.URLEncoder.encode(eventName, "UTF-8") + "&owner=" + java.net.URLEncoder.encode(eventOwner, "UTF-8"));
        }

    }
}
