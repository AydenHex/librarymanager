package com.tennoayden.app.business.services;


import com.tennoayden.app.business.models.Bibliotheque;
import com.tennoayden.app.business.models.ObjectFactory;
import com.tennoayden.app.business.models.StatusType;
import com.tennoayden.app.gui.controllers.FormController;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.sql.*;

/**
 * The type Bibliotheque service.
 */
public class BibliothequeService
{
    private static final Logger logger = Logger.getLogger(BibliothequeService.class);

    // static variable single_instance of type State
    private static BibliothequeService single_instance = null;

    /**
     * The Bibliotheque.
     */
    public Bibliotheque bibliotheque;

    // private constructor restricted to this class itself
    private BibliothequeService()
    {
        bibliotheque = new ObjectFactory().createBibliotheque();

    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
// static method to create instance of Singleton class
    public static BibliothequeService getInstance()
    {
        if (single_instance == null)
            single_instance = new BibliothequeService();

        return single_instance;
    }

    /**
     * Load livre.
     *
     * @param path the path
     * @throws JAXBException the jaxb exception
     */
    public void loadLivre(String path) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Bibliotheque.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        //reset bibliotheque
        BibliothequeService.getInstance().bibliotheque.getLivre().clear();

        //We had written this file in marshalling example
        Bibliotheque emps = (Bibliotheque) jaxbUnmarshaller.unmarshal(new File(path));

        for(Bibliotheque.Livre emp : emps.getLivre())
        {
            BibliothequeService.getInstance().bibliotheque.getLivre().add(emp);
        }
    }

    public void loadLivreDB() {
        String sql = "SELECT * FROM books";

        try (Connection conn = DatabaseService.getInstance().connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                Bibliotheque.Livre book = new Bibliotheque.Livre();
                book.setId(rs.getInt("id"));
                book.setTitre(rs.getString("title"));
                book.setAqui(rs.getString("aqui"));
                book.setColonne(rs.getShort("column"));
                book.setRangee(rs.getShort("row"));
                book.setParution(rs.getInt("release"));
                book.setStatus(StatusType.valueOf(rs.getString("status")));
                book.setUrl(rs.getString("url"));
                book.setPresentation(rs.getString("resume"));
                Bibliotheque.Livre.Auteur auteur = new Bibliotheque.Livre.Auteur();
                auteur.setNom(rs.getString("author_lastname"));
                auteur.setPrenom(rs.getString("author_firstname"));
                book.setAuteur(auteur);

                this.bibliotheque.getLivre().add(book);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void searchInLivres(String substring) {
        ArrayList<Bibliotheque.Livre> newLivres = new ArrayList<Bibliotheque.Livre>();
        for (Bibliotheque.Livre l : bibliotheque.getLivre()) {
            if (l.getTitre().contains(substring)) {
                newLivres.add(l);
            }
        }
        bibliotheque.setLivre(newLivres);
    }


    /**
     * Sauvegarder livre.
     *
     * @param path the path
     * @throws JAXBException the jaxb exception
     */
    public void sauvegarderLivre(String path) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Bibliotheque.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        //Marshal the employees list in file
        jaxbMarshaller.marshal(this.bibliotheque, new File(path));

        logger.log(Level.INFO, String.format("The user %s saved the bookcase !"));
    }

    /**
     * Gets livres auteurs.
     *
     * @return the livres auteurs
     */
    public HashMap<Bibliotheque.Livre.Auteur, ArrayList<Bibliotheque.Livre>> getLivresAuteurs() {
        HashMap<Bibliotheque.Livre.Auteur, ArrayList<Bibliotheque.Livre>> col = new HashMap<>();
        for (Bibliotheque.Livre livre: this.bibliotheque.getLivre()) {
            if (col.containsKey(livre.getAuteur())) {
                col.get(livre.getAuteur()).add(livre);
            } else {
                col.put(livre.getAuteur(), new ArrayList<Bibliotheque.Livre>());
                col.get(livre.getAuteur()).add(livre);
            }
        }
        return col;
    }
}