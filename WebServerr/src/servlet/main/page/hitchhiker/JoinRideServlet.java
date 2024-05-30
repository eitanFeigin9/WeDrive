package servlet.main.page.hitchhiker;

import database.Users;
import entity.ServerClient;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servlet.main.page.event.NewEventServlet;
import utils.ServletUtils;

import java.io.IOException;

@WebServlet(name = "JoinRideServlet", urlPatterns = {"/joinRide"})
@MultipartConfig
public class JoinRideServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = (String) request.getSession().getAttribute("userName");
        String eventName2 = (String) request.getSession().getAttribute("eventName");
        ServerClient client = Users.getUserByFullName(userName);
        String eventName = request.getParameter("eventName");
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

            response.sendRedirect("thankYouHitchhiker.jsp");
        }
    }
}
