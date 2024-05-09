package servlet.main.page.signup;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignupServlet extends HttpServlet {
    /* protected void doGet(HttpServletRequest request, HttpServletResponse response)
             throws ServletException, IOException {
         // Forward the request to the signup.jsp page
         request.getRequestDispatcher("signup.html").forward(request, response);
     }
 }

     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirect to signup.html
        response.sendRedirect("signup.html");
    }
}