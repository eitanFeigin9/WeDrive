package utils;


import database.Users;
import jakarta.servlet.ServletContext;

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
}
