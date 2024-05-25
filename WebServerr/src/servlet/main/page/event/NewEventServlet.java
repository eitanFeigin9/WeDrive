package servlet.main.page.event;

import java.io.File;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;


import javax.jms.JMSException;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import database.Users;
import entity.ServerClient;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import utils.ServletUtils;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.jms.Connection;

import java.io.IOException;


@WebServlet(name = "NewEventServlet", urlPatterns = {"/createEvent"})
@MultipartConfig
public class NewEventServlet extends HttpServlet {
    private static Map<String, String> nameMap = new HashMap<>();

    public static Map<String, String> getNameMap() {
        return nameMap;
    }
    private static final String QR_CODE_IMAGE_PATH = "QRCodes"; // Set the path to save the QR code image
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve form data
        ServerClient eventOwner = getEventOwnerFromSessionOrDatabase(request);
        String eventName = request.getParameter("eventName");
        String eventDate = request.getParameter("eventDate");
        String eventKind = request.getParameter("eventKind");
        String eventLocation = request.getParameter("eventLocation");
        HashSet<String> guestList = ServletUtils.createGuestList(request, response);
        // stuff that eitan found
       // String uuid = UUID.randomUUID().toString();
        nameMap.put(eventName, eventName);
        String url = request.getContextPath() + "/welcome.jsp?id=" + eventName;
        String link = "http://localhost:8080/weDrive/welcome.jsp?id=" + eventName;

        // Generate the QR code
        generateQRCodeImage(link, QR_CODE_IMAGE_PATH, 350, 350);
        request.setAttribute("qrUrl", link);

        // Redirect to a success page
        request.getRequestDispatcher("success.jsp").forward(request, response);
        //response.sendRedirect(url);
        //new stuff
        /*
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            // Initialize connection to the database
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/events", "username", "password");

            // Prepare SQL statement
            String sql = "INSERT INTO events (event_name, event_date) VALUES (?, ?)";
            pstmt = ((java.sql.Connection) conn).prepareStatement(sql);
            pstmt.setString(1, eventName);
            pstmt.setString(2, eventDate);

            // Execute SQL statement
            pstmt.executeUpdate();

            // Redirect to a success page
            response.sendRedirect("eventCreated.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            // Redirect to an error page
            response.sendRedirect("error.jsp");
        } finally {
            // Close resources
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (JMSException e) {
                throw new RuntimeException(e);
            }
        }
    }
/*
        // Event creation logic (replace with your actual implementation)
        if (!eventOwner.addNewEvent(eventName, eventDate, eventKind, guestList, eventLocation, ServletUtils.getFileName(request.getPart("guestList")))) {
            response.sendRedirect("eventExistsError.jsp");
            return; // Exit if event creation fails
        }

        String eventURL = "http://localhost:8080/weDrive/events/" + eventName; // Assuming a basic event URL structure
        String qrCodeText = eventURL;
        BitMatrix qrMatrix = null;
        try {
            qrMatrix = new QRCodeWriter().encode(qrCodeText, com.google.zxing.BarcodeFormat.QR_CODE, 350, 350);
        } catch (WriterException e) {
            e.printStackTrace();
            // Handle QR code generation exception (redirect to error page or log the error)
            return;
        }

        // Convert QR code matrix to image and encode as Base64 string
        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(qrMatrix, new MatrixToImageConfig(MatrixToImageConfig.BLACK, MatrixToImageConfig.WHITE));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(qrMatrix, "png", baos);
        byte[] imageBytes = baos.toByteArray();
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

        // Set attributes for QR code URL and event URL before redirecting
        request.setAttribute("qrCodeURL", "data:image/png;base64," + base64Image);
        request.setAttribute("eventURL", eventURL);

        // Redirect to qrCodeLink.jsp
        response.sendRedirect("eventQRCode.jsp");
    }
*/
    }
        private ServerClient getEventOwnerFromSessionOrDatabase (HttpServletRequest request){
            String userName = (String) request.getSession().getAttribute("userName");
            return Users.getUserByFullName(userName);
        }

    private void generateQRCodeImage(String text, String filePath, int width, int height) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.MARGIN, 1);
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);

            // Write the QR code to a PNG file
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", new File(filePath).toPath());
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
    }

}