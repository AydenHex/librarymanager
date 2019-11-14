package com.tennoayden.app.gui.models;

import java.io.File;
import java.util.ArrayList;

public class InfoModel {
    private String infoVersion="App | ReWork v3.0 | ";
    private String collaborator="<html><center><strong><U>Artisants developpeurs :</U><strong><br> Quentin Royer<br> Rigaud Louis<br><br> Fait avec <span style='color:red'>❤</span> par la #TeamAydenHexTenno</center><html>";
    private String pageTitre="<html><big><U>Information</U></big></html>";
    public InfoModel(){
        this.pageTitre=pageTitre;
        this.collaborator=collaborator;
        this.infoVersion=infoVersion;
    }

    public String getCollaborator() {
        return collaborator;
    }

    public void setCollaborator(String collaborator) {
        this.collaborator = collaborator;
    }

    public String getInfoVersion() {
        return infoVersion;
    }

    public void setInfoVersion(String infoVersion) {
        this.infoVersion = infoVersion;
    }


    public String getPageTitre() {
        return pageTitre;
    }

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
