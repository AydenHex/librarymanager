package com.tennoayden.app.gui.models;

import com.tennoayden.app.business.models.*;
import com.tennoayden.app.business.services.BibliothequeService;

import javax.swing.table.AbstractTableModel;

/**
 * The type Table model.
 */
public class TableModel extends AbstractTableModel {
    private final Bibliotheque bibliotheque;
    private static final long serialVersionUID = 1L;

    private final String[] entetes = {"Titre", "Auteur","Année","Présentation","Status","Rangée","Colonne"};

    /**
     * Instantiates a new Table model.
     *
     * @param bibliotheque the bibliotheque
     */
    public TableModel(Bibliotheque bibliotheque) {
        super();
        this.bibliotheque = BibliothequeService.getInstance().bibliotheque;
    }

    public int getRowCount() {
        return this.bibliotheque.getLivre().size();
    }

    public int getColumnCount() {
        return entetes.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return entetes[columnIndex];
    }


    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return this.bibliotheque.getLivre().get(rowIndex).getTitre();
            case 1:
                return this.bibliotheque.getLivre().get(rowIndex).getAuteur().getPrenom() + " " + this.bibliotheque.getLivre().get(rowIndex).getAuteur().getNom();
            case 2:
                return this.bibliotheque.getLivre().get(rowIndex).getParution();
            case 3:
                return this.bibliotheque.getLivre().get(rowIndex).getPresentation();
            case 4:
                return this.bibliotheque.getLivre().get(rowIndex).getStatus() == StatusType.PRETE ?
                        this.bibliotheque.getLivre().get(rowIndex).getStatus() + "(" +this.bibliotheque.getLivre().get(rowIndex).getAqui() +")":
                        this.bibliotheque.getLivre().get(rowIndex).getStatus();
            case 5:
                return this.bibliotheque.getLivre().get(rowIndex).getRangee();
            case 6:
                return this.bibliotheque.getLivre().get(rowIndex).getColonne();
            default:
                return null; //Ne devrait jamais arriver
        }
    }
}