package servlet.main.page.ride;

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

@WebServlet(name = "CancelRideServlet", urlPatterns = {"/cancelRide"})
public class CancelRideServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String eventName = request.getParameter("eventName");

        String userName = (String)request.getSession().getAttribute("userName");
        ServerClient client = Users.getUserByUserName(userName);
        HashMap<String, ServerClient> webUsers = Users.getWebUsers();

        //remove all the hitchhikers that are in this drive
        DriverRide driverRide = client.getDrivingEventByName(eventName);
        List<Map.Entry<String, HitchhikerRide>> entries = new ArrayList<>(driverRide.getCurrentHitchhikers().entrySet());
        Collections.reverse(entries);
        List<String> hitchhikersToDelete = new LinkedList<>();
        for (Map.Entry<String, HitchhikerRide> entry : entries) {
            String hitchhikerName = entry.getKey();
            if (webUsers.containsKey(hitchhikerName)) {
                ServerClient user = webUsers.get(hitchhikerName);
                HitchhikerRide hitchhikerRide = user.getHitchhikingEventByName(eventName);
                MatchedDAO matchedDAO = new MatchedDAO();
                matchedDAO.deleteMatch(eventName,userName,hitchhikerName);
                    hitchhikersToDelete.add(hitchhikerName);
                }
            }
        for (String item : hitchhikersToDelete) {
            driverRide.removeHitchhiker(item);
        }
        Users.removeSpecificDriverRide(userName,eventName);
        Users.getUserByUserName(userName).getDrivingEvents().remove(eventName);
        response.sendRedirect("driverOptionsMenu.jsp");

    }


            }





