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

@WebServlet(name = "CancelRideHitchhikerServlet", urlPatterns = {"/cancelRideHitchhiker"})
public class CancelRideHitchhikerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String eventName = request.getParameter("eventName");

        String userName = (String)request.getSession().getAttribute("userName");
        ServerClient client = Users.getUserByFullName(userName);

        Users userManager = ServletUtils.getUserManager(getServletContext());
        HashMap<String, ServerClient> webUsers = userManager.getWebUsers();

        //need to remove hitchhiker from the driver list and update the curr amount of hitchhikers
        HitchhikerRide hitchhikerRide = client.getHitchhikingEventByName(eventName);
        if (hitchhikerRide != null)
        {
            String driverName = hitchhikerRide.getDriverName();
            if (!driverName.isEmpty() && webUsers.containsKey(driverName)) {
                ServerClient driver = webUsers.get(driverName);
                DriverRide driverRide = driver.getDrivingEventByName(eventName);
                //driverRide.removeHitchhiker(client.getFullName()); //remove hitchiker from the driverRide
                //driverRide.lowerTotalFuelReturns(hitchhikerRide.getFuelMoney());
            }
        }
        client.getHitchhikingEvents().remove(eventName);


        response.sendRedirect("hitchhikerOptionsMenu.jsp");
    }
}