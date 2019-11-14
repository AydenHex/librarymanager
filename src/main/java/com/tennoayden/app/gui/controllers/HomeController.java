package com.tennoayden.app.gui.controllers;

import com.tennoayden.app.business.models.Bibliotheque;
import com.tennoayden.app.business.models.Filtre;
import com.tennoayden.app.business.others.WordGenerator;
import com.tennoayden.app.business.services.BibliothequeService;
import com.tennoayden.app.business.services.ConfigService;
import com.tennoayden.app.gui.models.InfoModel;
import com.tennoayden.app.gui.models.TableModel;
import com.tennoayden.app.gui.views.FormView;
import com.tennoayden.app.gui.views.HomeView;
import com.tennoayden.app.gui.views.InfoView;

import javax.swing.*;
import javax.xml.bind.JAXBException;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class HomeController {
    HomeView view;
    TableModel model;

    public HomeController(String titre) {
        this.model = new TableModel(BibliothequeService.getInstance().bibliotheque);
        this.view = new HomeView(titre, model);
        this.initView();
        this.initController();
    }

    public void initView() {
        this.reloadTable();
    }



    public void initController() {
            view.getFichierOuvrirMenu().addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        HomeController.this.chooseXML();
                    } catch (JAXBException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            view.getEditionAjouterLivre().addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        HomeController.this.ajouterLivre();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            view.getTable().addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                    try {
                        HomeController.this.modifierLivre(mouseEvent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void mousePressed(MouseEvent mouseEvent) {}
                @Override
                public void mouseReleased(MouseEvent mouseEvent) {}
                @Override
                public void mouseEntered(MouseEvent mouseEvent) {}
                @Override
                public void mouseExited(MouseEvent mouseEvent) {}
            });
            view.getDeleteItem().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    supprimerLivre();
                }
            });
            view.getInformations().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e)  {
                    try {
                        information();
                    }catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
        view.getSauvegarderSous().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    sauvegarderSous();
                } catch (JAXBException e) {
                    e.printStackTrace();
                }
            }
        });
        view.getSauvegarder().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    sauvegarder();
                } catch (JAXBException e) {
                    e.printStackTrace();
                }
            }
        });
        view.getExporter().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    exporter();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        view.getFermer().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    fermer();
                } catch (JAXBException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void reloadTable() {
        this.model.fireTableDataChanged();
        this.view.repaint();
    }

    //Listeners
    public void chooseXML() throws JAXBException {
        Filtre filtre = new Filtre(
                new String[]{".xml"},
                "Les fichiers XML (.xml)"
        );

        JFileChooser choix = new JFileChooser("Choisir un fichier");
        choix.addChoosableFileFilter(filtre);
        if(choix.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
            try {
                BibliothequeService.loadLivre(choix.getSelectedFile().getAbsolutePath());
            } catch (JAXBException e) {
                JOptionPane.showMessageDialog(this.view, "Veuillez séléctionné un fichier valide !", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
        this.reloadTable();

    }
    public void sauvegarderSous() throws JAXBException {
        JFileChooser choix = new JFileChooser("Sauvegarder...");
        if(choix.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
            BibliothequeService.getInstance().sauvegarderLivre(choix.getSelectedFile().getAbsolutePath());
            JOptionPane.showMessageDialog(this.view,
                    "Vos modification ont bien été sauvegarger");
        }

    }
    public void sauvegarder() throws JAXBException {
        if (ConfigService.getInstance().path.isEmpty()) {
            sauvegarderSous();
        } else {
            BibliothequeService.getInstance().sauvegarderLivre(ConfigService.getInstance().path);
        }
        JOptionPane.showMessageDialog(null,
                "Vos modification ont bien été sauvegarger");
    }
    public void exporter() throws Exception {
        JFileChooser choix = new JFileChooser();
        if(choix.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
            new WordGenerator(choix.getSelectedFile().getAbsolutePath());
        }
    }


    public void ajouterLivre() throws IOException {
        new FormController("Ajouter un livre", this);
    }
    public void modifierLivre(MouseEvent mouseEvent) throws IOException {
        JTable table =(JTable) mouseEvent.getSource();
        Point point = mouseEvent.getPoint();
        int row = table.rowAtPoint(point);
        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
            Bibliotheque.Livre livre = BibliothequeService.getInstance().bibliotheque.getLivre().get(table.getSelectedRow());
            new FormController("Modifier livre", this, livre);
        }
    }
    public void supprimerLivre() {
        int n = JOptionPane.showConfirmDialog(
                null,
                "Voulez-vous vraiment supprimer la ligne séléctionnée ?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);
        if (n == 0) {
            BibliothequeService.getInstance().bibliotheque.getLivre().
                    remove(view.getTable().getSelectedRow());
            reloadTable();
            ConfigService.getInstance().modification = true;
        }
    }

    public void fermer() throws JAXBException {
        if (ConfigService.getInstance().modification == true) {
            String[] options = new String[] {"Sauvegarder", "Annuler", "Quitter sans sauvegarder"};
            int option =  JOptionPane.showOptionDialog(null, "Title", "Message",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options, options[0]);

            switch(option) {
                case 0:
                    if (ConfigService.getInstance().path.isEmpty()) {
                        sauvegarderSous();
                    } else {
                        sauvegarder();
                        this.view.dispose();
                    }
                    break;
                case 1:
                    break;
                case 2:
                    this.view.dispose();
            }

        } else {
            view.dispose();
        }
    }

    public HomeView getView() {
        return this.view;
    }

    public void information() throws IOException {
        new InfoControler(new InfoModel(), new InfoView("Fenetre d'informations"), this);
    }

}
