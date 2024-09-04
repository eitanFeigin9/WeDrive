package servlet.main.page.ride;

import database.DriverRideDAO;
import database.EventsDAO;
import database.UsersDAO;
import entity.ServerClient;
import event.EventData;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ride.DriverRide;

import java.io.IOException;


@WebServlet(name = "CreateDriverRideServlet", urlPatterns = {"/createRide"})
@MultipartConfig
public class CreateDriverRideServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String driverName = (String) request.getSession().getAttribute("userName");
        //ServerClient client = Users.getUserByFullName(userName);
        String eventName = request.getParameter("eventName");
        String eventOwner = request.getParameter("eventOwner");
        //ServerClient owner = Users.getUserByFullName(eventOwner);
        UsersDAO usersDAO = new UsersDAO();
        EventsDAO eventsDAO = new EventsDAO();
        EventData event = eventsDAO.getEventByOwnerAndName(eventOwner,eventName);
        //EventData event = owner.getOwnedEventByName(eventName);
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

        DriverRideDAO driverRideDAO = new DriverRideDAO();
        ServerClient driver = usersDAO.getUserByFullName(driverName);
        if (!driverRideDAO.addNewDriverRide(eventName,eventAddress, Double.toString(eventLatitude) ,Double.toString(eventLongitude), maxCapacityInInt, pickupCity, fuelReturnsInInt, 0,0,Double.toString(driverLatitude), Double.toString(driverLongitude), maxPickupDistance,driverName)) {
            response.sendRedirect("eventExistsError.jsp");
        } else {
            DriverRide newRide = driverRideDAO.getDriverRideForUser(driverName, eventName);
            boolean matched = driverRideDAO.matchHitchhikersToDriver(driverName,driver.getPhoneNumber(), newRide, eventName, driverLatitude, driverLongitude, maxPickupDistance);
            response.sendRedirect("thankForNewRide.jsp?id=" + java.net.URLEncoder.encode(eventName, "UTF-8") + "&owner=" + java.net.URLEncoder.encode(eventOwner, "UTF-8"));
        }
    }


}


