package com.tennoayden.app.gui.views;

import com.tennoayden.app.business.models.StatusType;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;

/**
 * The type Form view.
 */
public class LoginView extends JDialog{
    private JLabel usernameLabel, passwordLabel;
    private JTextField username;
    private JPasswordField password;
    private JButton appliquer;
    private JPanel panAuth;
    private Font font;
    private GridBagConstraints c;
    private Dimension textDimension;

    public LoginView() throws IOException {
        // Set common var
        setTitle("Authentification");
        textDimension = new Dimension(100, 20);
        c = new GridBagConstraints();
        font = new Font("Verdana", Font.PLAIN, 14);

        // Set JTextfield and Jlabel...
        usernameLabel = new JLabel("Username :");
        username = new JTextField();
        passwordLabel = new JLabel("Mot de passe :");
        password = new JPasswordField();

        appliquer = new JButton("Se connecter");


        //set name component
        username.setName("username");
        password.setName("password");


        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(new BorderLayout());

        panAuth = new JPanel();
        panAuth.setPreferredSize(new Dimension(400, 200));
        panAuth.setBorder((BorderFactory.createTitledBorder("Authentification")));
        panAuth.setLayout(new GridBagLayout());

        placeComponents();

        add(panAuth, BorderLayout.NORTH);
        pack();
    }

    /**
     * Place components.
     */
    public void placeComponents() {
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(3,3,3,3);
        c.anchor = GridBagConstraints.LAST_LINE_END;
        usernameLabel.setFont(font);
        panAuth.add(usernameLabel, c);
        c.gridx = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        username.setPreferredSize(textDimension);
        panAuth.add(username, c);

        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.LAST_LINE_END;
        passwordLabel.setFont(font);
        panAuth.add(passwordLabel, c);
        c.gridx = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        password.setPreferredSize(textDimension);
        panAuth.add(password, c);


        c.gridy = 9;
        c.gridx = 2;
        panAuth.add(appliquer, c);
    }

    public String getUsername() {
        return this.username.getText();
    }

    public String getPassword() {
        return this.password.getText();
    }

    public JButton getAppliquer() {
        return this.appliquer;
    }



}
