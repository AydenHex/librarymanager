package com.tennoayden.app.gui.views;

import com.tennoayden.app.business.models.StatusType;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;

/**
 * The type Form view.
 */
public class FormView extends JDialog{
    private JLabel titreLabel, auteurLabel, resumeLabel, parutionLabel, rangeeLabel, colonneLabel, statusLabel, aQuiLabel, imageLabel;
    private JTextField titre, auteurPrenom, auteurNom, parution, rangee, colonne, aQui, image;
    private ImageIcon imageIcon;
    private JComboBox status;
    private JTextArea resume;
    private JPanel panForm;
    private Dimension textDimension, areaDimension;
    private GridBagConstraints c;
    private Font font;
    private JButton appliquer;

    /**
     * Instantiates a new Form view.
     *
     * @param parent    the parent
     * @param titreForm the titre form
     * @throws IOException the io exception
     */
    public FormView(JFrame parent, String titreForm) throws IOException {
        super(parent, titreForm);
        // Set common var
        textDimension = new Dimension(100, 20);
        areaDimension = new Dimension(200, 20);
        c = new GridBagConstraints();
        font = new Font("Verdana", Font.PLAIN, 14);

        // Set JTextfield and Jlabel...
        titreLabel = new JLabel("Titre :");
        titre = new JTextField();
        auteurLabel = new JLabel("Auteur :");
        auteurNom = new JTextField();
        auteurPrenom = new JTextField();
        resumeLabel = new JLabel("Résumé :");
        resume = new JTextArea(4,8);
        resume.setLineWrap(true);
        parutionLabel = new JLabel("Parution :");
        parution = new JTextField(4);
        rangeeLabel = new JLabel("Rangée :");
        rangee = new JTextField(1);
        colonneLabel = new JLabel("Colonne :");
        colonne = new JTextField(1);
        statusLabel = new JLabel("Status :");
        status = new JComboBox(StatusType.values());
        aQuiLabel = new JLabel("à :");
        aQui = new JTextField();
        aQui.setEnabled(false);
        imageLabel = new JLabel("Image :");
        image = new JTextField();

        appliquer = new JButton(titreForm);

        imageIcon = new ImageIcon();

        //set name component
        titre.setName("titre");
        auteurNom.setName("auteurnom");
        auteurPrenom.setName("auteurprenom");
        resume.setName("resume");
        parution.setName("parution");
        rangee.setName("rangee");
        colonne.setName("colonne");
        status.setName("status");
        aQui.setName("aqui");
        image.setName("image");
        appliquer.setName("appliquer");


        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(new BorderLayout());

        panForm = new JPanel();
        panForm.setPreferredSize(new Dimension(400, 600));
        panForm.setBorder((BorderFactory.createTitledBorder("Formulaire")));
        panForm.setLayout(new GridBagLayout());

        placeComponents();

        add(panForm, BorderLayout.NORTH);
        pack();
    }

    /**
     * Place components.
     */
    public void placeComponents() {
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(3,3,3,3);
        c.anchor = GridBagConstraints.LAST_LINE_END;
        statusLabel.setFont(font);
        panForm.add(statusLabel, c);
        c.gridx = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        status.setPreferredSize(textDimension);
        panForm.add(status, c);

        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.LAST_LINE_END;
        aQuiLabel.setFont(font);
        panForm.add(aQuiLabel, c);
        c.gridx = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        aQui.setPreferredSize(textDimension);
        panForm.add(aQui, c);

        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(3,0,3,3);
        c.anchor = GridBagConstraints.LAST_LINE_END;
        titreLabel.setFont(font);
        panForm.add(titreLabel, c);
        c.gridx = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        titre.setPreferredSize(textDimension);
        panForm.add(titre, c);

        c.gridx = 0;
        c.gridy = 3;
        c.anchor = GridBagConstraints.LAST_LINE_END;
        auteurLabel.setFont(font);
        panForm.add(auteurLabel, c);
        c.gridx = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        auteurPrenom.setPreferredSize(textDimension);
        panForm.add(auteurPrenom, c);
        c.gridx = 2;
        auteurNom.setPreferredSize(textDimension);
        panForm.add(auteurNom, c);

        c.gridx = 0;
        c.gridy = 4;
        c.anchor = GridBagConstraints.LAST_LINE_END;
        resumeLabel.setFont(font);
        panForm.add(resumeLabel, c);
        c.gridx = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        panForm.add(resume, c);

        c.gridx = 0;
        c.gridy = 5;
        c.anchor = GridBagConstraints.LAST_LINE_END;
        parutionLabel.setFont(font);
        panForm.add(parutionLabel, c);
        c.gridx = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        panForm.add(parution, c);

        c.gridx = 0;
        c.gridy = 6;
        c.anchor = GridBagConstraints.LAST_LINE_END;
        rangeeLabel.setFont(font);
        panForm.add(rangeeLabel, c);
        c.gridx = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        panForm.add(rangee, c);

        c.gridx = 0;
        c.gridy = 7;
        c.anchor = GridBagConstraints.LAST_LINE_END;
        colonneLabel.setFont(font);
        panForm.add(colonneLabel, c);
        c.gridx = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        panForm.add(colonne, c);

        c.gridy = 8;
        c.gridx = 0;
        c.anchor = GridBagConstraints.LAST_LINE_END;
        imageLabel.setFont(font);
        panForm.add(imageLabel, c);
        c.gridx = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        image.setPreferredSize(textDimension);
        panForm.add(image, c);


        c.gridy = 9;
        c.gridx = 2;
        panForm.add(appliquer, c);

        add(new JLabel(imageIcon), BorderLayout.SOUTH);
    }

