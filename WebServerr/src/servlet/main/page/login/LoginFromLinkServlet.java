package servlet.main.page.login;

import database.Users;
import entity.ServerClient;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;

import java.io.IOException;

@WebServlet(name = "LoginFromLinkServlet", urlPatterns = {"/loginFromLink"})
public class LoginFromLinkServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Users userManager = ServletUtils.getUserManager(getServletContext());
        String fullName = request.getParameter("fullname");
        String password = request.getParameter("password");
        if (userManager.getWebUsers().containsKey(fullName)) {
            ServerClient client = userManager.getWebUsers().get(fullName);
            if (client.getPassword().equals(password)) {
                request.getSession().setAttribute("userName", fullName);
                response.sendRedirect("optionsFromLink.jsp");
            }
            else {
                response.sendRedirect("loginPasswordError.jsp");
            }
        }
    }
}
