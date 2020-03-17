package com.tennoayden.app.gui.models;


import com.tennoayden.app.business.models.UserManager;
import com.tennoayden.app.business.services.UserService;

import javax.swing.table.AbstractTableModel;

public class UserModel extends AbstractTableModel {

    private final UserManager userManager;
    private static final long serialVersionUID = 1L;
    private final String[] entetes = {"Prénom", "Nom", "Role"};

    public UserModel()
    {
        super();
        this.userManager = UserService.getInstance().userManager;
    }

    @Override
    public int getRowCount() {
        return this.userManager.getUser().size();
    }
    @Override
    public int getColumnCount() {
        return entetes.length;
    }
    public String getColumnName(int columnIndex) {
        return entetes[columnIndex];
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return this.userManager.getUser().get(rowIndex).getUsername();
            case 1:
                return this.userManager.getUser().get(rowIndex).getPassword();
            case 2:
                return this.userManager.getUser().get(rowIndex).getRole();
            default:
                return null; //Ne devrait jamais arriver
        }
    }
}
