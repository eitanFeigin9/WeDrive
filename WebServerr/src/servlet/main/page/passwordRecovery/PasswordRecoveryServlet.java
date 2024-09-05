package servlet.main.page.passwordRecovery;

import java.io.IOException;

import database.Users;
import database.UsersDAO;
import entity.ServerClient;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "PasswordRecoveryServlet", urlPatterns = {"/recover"})
public class PasswordRecoveryServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String securityAnswer = request.getParameter("securityAnswer");

        ServerClient user = UsersDAO.getUserByUserName(userName);
        if (user != null && user.getSecurityAnswer().equalsIgnoreCase(securityAnswer)) {
            request.setAttribute("password", user.getPassword());
            request.getRequestDispatcher("passwordRecovery.jsp").forward(request, response);
        } else {
            response.sendRedirect("forgotPasswordError.jsp");
        }
    }
}
