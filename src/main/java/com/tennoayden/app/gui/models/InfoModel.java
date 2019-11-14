package com.tennoayden.app.gui.models;

import java.io.File;
import java.util.ArrayList;

/**
 * The type Info model.
 */
public class InfoModel {
    private String infoVersion="App | ReWork v3.0 | ";
    private String collaborator="<html><center><strong><U>Artisants developpeurs :</U><strong><br> Quentin Royer<br> Rigaud Louis<br><br> Fait avec <span style='color:red'>❤</span> par la #TeamAydenHexTenno</center><html>";
    private String pageTitre="<html><big><U>Information</U></big></html>";

    /**
     * Instantiates a new Info model.
     */
    public InfoModel(){
        this.pageTitre=pageTitre;
        this.collaborator=collaborator;
        this.infoVersion=infoVersion;
    }

    /**
     * Gets collaborator.
     *
     * @return the collaborator
     */
    public String getCollaborator() {
        return collaborator;
    }

    /**
     * Sets collaborator.
     *
     * @param collaborator the collaborator
     */
    public void setCollaborator(String collaborator) {
        this.collaborator = collaborator;
    }

    /**
     * Gets info version.
     *
     * @return the info version
     */
    public String getInfoVersion() {
        return infoVersion;
    }

    /**
     * Sets info version.
     *
     * @param infoVersion the info version
     */
    public void setInfoVersion(String infoVersion) {
        this.infoVersion = infoVersion;
    }


    /**
     * Gets page titre.
     *
     * @return the page titre
     */
    public String getPageTitre() {
        return pageTitre;
    }

    /**
     * Sets page titre.
     *
     * @param pageTitre the page titre
     */
    public void setPageTitre(String pageTitre) {
        this.pageTitre = pageTitre;
    }

    @Override
    public String toString() {
        return "InfoModel{" +
                "infoVersion='" + infoVersion + '\'' +
                ", collaborator=" + collaborator +
                '}';
    }
}
