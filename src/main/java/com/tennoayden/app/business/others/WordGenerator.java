package com.tennoayden.app.business.others;

import com.tennoayden.app.business.models.Bibliotheque;
import com.tennoayden.app.business.models.StatusType;
import com.tennoayden.app.business.services.BibliothequeService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.sl.usermodel.VerticalAlignment;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The type Word generator.
 */
public class WordGenerator {
    /**
     * The Document.
     */
    XWPFDocument document;
    /**
     * The Data.
     */
    HashMap<Bibliotheque.Livre.Auteur, ArrayList<Bibliotheque.Livre>> data;
    /**
     * The Bookmark id.
     */
    int bookmarkId;

    /**
     * Instantiates a new Word generator.
     *
     * @param path the path
     * @throws Exception the exception
     */
    public WordGenerator(String path) throws Exception {
        document = new XWPFDocument();
        data = BibliothequeService.getInstance().getLivresAuteurs();
        bookmarkId = 0;

        createPageDeGarde();
        addPageBreak();
        createSommaire();
        addPageBreak();
        createBookDetails();
        addPageBreak();
        createLivrePrete();


        FileOutputStream out = new FileOutputStream(path+".docx");
        document.write(out);
        out.close();
        document.close();
    }

    /**
     * Create sommaire.
     *
     * @throws Exception the exception
     */
    public void createSommaire() throws Exception {
        XWPFParagraph sommaire = document.createParagraph();
        sommaire.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun sommaireRun = sommaire.createRun();
        sommaireRun.setFontSize(36);
        sommaireRun.setBold(true);
        sommaireRun.setText("Sommaire");
        sommaireRun.addCarriageReturn();
        sommaireRun.addCarriageReturn();

        XWPFParagraph sommaireSousTitreAuteur = document.createParagraph();
        XWPFRun sommaireAuteurRun = sommaireSousTitreAuteur.createRun();
        sommaireAuteurRun.setFontSize(24);
        sommaireAuteurRun.setText("Mes auteurs");
        XWPFParagraph paragrafAuteurs = document.createParagraph();
        for (Bibliotheque.Livre.Auteur auteur : data.keySet()) {
            System.out.println(auteur.getNom());
            XWPFHyperlinkRun hyperlinkrun = createHyperlinkRunToAnchor(paragrafAuteurs, auteur.getNom() + auteur.getPrenom());
            hyperlinkrun.setText(auteur.getPrenom() + " " + auteur.getNom());
            hyperlinkrun.setUnderline(UnderlinePatterns.SINGLE);
            hyperlinkrun.addCarriageReturn();
        }
        XWPFParagraph sommaireSousTitrePrete = document.createParagraph();
        XWPFHyperlinkRun hyperlinkrun = createHyperlinkRunToAnchor(sommaireSousTitrePrete, "livreprete");
        hyperlinkrun.setFontSize(24);
        hyperlinkrun.setText("Mes livres prêté");
        hyperlinkrun.setUnderline(UnderlinePatterns.SINGLE);
        hyperlinkrun.addCarriageReturn();
    }

    /**
     * Add page break.
     */
    public void addPageBreak() {
        XWPFParagraph breakParagraph = document.createParagraph();
        breakParagraph.setPageBreak(true);
    }

