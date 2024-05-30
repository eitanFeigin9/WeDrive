package servlet.main.page.hitchhiker;

import database.Users;
import entity.ServerClient;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import jakarta.servlet.http.HttpServlet;

import java.io.IOException;

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
        client.deleteHitchhikingEvent(eventName);
        client.addNewHitchhikingEvent(eventName, pickupCity, fuelMoneyInInt);
        response.sendRedirect("hitchhikerOptionsMenu.jsp");
    }
}