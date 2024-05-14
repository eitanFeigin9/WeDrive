package servlet.main.page.login;

import database.Users;
import entity.ServerClient;
import jakarta.servlet.annotation.WebServlet;
import utils.ServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward the request to the login.jsp page
        Users userManager = ServletUtils.getUserManager(getServletContext());
        String fullName = request.getParameter("fullname");
        String password = request.getParameter("password");
        if (userManager.getWebUsers().containsKey(fullName)) {
            ServerClient client = userManager.getWebUsers().get(fullName);
            if (client.getPassword().equals(password)) {
                response.sendRedirect("mainPage.jsp");
            }
            else {
                response.sendRedirect("loginPasswordError.jsp");
            }
        }
    }
}

