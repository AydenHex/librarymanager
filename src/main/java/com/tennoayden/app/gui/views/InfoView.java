package com.tennoayden.app.gui.views;

import com.tennoayden.app.gui.models.InfoModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * The type Info view.
 */
public class InfoView extends JDialog {
        private JPanel panTitle, panVersion, panCollab;
        private JLabel titleInfo, version, collab;


    /**
     * Instantiates a new Info view.
     *
     * @param titre the titre
     */
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

    /**
     * Gets pan collab.
     *
     * @return the pan collab
     */
    public JPanel getPanCollab() {
        return panCollab;
    }

    /**
     * Sets pan collab.
     *
     * @param panCollab the pan collab
     */
    public void setPanCollab(JPanel panCollab) {
        this.panCollab = panCollab;
    }

    /**
     * Gets collab.
     *
     * @return the collab
     */
    public JLabel getCollab() {
        return collab;
    }

    /**
     * Sets collab.
     *
     * @param collab the collab
     */
    public void setCollab(JLabel collab) {
        this.collab = collab;
    }

    /**
     * Gets pan title.
     *
     * @return the pan title
     */
    public JPanel getPanTitle() {
        return panTitle;
    }

    /**
     * Sets pan title.
     *
     * @param panTitle the pan title
     */
    public void setPanTitle(JPanel panTitle) {
        this.panTitle = panTitle;
    }

    /**
     * Gets pan version.
     *
     * @return the pan version
     */
    public JPanel getPanVersion() {
        return panVersion;
    }

    /**
     * Sets pan version.
     *
     * @param panVersion the pan version
     */
    public void setPanVersion(JPanel panVersion) {
        this.panVersion = panVersion;
    }

    /**
     * Gets title info.
     *
     * @return the title info
     */
    public JLabel getTitleInfo() {
        return titleInfo;
    }

    /**
     * Sets title info.
     *
     * @param titleInfo the title info
     */
    public void setTitleInfo(JLabel titleInfo) {
        this.titleInfo = titleInfo;
    }

    /**
     * Gets version.
     *
     * @return the version
     */
    public JLabel getVersion() {
        return version;
    }

    /**
     * Sets version.
     *
     * @param version the version
     */
    public void setVersion(JLabel version) {
        this.version = version;
    }
}
