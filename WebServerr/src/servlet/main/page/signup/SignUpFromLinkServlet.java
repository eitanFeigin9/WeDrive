package servlet.main.page.signup;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import database.EventsDAO;
import database.Users;
import database.UsersDAO;
import entity.ServerClient;
import event.EventData;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;

@WebServlet(name = "SignUpFromLinkServlet", urlPatterns = {"/signupFromLink"})
public class SignUpFromLinkServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // the version with database
        UsersDAO usersDAO = new UsersDAO();
        EventsDAO eventsDAO = new EventsDAO();

        String fullName = request.getParameter("fullName");
        String userName = request.getParameter("userName");
        String eventName = request.getParameter("id");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String securityAnswer = request.getParameter("securityAnswer");
        String eventOwnerName = request.getParameter("owner");
        HashMap<String, EventData> ownedEvents = Users.getUserByUserName(eventOwnerName).getOwnedEvents();

        if (!ownedEvents.isEmpty())
        {
            EventData eventData = ownedEvents.get(eventName);
            if (eventData != null && eventData.getFileName() != null && !(eventData.getGuestList().isEmpty()))
            {
                HashSet<String> guestList = eventData.getGuestList();
                if (guestList.contains(fullName)) {
                    if(!usersDAO.addNewUser(fullName,userName,email,phone,password,securityAnswer)){
                        response.sendRedirect("signUpFromLinkNameError.jsp?id=" + java.net.URLEncoder.encode(eventName, "UTF-8") + "&owner=" + java.net.URLEncoder.encode(eventOwnerName, "UTF-8"));
                    }
                    else {
                        response.sendRedirect("loginFromLink.jsp?id=" + java.net.URLEncoder.encode(eventName, "UTF-8") + "&owner=" + java.net.URLEncoder.encode(eventOwnerName, "UTF-8"));

                    }
                }
                else {
                    response.sendRedirect("notInGuestListError.jsp?id=" + java.net.URLEncoder.encode(eventName, "UTF-8") + "&owner=" + java.net.URLEncoder.encode(eventOwnerName, "UTF-8"));
                }
            }
            else if (eventData != null) //did not insert guest list
            {
                if(!usersDAO.addNewUser(fullName,userName,email,phone,password,securityAnswer)){
                    response.sendRedirect("signUpFromLinkNameError.jsp?id=" + java.net.URLEncoder.encode(eventName, "UTF-8") + "&owner=" + java.net.URLEncoder.encode(eventOwnerName, "UTF-8"));
                }
                else {
                    response.sendRedirect("loginFromLink.jsp?id=" + java.net.URLEncoder.encode(eventName, "UTF-8") + "&owner=" + java.net.URLEncoder.encode(eventOwnerName, "UTF-8"));
                }
            }
            else {
                //different redirection
                response.sendRedirect("signUpNameError.jsp");
            }
        }

    }
}
