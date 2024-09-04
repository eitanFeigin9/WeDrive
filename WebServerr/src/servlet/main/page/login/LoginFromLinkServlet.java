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
        //Users userManager = ServletUtils.getUserManager(getServletContext());
        String fullName = request.getParameter("fullname");
        String password = request.getParameter("password");
        String eventId = request.getParameter("id");
        String eventOwner = request.getParameter("owner");

        ServerClient owner = usersDAO.getUserByFullName(eventOwner);
        //ServerClient owner = userManager.getUserByFullName(eventOwner);
        if (owner == null) {
            redirectWithError(response, eventId, eventOwner, "LoginFromLinkPasswordError.jsp");
            return;
        }

        EventData eventData = owner.getOwnedEventByName(eventId);
        if (eventData == null || (eventData.getFileName() != null && eventData.getGuestList().isEmpty())) {
            redirectWithError(response, eventId, eventOwner, "LoginFromLinkPasswordError.jsp");
            return;
        }

        if (!usersDAO.isValidUser(fullName, password)) {
            redirectWithError(response, eventId, eventOwner, "LoginFromLinkPasswordError.jsp");
            return;
        }


        if (eventData.getGuestList().contains(fullName) || eventData.getFileName() == null) {
            request.getSession().setAttribute("userName", fullName);
            response.sendRedirect("optionsFromLink.jsp?id=" + java.net.URLEncoder.encode(eventId, "UTF-8") + "&owner=" + java.net.URLEncoder.encode(eventOwner, "UTF-8"));
        } else {
            response.sendRedirect("NotInGuestListLoginFromLink.jsp?id=" + java.net.URLEncoder.encode(eventId, "UTF-8") + "&owner=" + java.net.URLEncoder.encode(eventOwner, "UTF-8"));
        }
    }

    private void redirectWithError(HttpServletResponse response, String eventId, String eventOwner, String errorPage) throws IOException {
        response.sendRedirect(errorPage + "?id=" + java.net.URLEncoder.encode(eventId, "UTF-8") + "&owner=" + java.net.URLEncoder.encode(eventOwner, "UTF-8"));
    }




}