    /**
     * Gets titre.
     *
     * @return the titre
     */
    public String getTitre() {
        return titre.getText();
    }

    /**
     * Sets titre.
     *
     * @param titre the titre
     */
    public void setTitre(String titre) {
        this.titre.setText(titre);
    }

    /**
     * Gets auteur prenom.
     *
     * @return the auteur prenom
     */
    public String getAuteurPrenom() {
        return auteurPrenom.getText();
    }

    /**
     * Sets auteur prenom.
     *
     * @param prenom the prenom
     */
    public void setAuteurPrenom(String prenom) {
        this.auteurPrenom.setText(prenom);
    }

    /**
     * Gets auteur nom.
     *
     * @return the auteur nom
     */
    public String getAuteurNom() {
        return auteurNom.getText();
    }

    /**
     * Sets auteur nom.
     *
     * @param nom the nom
     */
    public void setAuteurNom(String nom) {
        this.auteurNom.setText(nom);
    }

    /**
     * Gets resume.
     *
     * @return the resume
     */
    public String getResume() {
        return resume.getText();
    }

    /**
     * Sets resume.
     *
     * @param resume the resume
     */
    public void setResume(String resume) {
        this.resume.setText(resume);
    }

    /**
     * Gets parution.
     *
     * @return the parution
     */
    public Integer getParution() {
        return Integer.parseInt(parution.getText());
    }

    /**
     * Sets parution.
     *
     * @param parution the parution
     */
    public void setParution(Integer parution) {
        this.parution.setText(Integer.toString(parution));
    }

    /**
     * Gets rangee.
     *
     * @return the rangee
     */
    public Short getRangee() {
        return Short.parseShort(rangee.getText());
    }

    /**
     * Sets rangee.
     *
     * @param rangee the rangee
     */
    public void setRangee(Short rangee) {
        this.rangee.setText(Short.toString(rangee));
    }

    /**
     * Gets colonne.
     *
     * @return the colonne
     */
    public Short getColonne() {
        return Short.parseShort(colonne.getText());
    }

    /**
     * Sets colonne.
     *
     * @param colonne the colonne
     */
    public void setColonne(Short colonne) {
        this.colonne.setText(Short.toString(colonne));
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public StatusType getStatus() {
        return StatusType.valueOf(status.getSelectedItem().toString());
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(StatusType status) {
        this.status.setSelectedItem(status);
    }

    /**
     * Gets a qui.
     *
     * @return the a qui
     */
    public String getAQui() {
        return aQui.getText();
    }

    /**
     * Sets a qui.
     *
     * @param aQui the a qui
     */
    public void setAQui(String aQui) {
        this.aQui.setText(aQui);
    }

    /**
     * Gets button.
     *
     * @return the button
     */
    public JButton getButton() {
        return this.appliquer;
    }

    /**
     * Gets aqui field.
     *
     * @return the aqui field
     */
    public JTextField getAquiField() {
        return this.aQui;
    }

    /**
     * Gets status field.
     *
     * @return the status field
     */
    public JComboBox getStatusField() {
        return this.status;
    }

    /**
     * Gets image icon.
     *
     * @return the image icon
     */
    public ImageIcon getImageIcon() { return this.imageIcon; }

    /**
     * Gets url.
     *
     * @return the url
     */
    public String getURL() { return this.image.getText(); }

    /**
     * Sets url.
     *
     * @param image the image
     */
    public void setURL(String image) { this.image.setText(image);}

}
