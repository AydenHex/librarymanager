//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2019.11.10 à 06:21:39 PM CET 
//


package com.tennoayden.app.business.models;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour statusType.
 *
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * <p>
 * <pre>
 * &lt;simpleType name="statusType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ACQUIS"/>
 *     &lt;enumeration value="PRET"/>
 *     &lt;enumeration value="PRETE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "statusType")
@XmlEnum
public enum StatusType {

    /**
     * Acquis status type.
     */
    ACQUIS,
    /**
     * Pret status type.
     */
    PRET,
    /**
     * Prete status type.
     */
    PRETE;

    /**
     * Value string.
     *
     * @return the string
     */
    public String value() {
        return name();
    }

    /**
     * From value status type.
     *
     * @param v the v
     * @return the status type
     */
    public static StatusType fromValue(String v) {
        return valueOf(v);
    }

}
