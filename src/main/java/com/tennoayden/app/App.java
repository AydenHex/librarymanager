package com.tennoayden.app;

import com.tennoayden.app.business.services.UserService;
import com.tennoayden.app.business.services.DatabaseService;
import com.tennoayden.app.gui.controllers.LoginController;

import javax.xml.bind.JAXBException;
import java.io.IOException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

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
        UserService as = UserService.getInstance();
        //BasicConfigurator.configure();

        logger.log(Level.TRACE, "The application is launching...");
        DatabaseService dbService = DatabaseService.getInstance();
        try {
            as.loadUsers("resources\\users.xml");
            logger.log(Level.TRACE, "Application started");
            LoginController login = new LoginController();
        }
        catch (JAXBException j) {
            logger.error("JAXB Exception raised while application stasrting: " + j);
        }
        catch (IOException ioe) {
            logger.error("IOException raised while application starting: " + ioe);
        }



    }
}
