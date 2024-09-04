package servlet.main.page.login;

import database.Users;
import database.UsersDAO;
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
        UsersDAO usersDAO = new UsersDAO();
        String fullName = request.getParameter("fullname");
        String password = request.getParameter("password");

        if (usersDAO.isValidUser(fullName, password)) {
            request.getSession().setAttribute("userName", fullName);
            response.sendRedirect("mainPage.jsp");
        } else {
            response.sendRedirect("loginPasswordError.jsp");
        }
    }
}

