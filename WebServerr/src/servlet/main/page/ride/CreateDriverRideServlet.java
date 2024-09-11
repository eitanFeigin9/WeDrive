package servlet.main.page.ride;

import database.DriverRideDAO;
import database.EventsDAO;
import database.Users;
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
        ServerClient client = Users.getUserByUserName(driverName);
        String eventName = request.getParameter("eventName");

        String eventOwner = request.getParameter("eventOwner");
        ServerClient owner = Users.getUserByUserName(eventOwner);

        String sourceLocation = request.getParameter("sourceLocation");
        String sourceLatitude = request.getParameter("sourceLatitude");
        String sourceLongitude = request.getParameter("sourceLongitude");
        UsersDAO usersDAO = new UsersDAO();
        EventsDAO eventsDAO = new EventsDAO();
        EventData event = owner.getOwnedEventByName(eventName);
        double eventLatitude = event.getLatitude();
        String eventAddress = event.getLocation();
        double eventLongitude = event.getLongitude();
        String latitudeStr = Double.toString(eventLatitude);
        String longitudeStr = Double.toString(eventLongitude);
        String maxCapacityInStr = request.getParameter("maxCapacity");
        String fuelReturnsPerPersonStr = request.getParameter("fuelReturns");
        String maxPickupDistanceStr = request.getParameter("maxPickupDistance");
        int maxCapacityInInt;
        int fuelReturnsInInt;
        double driverLatitude;
        double driverLongitude;
        double maxPickupDistance;

        try {
            maxCapacityInInt = Integer.parseInt(maxCapacityInStr);
            fuelReturnsInInt = Integer.parseInt(fuelReturnsPerPersonStr);
         //   driverLatitude = Double.parseDouble(latitudeStr);   // Parse latitude
           // driverLongitude = Double.parseDouble(longitudeStr); // Parse longitude
            maxPickupDistance = Double.parseDouble(maxPickupDistanceStr);
        } catch (NumberFormatException e) {
            //redirect to error page
            //תקלה שהוכנס טקסט לאזור של מספר
            return;
        }
        DriverRideDAO driverRideDAO = new DriverRideDAO();
        ServerClient driver = usersDAO.getUserByFullName(driverName);
        if (!driverRideDAO.insertDriverRide(driverName,eventName,eventAddress, latitudeStr ,longitudeStr, maxCapacityInInt, sourceLocation,sourceLatitude, sourceLongitude,fuelReturnsInInt, maxPickupDistance)) {
            response.sendRedirect("eventExistsError.jsp");
        } else {
            DriverRide newRide =client.getDrivingEventByName(eventName);
            boolean matched = driverRideDAO.matchHitchhikersToDriver(driverName, eventName);
            if(matched){
                response.sendRedirect("driverFindMatch.jsp?userName=" + driverName + "&eventName=" + eventName);

            }
            else {            response.sendRedirect("thankForNewRide.jsp?id=" + java.net.URLEncoder.encode(eventName, "UTF-8") + "&owner=" + java.net.URLEncoder.encode(eventOwner, "UTF-8"));
            }
        }
    }


}