    /**
     * Create book details.
     *
     * @throws IOException            the io exception
     * @throws InvalidFormatException the invalid format exception
     */
    public void createBookDetails() throws IOException, InvalidFormatException {
        XWPFParagraph detail = document.createParagraph();

        for (Bibliotheque.Livre.Auteur auteur : data.keySet()) {
            XWPFParagraph paragraph = createBookmarkedParagraph(document, auteur.getNom() + auteur.getPrenom(), bookmarkId++);
            XWPFRun run = paragraph.getRuns().get(0);
            run.setBold(true);
            run.setText(auteur.getPrenom() + " " + auteur.getNom());
            for (Bibliotheque.Livre livre : data.get(auteur)) {
                XWPFParagraph detailLivre = document.createParagraph();
                XWPFRun runLivre = detailLivre.createRun();
                runLivre.setText("Titre : " + livre.getTitre());
                runLivre.addCarriageReturn();
                runLivre.setText("Résumé : " + livre.getPresentation());
                runLivre.addCarriageReturn();
                runLivre.setText("Parution : " + livre.getParution());
                runLivre.addCarriageReturn();
                if (livre.getUrl() != null) {
                    try {
                        FileInputStream is = new FileInputStream(livre.getUrl());
                        runLivre.addBreak();
                        runLivre.addPicture(is, XWPFDocument.PICTURE_TYPE_JPEG, livre.getUrl(), Units.toEMU(100), Units.toEMU(100)); // 200x200 pixels
                        is.close();
                    }
                    catch (FileNotFoundException fe) {
                        runLivre.addBreak();
                        runLivre.setText("Image not found");
                    }
                }
                // TO DO: Complete image inclusion
            }
            run.addCarriageReturn();

        }
    }

    private void createLivrePrete() {
        XWPFParagraph paragraph = createBookmarkedParagraph(document, "livreprete", bookmarkId++);
        XWPFRun run = paragraph.getRuns().get(0);
        run.setBold(true);
        run.setStyle("Heading3");
        run.setText("Livre Prêté");

        XWPFTable table = document.createTable();
        int tableI = 1;

        XWPFTableRow tableRowOne = table.getRow(0);
        tableRowOne.getCell(0).setText("Titre");
        tableRowOne.addNewTableCell().setText("A qui ?");

        for (Bibliotheque.Livre livret : BibliothequeService.getInstance().bibliotheque.getLivre()) {
            if (livret.getStatus() == StatusType.PRETE) {
                XWPFTableRow tableRowTwo = table.createRow();
                tableRowTwo.getCell(0).setText(livret.getTitre());
                tableRowTwo.getCell(1).setText(livret.getAqui());
                tableI++;
            }
        }
    }
    private void createPageDeGarde() {
        XWPFParagraph garde = document.createParagraph();
        garde.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun gardeRun = garde.createRun();
        gardeRun.setFontSize(60);
        gardeRun.setVerticalAlignment(String.valueOf(VerticalAlignment.MIDDLE));
        gardeRun.setBold(true);
        gardeRun.addCarriageReturn();
        gardeRun.addCarriageReturn();
        gardeRun.addCarriageReturn();
        gardeRun.setText("Rapport Ma Bibliotheque");
    }

    /**
     * Create hyperlink run to anchor xwpf hyperlink run.
     *
     * @param paragraph the paragraph
     * @param anchor    the anchor
     * @return the xwpf hyperlink run
     * @throws Exception the exception
     */
    static XWPFHyperlinkRun createHyperlinkRunToAnchor(XWPFParagraph paragraph, String anchor) throws Exception {
        CTHyperlink cthyperLink=paragraph.getCTP().addNewHyperlink();
        cthyperLink.setAnchor(anchor);
        cthyperLink.addNewR();
        return new XWPFHyperlinkRun(
                cthyperLink,
                cthyperLink.getRArray(0),
                paragraph
        );
    }

    /**
     * Create bookmarked paragraph xwpf paragraph.
     *
     * @param document   the document
     * @param anchor     the anchor
     * @param bookmarkId the bookmark id
     * @return the xwpf paragraph
     */
    static XWPFParagraph createBookmarkedParagraph(XWPFDocument document, String anchor, int bookmarkId) {
        XWPFParagraph paragraph = document.createParagraph();
        CTBookmark bookmark = paragraph.getCTP().addNewBookmarkStart();
        bookmark.setName(anchor);
        bookmark.setId(BigInteger.valueOf(bookmarkId));
        XWPFRun run = paragraph.createRun();
        paragraph.getCTP().addNewBookmarkEnd().setId(BigInteger.valueOf(bookmarkId));
        return paragraph;
    }
}