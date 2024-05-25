package utils;


import database.Users;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;

public class ServletUtils {
    private static final Object managerLock = new Object();
    private static final String USER_MANAGER_ATTRIBUTE = "userManager";


    public static Users getUserManager(ServletContext servletContext) {

        synchronized (managerLock) {
            if (servletContext.getAttribute(USER_MANAGER_ATTRIBUTE) == null) {
             //   Users users=new Users();
             // Boolean X=  users.isFirstUser();
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
                //להעביר לעמוד תקלה
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
}
