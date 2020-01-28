package com.tennoayden.app.business.services;

import com.tennoayden.app.business.models.ObjectFactory;
import com.tennoayden.app.business.models.User;
import com.tennoayden.app.business.models.UserManager;
import com.tennoayden.app.gui.controllers.FormController;
import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class AuthService {

    private static final Logger logger = Logger.getLogger(AuthService.class);

    private static AuthService single_instance = null;

    public UserManager userManager;
    public User currentUser;
    private ObjectFactory of;

    private AuthService() {
        of = new ObjectFactory();
        userManager = of.createUserManager();
    }

    public static AuthService getInstance() {
        if (single_instance == null)
            single_instance = new AuthService();

        return single_instance;
    }

    public static void loadUsers(String path) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(UserManager.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        AuthService.getInstance().userManager.getUser().clear();

        //We had written this file in marshalling example
        UserManager emps = (UserManager) jaxbUnmarshaller.unmarshal(new File(path));

        for(User emp : emps.getUser()) {
            AuthService.getInstance().userManager.getUser().add(emp);
        }
    }

    public static boolean authentificate(String username, String password) {
        for (User user : AuthService.getInstance().userManager.getUser()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                AuthService.getInstance().currentUser = user;
                logger.log(Level.INFO, String.format("%s connected", AuthService.getInstance().currentUser.getUsername()));
                return true;
            }
        }
        return false;
    }

    public UserManager getUserManager() {
        return userManager;
    }
}
