package listener;

import initialization.DataInitializer;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppInitializerListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Initialize data
        DataInitializer.initialize();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Clean-up resources if needed
    }
}
