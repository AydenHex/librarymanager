package com.tennoayden.app.business.services;

/**
 * The type Config service.
 */
public class ConfigService {

    private static ConfigService single_instance = null;

    /**
     * The Path.
     */
    public String path;
    /**
     * The Modification.
     */
    public Boolean modification;

    private ConfigService() {
        this.path = "";
        this.modification = false;
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ConfigService getInstance()
    {
        if (single_instance == null)
            single_instance = new ConfigService();

        return single_instance;
    }
}
