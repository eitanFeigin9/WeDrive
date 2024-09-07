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

@WebServlet(name = "PasswordRecoveryFromLinkServlet", urlPatterns = {"/recoverFromLink"})
public class PasswordRecoveryFromLinkServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String securityAnswer = request.getParameter("securityAnswer");
        String eventName = request.getParameter("id");
        String eventOwnerName = request.getParameter("owner");
        ServerClient owner = Users.getUserByUserName(eventOwnerName);

        ServerClient user = Users.getUserByUserName(userName);
        if (user != null && user.getSecurityAnswer().equalsIgnoreCase(securityAnswer)) {
            request.setAttribute("password", user.getPassword());
            request.setAttribute("id", eventName);
            request.setAttribute("owner", eventOwnerName);
          //  response.sendRedirect("/PasswordRecoveryFromLink.jsp?id="+eventName+"&owner="+eventOwnerName);
            request.getRequestDispatcher("/PasswordRecoveryFromLink.jsp?id="+eventName+"&owner="+eventOwnerName).forward(request, response);
        } else {
            response.sendRedirect("ForgotPasswordErrorFromLink.jsp?id=" + java.net.URLEncoder.encode(eventName, "UTF-8") + "&owner=" + java.net.URLEncoder.encode(eventOwnerName, "UTF-8"));
        }
    }
}
