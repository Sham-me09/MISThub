package ui;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB {

    private static final String URL =
            "jdbc:sqlite:src/database/misthub.db";

    public static Connection connect() {

        try {
            Connection conn = DriverManager.getConnection(URL);
            System.out.println("Database Connected");
            return conn;

        } catch (Exception e) {
            System.out.println("Database Failed");
            e.printStackTrace();
            return null;
        }
    }
}