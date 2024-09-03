package servlet.main.page.ride;

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
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "CancelRideServlet", urlPatterns = {"/cancelRide"})
public class CancelRideServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String eventName = request.getParameter("eventName");

        String userName = (String)request.getSession().getAttribute("userName");
        ServerClient client = Users.getUserByFullName(userName);

        Users userManager = ServletUtils.getUserManager(getServletContext());
        HashMap<String, ServerClient> webUsers = userManager.getWebUsers();

        //remove all the hitchhikers that are in this drive
        DriverRide driverRide = client.getDrivingEventByName(eventName);
        HashMap<String, HitchhikerDetails> hitchhikers = driverRide.getCurrentHitchhikers();
        for (String hitchhikerName : hitchhikers.keySet()) {
            if (webUsers.containsKey(hitchhikerName)) {
                ServerClient user = webUsers.get(hitchhikerName);
                HitchhikerRide hitchhikerRide = user.getHitchhikingEventByName(eventName);
                hitchhikerRide.setFreeForPickup(true);
                hitchhikerRide.setDriverName("");
                hitchhikerRide.setDriverPhone("");
                //driverRide.removeHitchhiker(hitchhikerName);
            }
        }

        client.getDrivingEvents().remove(eventName);

        response.sendRedirect("driverOptionsMenu.jsp");
    }
}