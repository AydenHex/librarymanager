package com.tennoayden.app.business.models;

import javax.swing.table.AbstractTableModel;

public class JTableAdminFormModel extends AbstractTableModel {

    private final Object [][] donnees;

    private final String[] entetes = {"Prénom", "Nom", "Role"};

    public JTableAdminFormModel ()
    {
        super();

        // Temporaire car pas encore de controlleur faire model supplementaire pour lecture doc ?

        donnees= new Object[][] {
                // il faut recup du xml
                {"sudo", "sudo", "user" /*get model.getrole()*/},

        };


    }

    @Override
    public int getRowCount() {
        return 0;
    }

    @Override
    public int getColumnCount() {
        return 0;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        return null;
    }
}
