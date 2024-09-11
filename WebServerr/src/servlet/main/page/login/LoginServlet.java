package servlet.main.page.login;

import database.Users;
import database.UsersDAO;
import jakarta.servlet.annotation.WebServlet;
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
        UsersDAO usersDAO = new UsersDAO();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        // Check if username exists
        if (!Users.checkUserExists(username)) {
            response.sendRedirect("login.jsp?error=Username does not exist!");
            return; // Stop further processing
        }
        if (Users.isValidUser(username, password)) {
            request.getSession().setAttribute("userName", username);
            response.sendRedirect("mainPage.jsp");
            return; // Stop further processing
        } else {
            response.sendRedirect("login.jsp?error=Password isn't correct!");
        }
    }
}


