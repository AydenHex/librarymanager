package com.tennoayden.app.gui.controllers;

import com.tennoayden.app.business.models.User;
import com.tennoayden.app.business.models.UserManager;
import com.tennoayden.app.business.services.UserService;
import com.tennoayden.app.gui.models.UserModel;
import com.tennoayden.app.gui.views.AdminFormView;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class UsersController {
    private static final Logger logger = Logger.getLogger(UserService.class);

    AdminFormView view;
    UserModel model;

    public UsersController() throws IOException {
        this.model = new UserModel();
        this.view = new AdminFormView(this.model);
        initController();
    }

    public void initController() {
        view.getAjouterbtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create user object
                User user = new User();
                user.setUsername(view.getUsername().getText());
                user.setPassword(view.getPassword().getText());
                user.setRole(view.getRole().getSelectedItem().toString());

                // Add user to UserList
                UserService.getInstance().userManager.getUser().add(user);

                // Save new user list
                try {
                    UserService.getInstance().saveUsers("resources\\users.xml");
                    logger.log(Level.INFO, "User successfuly created");
                } catch (Exception exception){
                    logger.log(Level.INFO, "Can't save users: " + exception);
                }

                // and reload GUI
                reloadTable();

            }
        });
    }

    public void reloadTable() {
        this.model.fireTableDataChanged();
        this.view.repaint();
    }

}
