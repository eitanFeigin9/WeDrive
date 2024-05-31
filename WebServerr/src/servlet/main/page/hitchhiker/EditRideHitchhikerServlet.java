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

@WebServlet(name = "EditRideHitchhikerServlet", urlPatterns = {"/editRideHitchhiker"})
public class EditRideHitchhikerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String eventName = request.getParameter("eventName");

        String userName = (String)request.getSession().getAttribute("userName");
        ServerClient client = Users.getUserByFullName(userName);

        String pickupCity = request.getParameter("pickupCity");
        String fuelMoney = request.getParameter("fuelMoney");
        int fuelMoneyInInt;

        try {
            fuelMoneyInInt = Integer.parseInt(fuelMoney);
        } catch (NumberFormatException e) {
            response.sendRedirect("editRideError.jsp");
            return;
        }
        if (client.checkHitchhikingEventExists(eventName)) {
            HitchhikerRide hitchhikerRide = client.getHitchhikingEventByName(eventName);
            if (!hitchhikerRide.getFreeForPickup()) { //this hitchhiker has a driver
                Users userManager = ServletUtils.getUserManager(getServletContext());
                HashMap<String, ServerClient> webUsers = userManager.getWebUsers();
                String driverName = hitchhikerRide.getDriverName();
                if (webUsers.containsKey(driverName)) {
                    DriverRide driverRide = webUsers.get(driverName).getDrivingEventByName(eventName);
                    if (fuelMoneyInInt < driverRide.getFuelReturnsPerHitchhiker() || !pickupCity.equals(driverRide.getPickupCity())) {
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
        }
        response.sendRedirect("hitchhikerOptionsMenu.jsp");
    }
}