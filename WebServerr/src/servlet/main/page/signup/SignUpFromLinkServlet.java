package servlet.main.page.signup;

import java.io.IOException;
import java.util.HashSet;

import database.Users;
import entity.ServerClient;
import event.EventData;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;

@WebServlet(name = "SignUpFromLinkServlet", urlPatterns = {"/signupFromLink"})
public class SignUpFromLinkServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form data
        Users userManagerManager = ServletUtils.getUserManager(getServletContext());
        String fullName = request.getParameter("fullname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String securityAnswer = request.getParameter("securityAnswer");
        String eventName = request.getParameter("id");
        String eventOwnerName = request.getParameter("owner");
        ServerClient owner = ServletUtils.getUserManager(getServletContext()).getUserByFullName(eventOwnerName);
        if (owner != null)
        {
            EventData eventData = owner.getOwnedEventByName(eventName);
            if (eventData != null && eventData.getFileName() != null && !(eventData.getGuestList().isEmpty()))
            {
                HashSet<String> guestList = eventData.getGuestList();
                if (guestList.contains(fullName)) {
                    if(!Users.addNewUser(fullName,email,phone,password,securityAnswer)){
                        response.sendRedirect("signUpNameError.jsp");
                    }
                    else {
                        //response.sendRedirect("loginFromLink.jsp");
                        response.sendRedirect("loginFromLink.jsp?id=" + java.net.URLEncoder.encode(eventName, "UTF-8") + "&owner=" + java.net.URLEncoder.encode(eventOwnerName, "UTF-8"));

                    }
                }
                else {
                    response.sendRedirect("notInGuestListError.jsp");
                }
            }
            else if (eventData != null)
            {
                if(!Users.addNewUser(fullName,email,phone,password,securityAnswer)){
                    response.sendRedirect("signUpNameError.jsp");
                }
                else {
                    response.sendRedirect("loginFromLink.jsp?id=" + java.net.URLEncoder.encode(eventName, "UTF-8") + "&owner=" + java.net.URLEncoder.encode(eventOwnerName, "UTF-8"));

                }
            }
            else {
                //different redirection
                response.sendRedirect("signUpNameError.jsp");
            }
        }

    }
}
