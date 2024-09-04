package servlet.main.page.signup;

import java.io.IOException;

import database.Users;
import database.UsersDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;

@WebServlet(name = "SignUpServlet", urlPatterns = {"/signup"})
public class SignUpServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form data
        //Users userManagerManager = ServletUtils.getUserManager(getServletContext());
        String fullName = request.getParameter("fullname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String securityAnswer = request.getParameter("securityAnswer");
        UsersDAO usersDAO = new UsersDAO();
        if (!usersDAO.addNewUser(fullName,email,phone,password,securityAnswer))
        {
            response.sendRedirect("signUpNameError.jsp");
        }
        response.sendRedirect("thankyou.html");

        response.getWriter().println("Full Name: " + fullName + "Email: " + email + "Phone: " + phone + "Password: " + password);


    }
}
