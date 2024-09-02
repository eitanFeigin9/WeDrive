package servlet.main.page.ride;

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
import java.util.*;

import static utils.ServletUtils.calculateDistance;
import static utils.ServletUtils.matchHitchhikersToDriver;

@WebServlet(name = "EditRideServlet", urlPatterns = {"/editRide"})
public class EditRideServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String eventName = request.getParameter("eventName");

        String userName = (String)request.getSession().getAttribute("userName");
        ServerClient client = Users.getUserByFullName(userName);

        String maxCapacity = request.getParameter("maxCapacity");
        String pickupCity = request.getParameter("pickupCity");
        String fuelReturns = request.getParameter("fuelReturns");
        String latitudeStr = request.getParameter("latitude");
        String longitudeStr = request.getParameter("longitude");
        String maxPickupDistanceStr = request.getParameter("maxPickupDistance");
        double driverLatitude;
        double driverLongitude;
        double maxPickupDistance;
        int maxCapacityInInt;
        int fuelReturnsInInt;

        try {
            maxCapacityInInt = Integer.parseInt(maxCapacity);
            fuelReturnsInInt = Integer.parseInt(fuelReturns);
            driverLatitude = Double.parseDouble(latitudeStr);
            driverLongitude = Double.parseDouble(longitudeStr);
            maxPickupDistance = Double.parseDouble(maxPickupDistanceStr);
        } catch (NumberFormatException e) {
            response.sendRedirect("editRideError.jsp");
            return;
        }
        Users userManager = ServletUtils.getUserManager(getServletContext());
        HashMap<String, ServerClient> webUsers = userManager.getWebUsers();
        DriverRide driverRide = client.getDrivingEventByName(eventName);
        int copyOfNewMaxCapacity = maxCapacityInInt;
        //In case the driver increased the fuel return per hitchhiker
        if (fuelReturnsInInt > driverRide.getFuelReturnsPerHitchhiker() || !pickupCity.equals(driverRide.getPickupCity()) || maxPickupDistance < driverRide.getMaxPickupDistance()) {
            HashMap<String,String> hitchhikers = driverRide.getCurrentHitchhikers();
            List<String> hitchhikersToDelete = new LinkedList<>();
            for (String hitchhikerName : hitchhikers.keySet()) {
                if (webUsers.containsKey(hitchhikerName)) {
                    ServerClient user = webUsers.get(hitchhikerName);
                    HitchhikerRide hitchhikerRide = user.getHitchhikingEventByName(eventName);
                    double hitchhikerLatitude = hitchhikerRide.getLatitude();
                    double hitchhikerLongitude = hitchhikerRide.getLongitude();
                    double distance = calculateDistance(driverLatitude, driverLongitude, hitchhikerLatitude, hitchhikerLongitude);
                    if (hitchhikerRide.getFuelMoney() < fuelReturnsInInt || distance > maxPickupDistance) { //this hitchhiker can't afford this ride
                        hitchhikerRide.setFreeForPickup(true);
                        hitchhikerRide.setDriverName("");
                        hitchhikerRide.setDriverPhone("");
                        hitchhikersToDelete.add(hitchhikerName);
                        driverRide.lowerTotalFuelReturns(hitchhikerRide.getFuelMoney());
                        copyOfNewMaxCapacity--;
                    }
                }
            }
            for (String item : hitchhikersToDelete) {
                driverRide.removeHitchhiker(item);
            }
        }
        //In case the driver decreased the max capacity for this ride
        if (maxCapacityInInt < driverRide.getMaxCapacity() && driverRide.getCurrNumOfHitchhikers() > maxCapacityInInt) {
            int numOfHitchhikersToDelete = driverRide.getCurrNumOfHitchhikers() - maxCapacityInInt;
            HashMap<String,String> hitchhikers = driverRide.getCurrentHitchhikers();
            Iterator<Map.Entry<String, String>> iterator = hitchhikers.entrySet().iterator();

            while (numOfHitchhikersToDelete > 0 && iterator.hasNext()) {
                Map.Entry<String, String> hitchhikerEntry = iterator.next();
                String hitchhikerName = hitchhikerEntry.getKey();

                if (webUsers.containsKey(hitchhikerName)) {
                    ServerClient user = webUsers.get(hitchhikerName);
                    HitchhikerRide hitchhikerRide = user.getHitchhikingEventByName(eventName);
                    hitchhikerRide.setFreeForPickup(true);
                    hitchhikerRide.setDriverName("");
                    hitchhikerRide.setDriverPhone("");
                    driverRide.lowerTotalFuelReturns(hitchhikerRide.getFuelMoney());
                }

                iterator.remove();
                driverRide.removeHitchhiker(hitchhikerName);
                driverRide.lowerNumberOfCurrHitchhikers();
                numOfHitchhikersToDelete--;
            }
        }
        driverRide.setMaxCapacity(maxCapacityInInt);
        driverRide.setPickupCity(pickupCity);
        driverRide.setFuelReturnsPerHitchhiker(fuelReturnsInInt);
        driverRide.setMaxPickupDistance(maxPickupDistance);
        driverRide.setLatitude(driverLatitude);
        driverRide.setLongitude(driverLongitude);
        //matchHitchhikersToDriver checks if there are other passengers that match this driver after updating the details
        matchHitchhikersToDriver(client, driverRide, userManager, eventName, userName, driverLatitude, driverLongitude, maxPickupDistance);
        response.sendRedirect("driverOptionsMenu.jsp");
    }
}
