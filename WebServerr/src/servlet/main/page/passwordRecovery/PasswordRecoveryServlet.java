package servlet.main.page.passwordRecovery;

import java.io.IOException;

import database.Users;
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
        String fullname = request.getParameter("fullname");
        String securityAnswer = request.getParameter("securityAnswer");

        ServerClient user = Users.getUserByFullName(fullname);
        if (user != null && user.getSecurityAnswer().equalsIgnoreCase(securityAnswer)) {
            response.getWriter().println("Your password is: " + user.getPassword());
        } else {
            response.sendRedirect("login.jsp?error=incorrectAnswer");
        }
    }
}
