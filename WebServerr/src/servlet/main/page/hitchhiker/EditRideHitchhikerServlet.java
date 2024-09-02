package servlet.main.page.hitchhiker;

import database.Users;
import entity.ServerClient;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import jakarta.servlet.http.HttpServlet;
import ride.DriverRide;
import ride.HitchhikerRide;
import utils.ServletUtils;

import java.io.IOException;
import java.util.HashMap;

import static utils.ServletUtils.calculateDistance;
import static utils.ServletUtils.matchDriverToHitchhiker;

@WebServlet(name = "EditRideHitchhikerServlet", urlPatterns = {"/editRideHitchhiker"})
public class EditRideHitchhikerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String eventName = request.getParameter("eventName");

        String userName = (String)request.getSession().getAttribute("userName");
        ServerClient client = Users.getUserByFullName(userName);

        String pickupCity = request.getParameter("pickupCity");
        String fuelMoney = request.getParameter("fuelMoney");
        String latitudeStr = request.getParameter("latitude");
        String longitudeStr = request.getParameter("longitude");
        int fuelMoneyInInt;
        double hitchhikerLatitude;
        double hitchhikerLongitude;

        try {
            fuelMoneyInInt = Integer.parseInt(fuelMoney);
            hitchhikerLatitude = Double.parseDouble(latitudeStr);
            hitchhikerLongitude = Double.parseDouble(longitudeStr);
        } catch (NumberFormatException e) {
            response.sendRedirect("editRideError.jsp");
            return;
        }
        if (client.checkHitchhikingEventExists(eventName)) {
            Users userManager = ServletUtils.getUserManager(getServletContext());
            HitchhikerRide hitchhikerRide = client.getHitchhikingEventByName(eventName);
            if (!hitchhikerRide.getFreeForPickup()) { //this hitchhiker has a driver
                HashMap<String, ServerClient> webUsers = userManager.getWebUsers();
                String driverName = hitchhikerRide.getDriverName();
                if (webUsers.containsKey(driverName)) {
                    DriverRide driverRide = webUsers.get(driverName).getDrivingEventByName(eventName);
                    double driverLatitude = hitchhikerRide.getLatitude();
                    double driverLongitude = hitchhikerRide.getLongitude();
                    double distance = calculateDistance(driverLatitude, driverLongitude, hitchhikerLatitude, hitchhikerLongitude);
                    if (fuelMoneyInInt < driverRide.getFuelReturnsPerHitchhiker() || distance > driverRide.getMaxPickupDistance()) {
                        driverRide.removeHitchhiker(userName);
                        driverRide.lowerTotalFuelReturns(hitchhikerRide.getFuelMoney());
                        hitchhikerRide.setDriverPhone("");
                        hitchhikerRide.setDriverName("");
                        hitchhikerRide.setFreeForPickup(true);
                    }
                }
            }
            hitchhikerRide.setPickupCity(pickupCity);
            hitchhikerRide.setFuelMoney(fuelMoneyInInt);
            hitchhikerRide.setLatitude(hitchhikerLatitude);
            hitchhikerRide.setLongitude(hitchhikerLongitude);
            boolean isMatchFound = matchDriverToHitchhiker(client, hitchhikerRide, userManager, eventName, userName, hitchhikerLatitude,hitchhikerLongitude);
        }
        response.sendRedirect("hitchhikerOptionsMenu.jsp");
    }
}