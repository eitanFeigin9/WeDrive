package servlet.main.page.signup;
import database.Users;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Extract form data
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String password = request.getParameter("password");
        if (!phoneNumber.matches("\\d+")) {
            // Phone number contains non-digit characters
            // Handle the validation error, for example, redirect back to the sign-up page with an error message
            response.sendRedirect("signup.html?error=invalidPhoneNumber");
            return;
            //הרציונל עמוד של ההרשמה עם הודעה : מספר טלפון לא נכון - אנא נסה שנית
        }
        if(Users.addNewUser(fullName,email,phoneNumber,password))
        {
            response.setContentType("text/html");
            response.getWriter().println("<h1>Sign Up Successful</h1>");
            response.getWriter().println("<p>Thank you for signing up, " + fullName + "!</p>");
            //לעשות כמו שנלמד בכיתה
        }
        // Process the form data (e.g., save it to a database)
        // Your code to process the form data goes here

        // Optionally, provide feedback to the user

    }
}