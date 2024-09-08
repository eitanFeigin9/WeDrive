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
        // Get form data from the request parameters
        String eventName = request.getParameter("eventName");
        String driverName = (String) request.getSession().getAttribute("userName"); // Assuming the username is stored in session
        String sourceLocation = request.getParameter("sourceLocation");
        String sourceLatitude = request.getParameter("sourceLatitude");
        String sourceLongitude = request.getParameter("sourceLongitude");
        String maxCapacityInStr = request.getParameter("maxCapacity");
        String fuelReturnsPerPersonStr = request.getParameter("fuelReturns");
        String maxPickupDistanceStr = request.getParameter("maxPickupDistance");

        ServerClient driver = Users.getUserByUserName(driverName);



        try {
            maxCapacityInInt = Integer.parseInt(maxCapacityInStr);
            fuelReturnsInInt = Integer.parseInt(fuelReturnsPerPersonStr);
            driverLatitude = Double.parseDouble(sourceLatitude);
            driverLongitude = Double.parseDouble(sourceLongitude);
            maxPickupDistance = Double.parseDouble(maxPickupDistanceStr);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "You entered text instead of number in one of the fields.");
            request.getRequestDispatcher("/editRide.jsp").forward(request, response);
            return;
        }
        HashMap<String, ServerClient> webUsers = Users.getWebUsers();
        DriverRide driverRide = driver.getDrivingEventByName(eventName);
        int copyOfNewMaxCapacity = maxCapacityInInt;
        //In case the driver increased the fuel return per hitchhiker
        if (fuelReturnsInInt > driverRide.getFuelReturnsPerHitchhiker() || !sourceLocation.equals(driverRide.getSourceLocation()) || maxPickupDistance < driverRide.getMaxPickupDistance()) {
            List<Map.Entry<String, HitchhikerRide>> entries = new ArrayList<>(driverRide.getCurrentHitchhikers().entrySet());
            Collections.reverse(entries);
            //HashMap<String, HitchhikerRide> hitchhikers = driverRide.getCurrentHitchhikers();
            List<String> hitchhikersToDelete = new LinkedList<>();
          //  for (String hitchhikerName : hitchhikers.keySet()) {
            for (Map.Entry<String, HitchhikerRide> entry : entries) {
                String hitchhikerName = entry.getKey();
                if (webUsers.containsKey(hitchhikerName)) {
                    ServerClient user = webUsers.get(hitchhikerName);
                    HitchhikerRide hitchhikerRide = user.getHitchhikingEventByName(eventName);
                    double hitchhikerLatitude = hitchhikerRide.getLatitude();
                    double hitchhikerLongitude = hitchhikerRide.getLongitude();
                    double distance = calculateDistance(driverLatitude, driverLongitude, hitchhikerLatitude, hitchhikerLongitude);
                    if (hitchhikerRide.getFuelMoney() < fuelReturnsInInt || distance > maxPickupDistance) { //this hitchhiker can't afford this ride
                        matchedDAO.deleteMatch(eventName,driverName,hitchhikerName);
                        hitchhikersToDelete.add(hitchhikerName);
                       // driverRide.lowerTotalFuelReturns(hitchhikerRide.getFuelMoney());
                       // copyOfNewMaxCapacity--;

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
            List<String> hitchhikerKeys = new ArrayList<>(driverRide.getCurrentHitchhikers().keySet());
            ListIterator<String> iterator = hitchhikerKeys.listIterator(hitchhikerKeys.size());

          //  HashMap<String,HitchhikerRide> hitchhikers = driverRide.getCurrentHitchhikers();
           // Iterator<Map.Entry<String, HitchhikerRide>> iterator = hitchhikers.entrySet().iterator();
            while (numOfHitchhikersToDelete > 0 && iterator.hasPrevious()) {
          //  while (numOfHitchhikersToDelete > 0 && iterator.hasNext()) {
                //Map.Entry<String, HitchhikerRide> hitchhikerEntry = iterator.next();
                String hitchhikerName = iterator.previous();

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
        boolean isMatch = driverRideDAO.matchHitchhikersToDriver(driverName, eventName);
        if(isMatch){
            response.sendRedirect("driverFindMatch.jsp?userName=" + driverName + "&eventName=" + eventName);

        }
        else {                 response.sendRedirect("driverOptionsMenu.jsp?userName=" + driverName);

        }
    }


}


