package com.tennoayden.app.business.services;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import javax.xml.crypto.Data;
import java.sql.*;

public class DatabaseService {

    private static DatabaseService single_instance = null;
    public static String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "//sqlite.db";
    private static final Logger logger = Logger.getLogger(DatabaseService.class);



    public static DatabaseService getInstance() {
        if (single_instance == null)
            single_instance = new DatabaseService();

        return single_instance;
    }

    private DatabaseService() {
        createNewDatabase();
        migrate();
    }

    public static void migrate() {
        // SQLite connection string

        // SQL statement for creating an user table

        String sqlRoleTable = "CREATE TABLE IF NOT EXISTS Roles(\n" +
                "    id INTEGER AUTO_INCREMENT PRIMARY KEY,\n" +
                "    name TEXT NOT NULL)";

        String sqlUserTable = "CREATE TABLE IF NOT EXISTS Users(\n" +
                "    id INTEGER AUTO_INCREMENT PRIMARY KEY,\n" +
                "    username TEXT NOT NULL,\n" +
                "    password TEXT NOT NULL,\n" +
                "    email TEXT UNIQUE,\n" +
                "    userRole INTEGER NOT NULL,\n" +
                "    FOREIGN KEY (userRole) REFERENCES Roles(id));";
        try (Connection conn = DriverManager.getConnection(DatabaseService.url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sqlRoleTable);
            stmt.execute(sqlUserTable);

            logger.log(Level.INFO, String.format("Migration of the database is a success"));

        } catch (SQLException e) {
            logger.log(Level.ERROR, String.format("Error raised while migration of database: %s", e));
        }
    }
    public static void createNewDatabase() {

        String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "//sqlite.db";

        try (Connection conn = DriverManager.getConnection(DatabaseService.url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();

                logger.log(Level.INFO, String.format("The driver name is %s", meta.getDriverName()));
                logger.log(Level.INFO, "A new database has been created.");

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
