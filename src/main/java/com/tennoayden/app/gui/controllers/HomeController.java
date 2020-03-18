package com.tennoayden.app.gui.controllers;

import com.tennoayden.app.business.models.Bibliotheque;
import com.tennoayden.app.business.models.Filtre;
import com.tennoayden.app.business.others.WordGenerator;
import com.tennoayden.app.business.services.UserService;
import com.tennoayden.app.business.services.BibliothequeService;
import com.tennoayden.app.business.services.ConfigService;
import com.tennoayden.app.gui.models.InfoModel;
import com.tennoayden.app.gui.models.TableModel;
import com.tennoayden.app.gui.views.AdminFormView;
import com.tennoayden.app.gui.views.HomeView;
import com.tennoayden.app.gui.views.InfoView;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.xml.bind.JAXBException;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * The type Home controller.
 */
public class HomeController {

    private static final Logger logger = Logger.getLogger(FormController.class);

    /**
     * The View.
     */
    HomeView view;
    /**
     * The Model.
     */
    TableModel model;

    /**
     * Instantiates a new Home controller.
     *
     * @param titre the titre
     */
    public HomeController(String titre) {
        this.model = new TableModel(BibliothequeService.getInstance().bibliotheque);
        this.view = new HomeView(titre, model);
        this.initView();
        this.initController();
    }

    /**
     * Init view.
     */
    public void initView() {
        if (ConfigService.getInstance().database) {
            BibliothequeService.getInstance().loadLivreDB();
        }
        this.reloadTable();
    }


    /**
     * Init controller.
     */
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


            view.getButtonSearch().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (view.getSearchTextfield().getText().isEmpty() || view.getSearchTextfield().getText().trim().isEmpty()) {
                        BibliothequeService.getInstance().loadLivreDB();
                    } else {
                       BibliothequeService.getInstance().searchInLivres(view.getSearchTextfield().getText());
                    }
                    reloadTable();
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

        view.getLogout().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (ConfigService.getInstance().modification == true) {
                        JOptionPane.showMessageDialog(view, "Veuillez sauvegardez vos modification avant de vous déconnecter");
                    } else {
                        UserService.getInstance().currentUser = null;
                        new LoginController();
                        view.dispose();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
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


        view.getAjouterUtilisateur().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    UsersController usersController = new UsersController();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

                view.getSwitchDatabase().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        switchDatabase();

                    }
                });

                // Authorization code
                if (!UserService.getInstance().currentUser.getRole().equals("admin")) {
                    view.getDeleteItem().setEnabled(false);
                    view.getDatabase().setEnabled(false);
                    view.getAdministateur().setEnabled(false);
                    view.getEdition().setEnabled(false);
                }
            }

            /**
             * Reload table.
             */
            public void reloadTable() {
                this.model.fireTableDataChanged();
                this.view.repaint();
            }

            /**
             * Choose xml.
             *
             * @throws JAXBException the jaxb exception
             */
//Listeners
            public void chooseXML() throws JAXBException {
                Filtre filtre = new Filtre(
                        new String[]{".xml"},
                        "Les fichiers XML (.xml)"
                );

                JFileChooser choix = new JFileChooser("Choisir un fichier");
                choix.addChoosableFileFilter(filtre);
                if (choix.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    try {
                        ConfigService.getInstance().database = false;
                        BibliothequeService.getInstance().loadLivre(choix.getSelectedFile().getAbsolutePath());
                    } catch (JAXBException e) {
                        JOptionPane.showMessageDialog(this.view, "Veuillez séléctionné un fichier valide !", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
                this.reloadTable();

            }

            /**
             * Sauvegarder sous.
             *
             * @throws JAXBException the jaxb exception
             */
            public void sauvegarderSous() throws JAXBException {
                JFileChooser choix = new JFileChooser("Sauvegarder...");
                if (choix.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    BibliothequeService.getInstance().sauvegarderLivre(choix.getSelectedFile().getAbsolutePath());
                    JOptionPane.showMessageDialog(this.view,
                            "Vos modification ont bien été sauvegarger");
                }

            }

            /**
             * Sauvegarder.
             *
             * @throws JAXBException the jaxb exception
             */
            public void sauvegarder() throws JAXBException {
                if (ConfigService.getInstance().path.isEmpty()) {
                    sauvegarderSous();
                } else {
                    BibliothequeService.getInstance().sauvegarderLivre(ConfigService.getInstance().path);
                }
                JOptionPane.showMessageDialog(null,
                        "Vos modification ont bien été sauvegarger");
            }

            /**
             * Exporter.
             *
             * @throws Exception the exception
             */
            public void exporter() throws Exception {
                JFileChooser choix = new JFileChooser();
                if (choix.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    new WordGenerator(choix.getSelectedFile().getAbsolutePath());
                    logger.log(Level.INFO, String.format("The user %s exported the library", UserService.getInstance().currentUser.getUsername()));
                }
            }


            /**
             * Ajouter livre.
             *
             * @throws IOException the io exception
             */
            public void ajouterLivre() throws IOException {
                new FormController("Ajouter un livre", this);
            }

            /**
             * Modifier livre.
             *
             * @param mouseEvent the mouse event
             * @throws IOException the io exception
             */
            public void modifierLivre(MouseEvent mouseEvent) throws IOException {
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    Bibliotheque.Livre livre = BibliothequeService.getInstance().bibliotheque.getLivre().get(table.getSelectedRow());
                    new FormController("Modifier livre", this, livre);
                }
            }

            /**
             * Supprimer livre.
             */
            public void supprimerLivre() {
                int n = JOptionPane.showConfirmDialog(
                        null,
                        "Voulez-vous vraiment supprimer la ligne séléctionnée ?",
                        "Confirmation",
                        JOptionPane.YES_NO_OPTION);
                if (n == 0) {
                    String bookName = BibliothequeService.getInstance().bibliotheque.getLivre().get(view.getTable().getSelectedRow()).getTitre();
                    BibliothequeService.getInstance().bibliotheque.getLivre().
                            remove(view.getTable().getSelectedRow());
                    reloadTable();
                    ConfigService.getInstance().modification = true;
                    logger.log(Level.INFO, String.format("The user %s has deleted the book : %s", UserService.getInstance().currentUser.getUsername(), bookName));
                }
            }

            public void switchDatabase() {
                if (ConfigService.getInstance().modification == true) {
                    JOptionPane.showMessageDialog(view, "Veuillez sauvegarder avant de passer en mode base de donnée !");
                    return;
                }
                ConfigService.getInstance().database = true;
                BibliothequeService.getInstance().bibliotheque.getLivre().clear();
                BibliothequeService.getInstance().loadLivreDB();
                reloadTable();
            }

            /**
             * Fermer.
             *
             * @throws JAXBException the jaxb exception
             */
            public void fermer() throws JAXBException {
                if (ConfigService.getInstance().modification == true) {
                    String[] options = new String[]{"Sauvegarder", "Annuler", "Quitter sans sauvegarder"};
                    int option = JOptionPane.showOptionDialog(null, "Title", "Message",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                            null, options, options[0]);

                    switch (option) {
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

            /**
             * Gets view.
             *
             * @return the view
             */
            public HomeView getView() {
                return this.view;
            }

            /**
             * Information.
             *
             * @throws IOException the io exception
             */
            public void information() throws IOException {
                new InfoControler(new InfoModel(), new InfoView("Fenetre d'informations"), this);
            }
        }



