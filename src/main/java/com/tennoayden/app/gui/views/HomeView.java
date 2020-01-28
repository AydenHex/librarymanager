package com.tennoayden.app.gui.views;

import com.tennoayden.app.gui.models.TableModel;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;

/**
 * The type Home view.
 */
public class HomeView extends JFrame{

    //MenuBar
    private JMenuBar menubar;
    private JMenu fichier, edition, apropos, database;
    private JMenuItem ouvrir, exporter, fermer, ajouterLivre, sauvegarder, sauvegarderSous, informations, switchDatabase;
    private JPopupMenu tableMenu;
    private JMenuItem deleteItem;

    //Table
    private JPanel panTable;
    private JTable jTable;
    private JScrollPane jScrollPane;

    /**
     * Instantiates a new Home view.
     *
     * @param titre      the titre
     * @param modelTable the model table
     */
    public HomeView(String titre, TableModel modelTable){
        // Set the frame

        setTitle(titre);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setPreferredSize(new Dimension(1000, 500));

        //Set MenuBar UI
        menubar = new JMenuBar();

        fichier = new JMenu("Fichier");
        edition = new JMenu("Edition");
        apropos = new JMenu("A propos");
        database = new JMenu("Base de donnée");

        ouvrir = new JMenuItem("Ouvrir");
        exporter = new JMenuItem("Exporter");
        fermer = new JMenuItem("Fermer");

        ajouterLivre = new JMenuItem("Ajouter un livre");
        sauvegarder = new JMenuItem("Sauvegarder...");
        sauvegarderSous = new JMenuItem("Sauvegarder sous...");

        switchDatabase = new JMenuItem("Se connecter à la BDD");

        informations = new JMenuItem("Informations");

        fichier.add(ouvrir);
        fichier.add(exporter);
        fichier.add(fermer);
        edition.add(ajouterLivre);
        edition.add(sauvegarder);
        edition.add(sauvegarderSous);
        apropos.add(informations);
        database.add(switchDatabase);

        menubar.add(fichier);
        menubar.add(edition);
        menubar.add(database);
        menubar.add(apropos);

        //

        setJMenuBar(menubar);

        // Popup menu
        tableMenu = new JPopupMenu();
        deleteItem = new JMenuItem("Supprimer");
        tableMenu.add(deleteItem);

        //set Table
        panTable = new JPanel();
        jTable = new JTable(modelTable);
        jScrollPane = new JScrollPane(jTable);
        panTable.setBorder((BorderFactory.createTitledBorder("Tableau des livres")));
        jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panTable.add(jScrollPane, BorderLayout.CENTER);
        panTable.setVisible(true);
        jTable.setComponentPopupMenu(tableMenu);
        jTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        jScrollPane.setPreferredSize(new Dimension(1000, 500));

        //change width table
        TableColumnModel tcm = jTable.getColumnModel();
        tcm.getColumn(0).setPreferredWidth(100);
        tcm.getColumn(1).setPreferredWidth(100);
        tcm.getColumn(2).setPreferredWidth(20);
        tcm.getColumn(3).setPreferredWidth(300);
        tcm.getColumn(4).setPreferredWidth(100);
        tcm.getColumn(5).setPreferredWidth(5);
        tcm.getColumn(6).setPreferredWidth(5);


        add(panTable);


        pack();

    }
    public void repaint() {
        jTable.clearSelection();
        jTable.repaint();
    }

    /**
     * Gets fichier ouvrir menu.
     *
     * @return the fichier ouvrir menu
     */
    public JMenuItem getFichierOuvrirMenu() {
        return this.ouvrir;
    }

    /**
     * Gets edition ajouter livre.
     *
     * @return the edition ajouter livre
     */
    public JMenuItem getEditionAjouterLivre() { return this.ajouterLivre; }

    /**
     * Gets informations.
     *
     * @return the informations
     */
    public JMenuItem getInformations() { return informations; }

    /**
     * Gets table.
     *
     * @return the table
     */
    public JTable getTable() {
        return jTable;
    }

    /**
     * Gets delete item.
     *
     * @return the delete item
     */
    public JMenuItem getDeleteItem() { return deleteItem; }

    /**
     * Gets sauvegarder.
     *
     * @return the sauvegarder
     */
    public JMenuItem getSauvegarder() { return this.sauvegarder; }

    /**
     * Gets sauvegarder sous.
     *
     * @return the sauvegarder sous
     */
    public JMenuItem getSauvegarderSous() { return this.sauvegarderSous; }

    /**
     * Gets exporter.
     *
     * @return the exporter
     */
    public JMenuItem getExporter() { return this.exporter; }

    /**
     * Gets fermer.
     *
     * @return the fermer
     */
    public JMenuItem getFermer() { return this.fermer; }

    public JMenu getDatabase() { return this.database; }

    public JMenuItem getSwitchDatabase() { return this.switchDatabase; }

}
