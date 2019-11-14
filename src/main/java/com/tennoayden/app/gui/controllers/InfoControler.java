package com.tennoayden.app.gui.controllers;

import com.tennoayden.app.gui.models.InfoModel;
import com.tennoayden.app.gui.views.InfoView;

import java.io.IOException;

/**
 * The type Info controler.
 */
public class InfoControler {
        private InfoModel infoModel;
        private InfoView infoView;

    /**
     * Instantiates a new Info controler.
     *
     * @param infoModel      the info model
     * @param infoView       the info view
     * @param homeController the home controller
     * @throws IOException the io exception
     */
    public InfoControler(InfoModel infoModel, InfoView infoView, HomeController homeController) throws IOException {
            this.infoModel=infoModel;
            this.infoView=infoView;


            infoView.getTitleInfo().setText(infoModel.getPageTitre());
            infoView.getVersion().setText(infoModel.getInfoVersion());
            infoView.getCollab().setText(infoModel.getCollaborator());



    }

}
