package com.tennoayden.app.business.services;

import com.tennoayden.app.business.models.Bibliotheque;
import com.tennoayden.app.business.models.ObjectFactory;
import com.tennoayden.app.business.models.User;
import com.tennoayden.app.business.models.UserManager;
import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class UserService {

    private static final Logger logger = Logger.getLogger(UserService.class);

    private static UserService single_instance = null;

    public UserManager userManager;
    public User currentUser;
    private ObjectFactory of;

    private UserService() {
        of = new ObjectFactory();
        userManager = of.createUserManager();
    }

    public static UserService getInstance() {
        if (single_instance == null)
            single_instance = new UserService();

        return single_instance;
    }

    public static void loadUsers(String path) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(UserManager.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        UserService.getInstance().userManager.getUser().clear();

        //We had written this file in marshalling example
        UserManager emps = (UserManager) jaxbUnmarshaller.unmarshal(new File(path));

        for(User emp : emps.getUser()) {
            UserService.getInstance().userManager.getUser().add(emp);
        }
    }

    public void saveUsers(String path) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(UserManager.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        //Marshal the employees list in file
        jaxbMarshaller.marshal(this.userManager, new File(path));

        logger.log(Level.INFO, String.format("The user list has been saved !"));
    }

    public static boolean authentificate(String username, String password) {
        for (User user : UserService.getInstance().userManager.getUser()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                UserService.getInstance().currentUser = user;
                logger.log(Level.INFO, String.format("%s connected", UserService.getInstance().currentUser.getUsername()));
                return true;
            }
        }
        return false;
    }

    public UserManager getUserManager() {
        return userManager;
    }
}
