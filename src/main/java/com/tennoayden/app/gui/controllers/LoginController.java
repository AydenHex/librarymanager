package com.tennoayden.app.gui.controllers;

import com.tennoayden.app.business.models.UserManager;
import com.tennoayden.app.business.services.UserService;
import com.tennoayden.app.gui.views.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginController {

    LoginView view;
    UserManager model;


    public LoginController() throws IOException {
        this.view = new LoginView();
        initController();
    }

    public void initController() {
        view.getAppliquer().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UserService as = UserService.getInstance();
                boolean res = as.authentificate(view.getUsername(), view.getPassword());
                if (res) {
                    HomeController homeController = new HomeController("Ma bibliotheque");
                    view.dispose();
                } else {
                    JOptionPane.showMessageDialog(view,
                            "Vos identifiants ne sont pas corrects");
                }
            }
        });
    }
}
