package servlet.main.page.event;

import java.io.File;
import java.util.*;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import database.Users;
import entity.ServerClient;
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

@WebServlet(name = "NewEventServlet", urlPatterns = {"/createEvent"})
@MultipartConfig
public class NewEventServlet extends HttpServlet {
    private static Map<String, String> nameMap = new HashMap<>();
    private static Map<String, String> ownerMap = new HashMap<>();

    public static Map<String, String> getNameMap() {
        return nameMap;
    }
    public static String getEventOwnerName(String eventName) {
        return ownerMap.get(eventName);
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

        //check if user uploaded a guest list
        Part guestListPart = request.getPart("guestList");
        HashSet<String> guestList = new HashSet<>();
        String guestListFileName = null;
        if (guestListPart != null && guestListPart.getSize() > 0) {
            guestList = ServletUtils.createGuestList(request, response);
            guestListFileName = ServletUtils.getFileName(guestListPart);
        }
        //add new event
        if (!eventOwner.addNewOwnedEvent(eventName, eventDate, eventKind, guestList, eventLocation, guestListFileName)) {
            response.sendRedirect("eventExistsError.jsp");
            return; // Exit if event creation fails
        }
        nameMap.put(eventName, eventName);
        ownerMap.put(eventName, eventOwner.getFullName());
        String url = request.getContextPath() + "/welcome.jsp?id=" + eventName;
        String link = "http://localhost:8080/weDrive/welcome.jsp?id=" + eventName;

        // Generate the QR code
        generateQRCodeImage(link, QR_CODE_IMAGE_PATH, 350, 350);
        request.setAttribute("qrUrl", link);

        // Redirect to a success page
        request.getRequestDispatcher("success.jsp").forward(request, response);

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