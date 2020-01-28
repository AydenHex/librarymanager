package com.tennoayden.app.gui.views;



import com.tennoayden.app.business.models.UserManager;
import com.tennoayden.app.gui.models.UserModel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class AdminFormView extends JDialog{
    private JLabel usernameLabel, passwordLabel, comboxfield;
    private JComboBox role;
    private JTextField username;
    private JPasswordField password;
    private JButton ajouterbtn, supprimerbtn;
    private JPanel panAuth, panTable;
    private Font font;
    private GridBagConstraints c;
    private Dimension textDimension;
    private JTable  userTable;
    String [] roles ={"admin","user"};

    public AdminFormView() throws IOException {

        //about the main windows
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(new BorderLayout());

        // Set JTable
        userTable=new JTable(new UserModel());
        // Set common var
        textDimension = new Dimension(500, 500);
        c = new GridBagConstraints();
        font = new Font("Verdana", Font.PLAIN, 14);

        // Set JTextfield and Jlabel...
        usernameLabel = new JLabel("Username :");
        username = new JTextField();
        passwordLabel = new JLabel("Mot de passe :");
        password = new JPasswordField();

        // Set JComboBox
        role= new JComboBox(roles);
        role.setPreferredSize(new Dimension(100, 20));
        comboxfield=new JLabel("Rôle :");


        ajouterbtn = new JButton("ajouter");
        supprimerbtn = new JButton("supprimer");

        //set name component
        username.setName("username");
        password.setName("password");





        panAuth = new JPanel();
        panAuth.setPreferredSize(new Dimension(500, 200));
        panAuth.setBorder((BorderFactory.createTitledBorder("Authentification")));
        panAuth.setLayout(new GridBagLayout());

        placeComponents();

        panTable=new JPanel();
        panTable.setPreferredSize(new Dimension(500, 280));
        panTable.setBorder((BorderFactory.createTitledBorder("Liste D'utilisateurs")));
        panTable.add(new JScrollPane(userTable), BorderLayout.CENTER);

        add(panTable, BorderLayout.NORTH);
        add(panAuth, BorderLayout.SOUTH);

        pack();
    }

    /**
     * Place components.
     */
    public void placeComponents() {
        textDimension = new Dimension(100, 20);

        //Pos Chmp username
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(3,3,5,3);
        c.anchor = GridBagConstraints.LAST_LINE_END;
        usernameLabel.setFont(font);
        panAuth.add(usernameLabel, c);
        c.gridx = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        username.setPreferredSize(textDimension);

        panAuth.add(username, c);

        // Pos Chmp passw
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.LAST_LINE_END;
        passwordLabel.setFont(font);
        panAuth.add(passwordLabel, c);
        c.gridx = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        password.setPreferredSize(textDimension);
        panAuth.add(password, c);

        // Pos JCombo role
        c.gridx = 0;
        c.gridy = 2;
        c.anchor = GridBagConstraints.LAST_LINE_END;
        comboxfield.setFont(font);
        panAuth.add(comboxfield, c);
        c.gridx = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        role.setPreferredSize(textDimension);
        panAuth.add(role, c);

        // Pos Btn ajouter
        c.gridy = 9;
        c.gridx = 2;
        panAuth.add(ajouterbtn, c);

        c.gridy = 9;
        c.gridx = 1;
        panAuth.add(supprimerbtn, c);
    }

    public String getUsername() {
        return this.username.getText();
    }

    public String getPassword() {
        return this.password.getText();
    }

    public JButton getAjouterbtn() {
        return this.ajouterbtn;
    }




}
