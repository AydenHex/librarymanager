package com.tennoayden.app.business.services;


import com.tennoayden.app.business.models.Bibliotheque;
import com.tennoayden.app.business.models.ObjectFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class BibliothequeService
{
    // static variable single_instance of type State
    private static BibliothequeService single_instance = null;

    public Bibliotheque bibliotheque;

    // private constructor restricted to this class itself
    private BibliothequeService()
    {
        bibliotheque = new ObjectFactory().createBibliotheque();

    }

    // static method to create instance of Singleton class
    public static BibliothequeService getInstance()
    {
        if (single_instance == null)
            single_instance = new BibliothequeService();

        return single_instance;
    }

    public static void loadLivre(String path) throws JAXBException {
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
    public void sauvegarderLivre(String path) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Bibliotheque.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        //Marshal the employees list in file
        jaxbMarshaller.marshal(this.bibliotheque, new File(path));
    }
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