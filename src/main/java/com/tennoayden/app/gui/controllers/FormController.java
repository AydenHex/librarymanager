package com.tennoayden.app.gui.controllers;

import com.tennoayden.app.App;
import com.tennoayden.app.business.models.Bibliotheque;
import com.tennoayden.app.business.models.Bibliotheque.Livre;
import com.tennoayden.app.business.models.ObjectFactory;
import com.tennoayden.app.business.models.StatusType;
import com.tennoayden.app.business.services.AuthService;
import com.tennoayden.app.business.services.BibliothequeService;
import com.tennoayden.app.business.services.ConfigService;
import com.tennoayden.app.business.services.DatabaseService;
import com.tennoayden.app.gui.views.FormView;
import com.tennoayden.app.gui.views.HomeView;
import org.apache.log4j.Logger;
import org.apache.log4j.Level;
//import sun.misc.FormattedFloatingDecimal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.Normalizer;
import java.sql.*;

/**
 * The type Form controller.
 */
public class FormController {

    private static final Logger logger = Logger.getLogger(FormController.class);

    /**
     * The View.
     */
    FormView view;
    /**
     * The Model.
     */
    Bibliotheque.Livre model;
    /**
     * The Of.
     */
    ObjectFactory of;
    /**
     * The Hc.
     */
    HomeController hc;

    /**
     * Instantiates a new Form controller.
     *
     * @param titre          the titre
     * @param homeController the home controller
     * @throws IOException the io exception
     */
    public FormController(String titre, HomeController homeController) throws IOException {
        of = new ObjectFactory();
        view = new FormView(homeController.view, titre);
        model = of.createBibliothequeLivre();
        hc = homeController;
        initController();
    }

    /**
     * Instantiates a new Form controller.
     *
     * @param titre          the titre
     * @param homeController the home controller
     * @param livre          the livre
     * @throws IOException the io exception
     */
    public FormController(String titre, HomeController homeController, Bibliotheque.Livre livre) throws IOException {
        of = new ObjectFactory();
        view = new FormView(homeController.view, titre);
        model = livre;
        hc = homeController;
        initController();
        initView();
    }

    /**
     * Init view.
     */
    public void initView() {
        this.view.setTitre(model.getTitre());
        this.view.setAuteurPrenom(model.getAuteur().getPrenom());
        this.view.setAuteurNom(model.getAuteur().getNom());
        this.view.setResume(model.getPresentation());
        this.view.setParution(model.getParution());
        this.view.setColonne(model.getColonne());
        this.view.setRangee(model.getRangee());
        this.view.setStatus(model.getStatus());
        this.view.setAQui(model.getAqui());
        this.view.setURL(model.getUrl());

        ImageIcon imageIcon = new ImageIcon(model.getUrl()); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        this.view.getImageIcon().setImage(newimg);

    }

    /**
     * Init controller.
     */
    public void initController() {
        view.getButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               creerLivre();
            }
        });
        view.getStatusField().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toggleAQui();
            }
        });
        if (!AuthService.getInstance().currentUser.getRole().equals("admin")) {
            view.getButton().setEnabled(false);
        }
    }

    /**
     * Toggle a qui.
     */
    public void toggleAQui() {
        if (view.getStatusField().getSelectedItem() == StatusType.PRETE) {
            view.getAquiField().setEnabled(true);
        } else {
            view.getAquiField().setEnabled(false);
        }
    }

    /**
     * Validate form boolean.
     *
     * @return the boolean
     */
    public Boolean validateForm() {
        if (view.getTitre().isEmpty() ||
            view.getAuteurNom().isEmpty() ||
            view.getAuteurPrenom().isEmpty()) {

            return false;
        }
        if (0 > view.getColonne() || view.getColonne() > 6) {
            return false;
        }
        if (0 > view.getRangee() || view.getRangee() > 8) {
            return false;
        }
        if (!(-800 > view.getParution() || view.getParution() <= 2030)) {
            return false;
        }
        return true;
    }

    /**
     * Creer livre.
     */
    public void creerLivre() {
        if (this.validateForm()) {
            Bibliotheque.Livre.Auteur auteur= of.createBibliothequeLivreAuteur();
            auteur.setNom(view.getAuteurNom());
            auteur.setPrenom(view.getAuteurPrenom());

            model.setTitre(view.getTitre());
            model.setAuteur(auteur);
            model.setPresentation(view.getResume());
            model.setColonne(view.getColonne());
            model.setRangee(view.getRangee());
            model.setStatus(view.getStatus());
            model.setAqui(view.getAQui());
            model.setParution(view.getParution());
            model.setUrl(view.getURL());

            if (view.getTitle() == "Ajouter un livre") {
                if (ConfigService.getInstance().database == false) {
                    BibliothequeService.getInstance().bibliotheque.getLivre().add(model);
                    logger.log(Level.INFO, String.format("The user %s has added the book : %s", AuthService.getInstance().currentUser.getUsername(), model.getTitre()));
                } else {
                    saveBookDB();
                    BibliothequeService.getInstance().loadLivreDB();
                    hc.reloadTable();
                }
            } else {
                logger.log(Level.INFO, String.format("The user %s has updated the book :%s", AuthService.getInstance().currentUser.getUsername(), model.getTitre()));
            }
            view.dispose();
            hc.reloadTable();
            if (ConfigService.getInstance().database != true) {
                ConfigService.getInstance().modification = true;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Remplissez-tous les champs du formulaire, s'il vous plait.", "Erreur Formulaire", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveBookDB() {

        String sql = "INSERT INTO books(title, author_firstname, author_lastname," +
                "release, column, row, url, aqui, status, resume) VALUES(?,?,?,?,?,?,?,?,?,?)";

        try (Connection conn = DatabaseService.getInstance().connect()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, model.getTitre());
            pstmt.setString(2, model.getAuteur().getPrenom());
            pstmt.setString(3, model.getAuteur().getNom());
            pstmt.setInt(4, model.getParution());
            pstmt.setShort(5, model.getColonne());
            pstmt.setShort(6, model.getRangee());
            pstmt.setString(7, model.getUrl());
            pstmt.setString(8, model.getAqui());
            pstmt.setString(9, model.getStatus().toString());
            pstmt.setString(10, model.getPresentation());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }





}
