package utils;

import database.DriverRideDAO;
import database.Users;
import entity.ServerClient;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import ride.DriverRide;
import ride.HitchhikerRide;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class ServletUtils {
    private static final Object managerLock = new Object();
    private static final String USER_MANAGER_ATTRIBUTE = "userManager";
    private static final int EARTH_RADIUS = 6371; // Earth radius in kilometers

    public static Users getUserManager(ServletContext servletContext) {
        synchronized (managerLock) {
            if (servletContext.getAttribute(USER_MANAGER_ATTRIBUTE) == null) {
                servletContext.setAttribute(USER_MANAGER_ATTRIBUTE, new Users());
            }
        }
        return (Users) servletContext.getAttribute(USER_MANAGER_ATTRIBUTE);
    }

    public static HashSet<String> createGuestList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashSet<String> guestNames = new HashSet<>();
        Part filePart = request.getPart("guestList");
        InputStream fileContent = filePart.getInputStream();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(fileContent))) {
            String line;
            String[] headers = reader.readLine().split(",");
            int guestListIndex = -1;

            for (int i = 0; i < headers.length; i++) {
                String cleanedHeader = headers[i].replaceAll("[^a-zA-Z0-9]", "");
                if ("guestlist".equalsIgnoreCase(cleanedHeader.trim())) {
                    guestListIndex = i;
                    break;
                }
            }

            if (guestListIndex == -1) {
                response.getWriter().println("Error: 'guest-list' column not found.");
                return null;
            }

            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length > guestListIndex) {
                    String guestName = columns[guestListIndex].trim();
                    if (!guestName.isEmpty()) {
                        guestNames.add(guestName);
                    }
                }
            }
        }

        for (String name : guestNames) {
            System.out.println(name);
        }

        return guestNames;
    }

    public static String getFileName(Part part) {
        String contentDispositionHeader = part.getHeader("content-disposition");
        for (String header : contentDispositionHeader.split(";")) {
            if (header.trim().startsWith("filename")) {
                return header.substring(header.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c; // Distance in kilometers
    }

    /*public static void matchHitchhikersToDriver(ServerClient client, DriverRide newRide, Users userManager, String eventName, String userName, Double driverLatitude, Double driverLongitude, Double maxPickupDistance) {
        if (newRide.isTherePlace()) {
            HashMap<String, ServerClient> webUsers = userManager.getWebUsers();
            for (Map.Entry<String, ServerClient> user : webUsers.entrySet()) {
                if (user.getValue().checkHitchhikingEventExists(eventName) && !user.getKey().equals(userName) && newRide.isTherePlace()) {
                    HitchhikerRide hitchhikerRide = user.getValue().getHitchhikingEventByName(eventName);
                    if (hitchhikerRide.getFreeForPickup()) {
                        double hitchhikerLatitude = hitchhikerRide.getLatitude();
                        double hitchhikerLongitude = hitchhikerRide.getLongitude();
                        double distance = calculateDistance(driverLatitude, driverLongitude, hitchhikerLatitude, hitchhikerLongitude);
                        if (distance <= maxPickupDistance) {
                            if (hitchhikerRide.getFuelMoney() >= newRide.getFuelReturnsPerHitchhiker()) {
                                if (newRide.addNewHitchhiker(user.getValue().getFullName(), user.getValue().getPhoneNumber(), hitchhikerRide.getPickupCity(), hitchhikerLatitude, hitchhikerLongitude)) {
                                    newRide.addToTotalFuelReturns(hitchhikerRide.getFuelMoney());
                                    hitchhikerRide.setFreeForPickup(false);
                                    hitchhikerRide.setDriverName(client.getFullName());
                                    hitchhikerRide.setDriverPhone(client.getPhoneNumber());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

     */

   /* public static boolean matchDriverToHitchhiker(ServerClient client, HitchhikerRide newRide, Users userManager, String eventName, String userName, Double hitchhikerLatitude, Double hitchhikerLongitude) {
        HashMap<String, ServerClient> webUsers = userManager.getWebUsers();
        for (Map.Entry<String, ServerClient> user : webUsers.entrySet()) {
            if (user.getValue().checkDrivingEventExists(eventName) && !user.getKey().equals(userName)) {
                DriverRide driverRide = user.getValue().getDrivingEventByName(eventName);
                double driverLatitude = driverRide.getLatitude();
                double driverLongitude = driverRide.getLongitude();
                double distance = calculateDistance(driverLatitude, driverLongitude, hitchhikerLatitude, hitchhikerLongitude);
                if (driverRide.isTherePlace() && distance <= driverRide.getMaxPickupDistance()) {
                    if (newRide.getFuelMoney() >= driverRide.getFuelReturnsPerHitchhiker()) {
                        if (driverRide.addNewHitchhiker(client.getFullName(), client.getPhoneNumber(), newRide.getPickupCity(), hitchhikerLatitude, hitchhikerLongitude)) {
                            driverRide.addToTotalFuelReturns(newRide.getFuelMoney());
                            newRide.setFreeForPickup(false);
                            newRide.setDriverName(user.getValue().getFullName());
                            newRide.setDriverPhone(user.getValue().getPhoneNumber());
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    */
    public static String generateQRCodeImage(String text, String fileName, int width, int height, ServletContext servletContext) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.MARGIN, 1);

            if (text == null || fileName == null) {
                throw new IllegalArgumentException("Text or file name is null");
            }

            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);

            // Resolve file path relative to the web application directory and concatenate the QR_CODE_IMAGE_PATH
            String qrCodeDirPath = servletContext.getRealPath("/") + "web/QRCodes";
            String qrCodeFilePath = qrCodeDirPath + File.separator + fileName;

            File file = new File(qrCodeFilePath);

            // Ensure the directory exists
            file.getParentFile().mkdirs();

            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", file.toPath());

            return fileName; // Return the file name to use in the JSP

        } catch (WriterException | IOException e) {
            e.printStackTrace();
            return null; // Return null in case of an error
        }
    }
}
