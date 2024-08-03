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
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = (String) request.getSession().getAttribute("userName");
        ServerClient client = Users.getUserByFullName(userName);
        String eventName = request.getParameter("eventName");
        String eventOwner = request.getParameter("eventOwner");
        String pickupCity = request.getParameter("pickupCity");
        String fuelMoneyStr = request.getParameter("fuelMoney");
        int fuelMoneyInInt;

        try {
            fuelMoneyInInt = Integer.parseInt(fuelMoneyStr);
        } catch (NumberFormatException e) {
            //redirect to error page
            return;
        }
        if (!client.addNewHitchhikingEvent(eventName,pickupCity,fuelMoneyInInt)) {
            response.sendRedirect("eventExistsError.jsp"); //change to you are already regiter as a hitchhiker to this event
        }
        else {
            HitchhikerRide newRide = client.getHitchhikingEventByName(eventName);
            Users userManager = ServletUtils.getUserManager(getServletContext());
            HashMap<String, ServerClient> webUsers = userManager.getWebUsers();
            for (Map.Entry<String, ServerClient> user : webUsers.entrySet()) {
                if (user.getValue().checkDrivingEventExists(eventName) && !user.getKey().equals(userName)) {
                    DriverRide driverRide = user.getValue().getDrivingEventByName(eventName);
                    if (driverRide.isTherePlace() && driverRide.getPickupCity().equals(newRide.getPickupCity())) {
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
