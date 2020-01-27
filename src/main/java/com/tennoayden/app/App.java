package com.tennoayden.app;

import com.tennoayden.app.business.services.AuthService;
import com.tennoayden.app.gui.controllers.HomeController;
import com.tennoayden.app.gui.views.HomeView;
import com.tennoayden.app.gui.controllers.LoginController;

import javax.xml.bind.JAXBException;
import java.io.IOException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;
/**
 * Hello world!
 */
public class App
{
    private static final Logger logger = Logger.getLogger(App.class);
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main( String[] args )
    {
        // Test
        //HomeController home = new HomeController("Gestion de ma Bibiliotheque");
        AuthService as = AuthService.getInstance();
        //BasicConfigurator.configure();

        logger.log(Level.TRACE, "The application is launching...");
        try {
            as.loadUsers("C:\\Users\\royer\\Documents\\test.xml");
            logger.log(Level.TRACE, "Application started");
            LoginController login = new LoginController();
        }
        catch (JAXBException j) {
            logger.error("JAXB Exception raised while application starting: " + j);
        }
        catch (IOException ioe) {
            logger.error("IOException raised while application starting: " + ioe);
        }



    }
}
