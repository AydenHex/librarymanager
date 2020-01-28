package com.tennoayden.app.business.services;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import javax.xml.crypto.Data;
import java.sql.*;

import com.tennoayden.app.business.models.User;


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


    public Connection connect() {
        // SQLite connection string
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DatabaseService.url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void migrate() {
        // SQLite connection string

        // SQL Request
        String sqlUserTable = "CREATE TABLE IF NOT EXISTS Users(\n" +
                "    id INTEGER AUTO_INCREMENT PRIMARY KEY,\n" +
                "    username TEXT NOT NULL,\n" +
                "    password TEXT NOT NULL,\n" +
                "    email TEXT UNIQUE,\n" +
                "    role TEXT NOT NULL);";

        String sqlBooksTable = "CREATE TABLE IF NOT EXISTS Books(\n" +
                " id INTEGER AUTO_INCREMENT PRIMARY KEY,\n" +
                " title TEXT NOT NULL,\n" +
                " author_firstname TEXT NOT NULL,\n" +
                " resume TEST NOT NULL,\n" +
                " author_lastname TEXT NOT NULL,\n" +
                " release INTEGER(4) NOT NULL,\n" +
                " column INTEGER(2) NOT NULL,\n" +
                " row INTEGER(2) NOT NULL,\n" +
                " url TEXT,\n" +
                " aqui TEXT,\n" +
                " status TEXT);";
        try (Connection conn = DriverManager.getConnection(DatabaseService.url);
             Statement stmt = conn.createStatement()) {
            // create new tables
            stmt.execute(sqlUserTable);
            stmt.execute(sqlBooksTable);

            logger.log(Level.INFO, String.format("Migration of the database is a success"));
            stmt.close();

        } catch (SQLException e) {
            logger.log(Level.ERROR, String.format("Error raised while migration of database: %s", e));
        }
    }
    public static void createNewDatabase() {
        /**
         * Allow to create a database.
         */

        try (Connection conn = DriverManager.getConnection(DatabaseService.url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();

                logger.log(Level.INFO, String.format("The driver name is %s", meta.getDriverName()));
                logger.log(Level.INFO, "A new database has been created.");

            }
            conn.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
