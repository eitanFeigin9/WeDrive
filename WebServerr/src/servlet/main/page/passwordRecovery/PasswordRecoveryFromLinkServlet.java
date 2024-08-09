package servlet.main.page.passwordRecovery;

import java.io.IOException;

import database.Users;
import entity.ServerClient;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;

@WebServlet(name = "PasswordRecoveryServlet", urlPatterns = {"/recoverFromLink"})
public class PasswordRecoveryFromLinkServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fullname = request.getParameter("fullname");
        String securityAnswer = request.getParameter("securityAnswer");
        String eventName = request.getParameter("id");
        String eventOwnerName = request.getParameter("owner");
        ServerClient owner = ServletUtils.getUserManager(getServletContext()).getUserByFullName(eventOwnerName);

        ServerClient user = Users.getUserByFullName(fullname);
        if (user != null && user.getSecurityAnswer().equalsIgnoreCase(securityAnswer)) {
            request.setAttribute("password", user.getPassword());
            request.setAttribute("id", eventName);
            request.setAttribute("owner", eventOwnerName);
            request.getRequestDispatcher("PasswordRecoveryFromLink.jsp").forward(request, response);
        } else {
            response.sendRedirect("ForgotPasswordErrorFromLink.jsp?id=" + java.net.URLEncoder.encode(eventName, "UTF-8") + "&owner=" + java.net.URLEncoder.encode(eventOwnerName, "UTF-8"));
        }
    }
}
