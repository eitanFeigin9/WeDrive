package servlet.main.page.login;

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

import java.io.IOException;
import java.util.HashSet;

@WebServlet(name = "LoginFromLinkServlet", urlPatterns = {"/loginFromLink"})
public class LoginFromLinkServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UsersDAO usersDAO = new UsersDAO();
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        String eventId = request.getParameter("id");
        String eventOwner = request.getParameter("owner");
        if (!Users.checkUserExists(userName)) {
            response.sendRedirect("loginFromLink.jsp?error=Username does not exist!");
            return; // Stop further processing
        }
        // Get the owner details
        ServerClient owner = Users.getUserByUserName(eventOwner);
        if (owner == null) {
            // Early exit after redirect
            response.sendRedirect("loginFromLink.jsp?error=Owner is invalid!");
            return;
        }

        // Get event data owned by the owner
        EventData eventData = owner.getOwnedEventByName(eventId);
        if (eventData == null || (eventData.getFileName() != null && eventData.getGuestList().isEmpty())) {
            // Early exit after redirect
            response.sendRedirect("loginFromLink.jsp?error=You are not invited to the event:" + eventId + "!");
            return;
        }

        // Check if the user credentials are valid
        if (Users.isValidUser(userName, password)) {
            // Set session attribute for logged-in user
            request.getSession().setAttribute("userName", userName);
            response.sendRedirect("optionsFromLink.jsp?id=" + java.net.URLEncoder.encode(eventId, "UTF-8") + "&owner=" + java.net.URLEncoder.encode(eventOwner, "UTF-8"));
            return;
        }

        // Check if the password is incorrect
        response.sendRedirect("loginFromLink.jsp?error=Password isn't correct!");
        return;
    }

    private void redirectWithError(HttpServletResponse response, String eventId, String eventOwner, String errorPage) throws IOException {
        response.sendRedirect(errorPage + "?id=" + java.net.URLEncoder.encode(eventId, "UTF-8") + "&owner=" + java.net.URLEncoder.encode(eventOwner, "UTF-8"));
    }
}