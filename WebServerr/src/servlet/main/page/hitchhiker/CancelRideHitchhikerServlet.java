package servlet.main.page.hitchhiker;

import database.Users;
import entity.ServerClient;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import jakarta.servlet.http.HttpServlet;

import java.io.IOException;

@WebServlet(name = "CancelRideHitchhikerServlet", urlPatterns = {"/cancelRideHitchhiker"})
public class CancelRideHitchhikerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String eventName = request.getParameter("eventName");

        String userName = (String)request.getSession().getAttribute("userName");
        ServerClient client = Users.getUserByFullName(userName);

        client.getHitchhikingEvents().remove(eventName);
        //need to remove hitchhiker from the driver list and update the curr amount of hitchhikers

        response.sendRedirect("hitchhikerOptionsMenu.jsp");
    }
}