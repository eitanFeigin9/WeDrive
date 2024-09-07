package servlet.main.page.ride;

import database.DriverRideDAO;
import database.MatchedDAO;
import database.Users;
import entity.ServerClient;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import jakarta.servlet.http.HttpServlet;
import ride.DriverRide;
import ride.HitchhikerDetails;
import ride.HitchhikerRide;
import utils.ServletUtils;

import java.io.IOException;
import java.util.*;

import static utils.ServletUtils.calculateDistance;
//import static utils.ServletUtils.matchHitchhikersToDriver;

@WebServlet(name = "EditRideServlet", urlPatterns = {"/editRide"})
public class EditRideServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        double driverLatitude;
        double driverLongitude;
        double maxPickupDistance;
        int maxCapacityInInt;
        int fuelReturnsInInt;
        MatchedDAO matchedDAO = new MatchedDAO();
        String eventName = request.getParameter("eventName");
        String driverName = (String)request.getSession().getAttribute("userName");
        ServerClient driver = Users.getUserByUserName(driverName);
        String sourceLocation = request.getParameter("sourceLocation");
        String sourceLatitude = request.getParameter("sourceLatitude");
        String sourceLongitude = request.getParameter("sourceLongitude");
        String maxCapacityInStr = request.getParameter("maxCapacity");
        String fuelReturnsPerPersonStr = request.getParameter("fuelReturns");
        String maxPickupDistanceStr = request.getParameter("maxPickupDistance");


        try {
            maxCapacityInInt = Integer.parseInt(maxCapacityInStr);
            fuelReturnsInInt = Integer.parseInt(fuelReturnsPerPersonStr);
            driverLatitude = Double.parseDouble(sourceLatitude);
            driverLongitude = Double.parseDouble(sourceLongitude);
            maxPickupDistance = Double.parseDouble(maxPickupDistanceStr);
        } catch (NumberFormatException e) {
            response.sendRedirect("editRideError.jsp");
            return;
        }
        Users userManager = ServletUtils.getUserManager(getServletContext());
        HashMap<String, ServerClient> webUsers = userManager.getWebUsers();
        DriverRide driverRide = driver.getDrivingEventByName(eventName);
        int copyOfNewMaxCapacity = maxCapacityInInt;
        //In case the driver increased the fuel return per hitchhiker
        if (fuelReturnsInInt > driverRide.getFuelReturnsPerHitchhiker() || !sourceLocation.equals(driverRide.getSourceLocation()) || maxPickupDistance < driverRide.getMaxPickupDistance()) {
            HashMap<String, HitchhikerRide> hitchhikers = driverRide.getCurrentHitchhikers();
            List<String> hitchhikersToDelete = new LinkedList<>();
            for (String hitchhikerName : hitchhikers.keySet()) {
                if (webUsers.containsKey(hitchhikerName)) {
                    ServerClient user = webUsers.get(hitchhikerName);
                    HitchhikerRide hitchhikerRide = user.getHitchhikingEventByName(eventName);
                    double hitchhikerLatitude = hitchhikerRide.getLatitude();
                    double hitchhikerLongitude = hitchhikerRide.getLongitude();
                    double distance = calculateDistance(driverLatitude, driverLongitude, hitchhikerLatitude, hitchhikerLongitude);
                    if (hitchhikerRide.getFuelMoney() < fuelReturnsInInt || distance > maxPickupDistance) { //this hitchhiker can't afford this ride
                        matchedDAO.deleteMatch(eventName,driverName,hitchhikerName);

                       /* hitchhikerRide.setFreeForPickup(true);
                        hitchhikerRide.setDriverName("");
                        hitchhikerRide.setDriverPhone("");  */
                        hitchhikersToDelete.add(hitchhikerName);


                    /*    driverRide.lowerTotalFuelReturns(hitchhikerRide.getFuelMoney());
                        copyOfNewMaxCapacity--;

                     */
                    }
                }
            }
            for (String item : hitchhikersToDelete) {
                driverRide.removeHitchhiker(item);
            }


        }
        //In case the driver decreased the max capacity for this ride
        if (maxCapacityInInt < driverRide.getMaxCapacity() && (driverRide.getMaxCapacity()-driverRide.getFreeCapacity()) > maxCapacityInInt) {
            int numOfHitchhikersToDelete = driverRide.getMaxCapacity()-driverRide.getFreeCapacity() - maxCapacityInInt;
            HashMap<String,HitchhikerRide> hitchhikers = driverRide.getCurrentHitchhikers();
            Iterator<Map.Entry<String, HitchhikerRide>> iterator = hitchhikers.entrySet().iterator();

            while (numOfHitchhikersToDelete > 0 && iterator.hasNext()) {
                Map.Entry<String, HitchhikerRide> hitchhikerEntry = iterator.next();
                String hitchhikerName = hitchhikerEntry.getKey();

                if (webUsers.containsKey(hitchhikerName)) {
                    matchedDAO.deleteMatch(eventName,driverName,hitchhikerName);
                    /*ServerClient user = webUsers.get(hitchhikerName);
                    HitchhikerRide hitchhikerRide = user.getHitchhikingEventByName(eventName);
                    hitchhikerRide.setFreeForPickup(true);
                    hitchhikerRide.setDriverName("");
                    hitchhikerRide.setDriverPhone("");

                     */
                    //driverRide.lowerTotalFuelReturns(hitchhikerRide.getFuelMoney());
                }

                iterator.remove();
                driverRide.removeHitchhiker(hitchhikerName);
                numOfHitchhikersToDelete--;
            }
        }
        driverRide.setMaxCapacity(maxCapacityInInt);
        driverRide.setSourceLocation(sourceLocation);
        driverRide.setFuelReturnsPerHitchhiker(fuelReturnsInInt);
        driverRide.setMaxPickupDistance(maxPickupDistance);
        driverRide.setSourceLatitude(driverLatitude);
        driverRide.setSourceLongitude(driverLongitude);
        //matchHitchhikersToDriver checks if there are other passengers that match this driver after updating the details
        DriverRideDAO driverRideDAO=new DriverRideDAO();
        driverRideDAO.matchHitchhikersToDriver(driverName, eventName);
        response.sendRedirect("driverOptionsMenu.jsp");
    }
}

