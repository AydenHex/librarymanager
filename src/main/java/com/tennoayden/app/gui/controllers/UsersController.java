package com.tennoayden.app.gui.controllers;

import com.tennoayden.app.business.models.Bibliotheque;
import com.tennoayden.app.business.models.User;
import com.tennoayden.app.business.models.UserManager;
import com.tennoayden.app.business.services.BibliothequeService;
import com.tennoayden.app.business.services.ConfigService;
import com.tennoayden.app.business.services.UserService;
import com.tennoayden.app.gui.models.UserModel;
import com.tennoayden.app.gui.views.AdminFormView;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.xml.bind.JAXBException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.regex.*;

public class UsersController {
    private static final Logger logger = Logger.getLogger(UserService.class);
    private static int selectedUser = -1;

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
                // Verification for the mail (REGEX + Unique)
                Pattern pattern = Pattern.compile("^(.+)@(.+)$");
                String email = view.getEmail().getText();

                if ((pattern.matcher(email).matches() && !UserService.getInstance().verifyEmail(email)) || selectedUser != -1) {
                    // Create user object
                    User user = new User();
                    user.setUsername(view.getUsername().getText());
                    user.setPassword(view.getPassword().getText());
                    user.setRole(view.getRole().getSelectedItem().toString());
                    user.setEmail(email);

                    if (selectedUser == -1) {
                        // Add user to UserList
                        UserService.getInstance().userManager.getUser().add(user);
                    } else {
                        UserService.getInstance().userManager.getUser().set(selectedUser, user);
                        selectedUser = -1;
                    }


                    // Save new user list
                    try {
                        UserService.getInstance().saveUsers("resources\\users.xml");
                        logger.log(Level.INFO, "User successfuly created/updated");
                    } catch (Exception exception){
                        logger.log(Level.INFO, "Can't save users: " + exception);
                    }

                    // and reload GUI
                    reloadTable();
                    cleanFields();
                } else {
                    JOptionPane.showMessageDialog(null, "Cet email existe déjà dans la base de donnée des utilisateur !");
                }
            }
        });

        view.getUserTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                userSelected(e);
            }
            @Override
            public void mousePressed(MouseEvent e) { }
            @Override
            public void mouseReleased(MouseEvent e) { }
            @Override
            public void mouseEntered(MouseEvent e) { }
            @Override
            public void mouseExited(MouseEvent e) { }
        });
        view.getDeleteItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteUser();
            }
        });
    }

    public void reloadTable() {
        this.model.fireTableDataChanged();
        this.view.repaint();
    }

    public void userSelected(MouseEvent mouseEvent) {
        JTable table = (JTable) mouseEvent.getSource();
        Point point = mouseEvent.getPoint();
        int row = table.rowAtPoint(point);
        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
            // change button text
            view.getAjouterbtn().setText("Mettre à jour");

            User user = UserService.getInstance().getUserManager().getUser().get(table.getSelectedRow());

            // set form and updateMode
            selectedUser = table.getSelectedRow();
            view.getUsername().setText(user.getUsername());
            view.getPassword().setText(user.getPassword());
            view.getRole().setSelectedItem(user.getRole());
            view.getEmail().setText(user.getEmail());
        }
    }

    public void deleteUser() {
        int n = JOptionPane.showConfirmDialog(
                null,
                "Voulez-vous vraiment supprimer la ligne séléctionnée ?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);
        if (n == 0) {
            String username = UserService.getInstance().userManager.getUser().get(view.getUserTable().getSelectedRow()).getUsername();
            UserService.getInstance().userManager.getUser().
                    remove(view.getUserTable().getSelectedRow());
            reloadTable();
            try {
                UserService.getInstance().saveUsers("resources\\users.xml");
                logger.log(Level.INFO, String.format("The user %s has deleted the user : %s", UserService.getInstance().currentUser.getUsername(), username));
            } catch (JAXBException jaxbe) {
                logger.log(Level.ERROR, String.format("Can't save user list : %s", jaxbe.toString()));
            }

        }
    }

    public void cleanFields() {
        view.getUsername().setText("");
        view.getPassword().setText("");
        view.getEmail().setText("");
        view.getRole().setSelectedIndex(0);
        view.getAjouterbtn().setText("Ajouter");
    }

}
