package com.tennoayden.app.gui.views;

import com.tennoayden.app.gui.models.TableModel;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class HomeView extends JFrame{

    //MenuBar
    private JMenuBar menubar;
    private JMenu fichier, edition, apropos;
    private JMenuItem ouvrir, exporter, fermer, ajouterLivre, sauvegarder, sauvegarderSous, informations;
    private JPopupMenu tableMenu;
    private JMenuItem deleteItem;

    //Table
    private JPanel panTable;
    private JTable jTable;
    private JScrollPane jScrollPane;

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

        ouvrir = new JMenuItem("Ouvrir");
        exporter = new JMenuItem("Exporter");
        fermer = new JMenuItem("Fermer");

        ajouterLivre = new JMenuItem("Ajouter un livre");
        sauvegarder = new JMenuItem("Sauvegarder...");
        sauvegarderSous = new JMenuItem("Sauvegarder sous...");


        informations = new JMenuItem("Informations");

        fichier.add(ouvrir);
        fichier.add(exporter);
        fichier.add(fermer);
        edition.add(ajouterLivre);
        edition.add(sauvegarder);
        edition.add(sauvegarderSous);
        apropos.add(informations);

        menubar.add(fichier);
        menubar.add(edition);
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

    public JMenuItem getFichierOuvrirMenu() {
        return this.ouvrir;
    }

    public JMenuItem getEditionAjouterLivre() { return this.ajouterLivre; }

    public JMenuItem getInformations() { return informations; }

    public JTable getTable() {
        return jTable;
    }

    public JMenuItem getDeleteItem() { return deleteItem; }
    public JMenuItem getSauvegarder() { return this.sauvegarder; }

    public JMenuItem getSauvegarderSous() { return this.sauvegarderSous; }
    public JMenuItem getExporter() { return this.exporter; }
    public JMenuItem getFermer() { return this.fermer; }

}
