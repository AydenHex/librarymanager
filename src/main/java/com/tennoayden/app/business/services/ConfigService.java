package com.tennoayden.app.business.services;

public class ConfigService {

    private static ConfigService single_instance = null;

    public String path;
    public Boolean modification;

    private ConfigService() {
        this.path = "";
        this.modification = false;
    }

    public static ConfigService getInstance()
    {
        if (single_instance == null)
            single_instance = new ConfigService();

        return single_instance;
    }
}
