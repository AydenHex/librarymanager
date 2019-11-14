package com.tennoayden.app.gui.views;

import com.tennoayden.app.gui.models.InfoModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class InfoView extends JDialog {
        private JPanel panTitle, panVersion, panCollab;
        private JLabel titleInfo, version, collab;


    public InfoView(String titre){
        this.setTitle(titre);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setSize(new Dimension(500, 200));
       // this.setBackground(new Color(150, 133, 138, 45));
        this.setLayout(new BorderLayout());
        this.setVisible(true);

        //set InfoView Panel
        //set JLabel for conterning text
        collab= new JLabel();
        titleInfo=new JLabel();
        version=new JLabel();

        //for Title
        panTitle=new JPanel();
        panTitle.add(titleInfo, BorderLayout.CENTER);

        //for Collaborator in middle
        panVersion=new JPanel();

        panVersion.add(version,BorderLayout.CENTER);

        //for version info
        panCollab=new JPanel();
        panCollab.add(collab, BorderLayout.CENTER);


        this.getContentPane().add(panTitle, BorderLayout.NORTH);
        this.getContentPane().add(panCollab, BorderLayout.CENTER);
        this.getContentPane().add(panVersion, BorderLayout.SOUTH);
    }

    public JPanel getPanCollab() {
        return panCollab;
    }

    public void setPanCollab(JPanel panCollab) {
        this.panCollab = panCollab;
    }

    public JLabel getCollab() {
        return collab;
    }

    public void setCollab(JLabel collab) {
        this.collab = collab;
    }

    public JPanel getPanTitle() {
        return panTitle;
    }

    public void setPanTitle(JPanel panTitle) {
        this.panTitle = panTitle;
    }

    public JPanel getPanVersion() {
        return panVersion;
    }

    public void setPanVersion(JPanel panVersion) {
        this.panVersion = panVersion;
    }

    public JLabel getTitleInfo() {
        return titleInfo;
    }

    public void setTitleInfo(JLabel titleInfo) {
        this.titleInfo = titleInfo;
    }

    public JLabel getVersion() {
        return version;
    }

    public void setVersion(JLabel version) {
        this.version = version;
    }
}
