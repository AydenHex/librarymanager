package com.tennoayden.app.business.models;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * The type Filtre.
 */
public class Filtre extends FileFilter {
    /**
     * The Les suffixes.
     */
    String [] lesSuffixes;
    /**
     * The La description.
     */
    String  laDescription;

    /**
     * Instantiates a new Filtre.
     *
     * @param lesSuffixes   the les suffixes
     * @param laDescription the la description
     */
    public Filtre(String []lesSuffixes,
                     String laDescription){
        this.lesSuffixes = lesSuffixes;
        this.laDescription = laDescription;
    }

    /**
     * Appartient boolean.
     *
     * @param suffixe the suffixe
     * @return the boolean
     */
    boolean appartient( String suffixe ){
        for( int i = 0; i<lesSuffixes.length; ++i)
            if(suffixe.equals(lesSuffixes[i]))
                return true;
        return false;
    }
    public boolean accept(File file) {
        if (file.isDirectory())  return true;
        String suffixe = null;
        String s = file.getName();
        int i = s.lastIndexOf('.');
        if(i > 0 &&  i < s.length() - 1)
            suffixe=s.substring(i+1).toLowerCase();
        return suffixe!=null&&appartient(suffixe);
    }

    // la description du filtre
    public String getDescription() {
        return laDescription;
    }
}