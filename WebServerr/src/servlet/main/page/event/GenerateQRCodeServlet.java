package servlet.main.page.event;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.servlet.annotation.MultipartConfig;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/generateQRCode")
@MultipartConfig
public class GenerateQRCodeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String eventName = request.getParameter("eventName");
        if (eventName == null || eventName.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Event name is required");
            return;
        }

        // Construct the URL for the event
        String eventUrl = request.getRequestURL().toString().replace(request.getServletPath(), "") + "/eventDetails.jsp?eventName=" + eventName;

        response.setContentType("image/png");
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(eventUrl, BarcodeFormat.QR_CODE, 200, 200);
            try (OutputStream out = response.getOutputStream()) {
                MatrixToImageWriter.writeToStream(bitMatrix, "PNG", out);
            }
        } catch (WriterException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "QR Code generation failed");
        }
    }
}
