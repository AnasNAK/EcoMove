package org.NAK.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static volatile Database instance;
    private static volatile Connection conn;

    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/EcoVole";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "anas";

    private Database() {

    }

    public static Database getInstance() {
        if (instance == null) {
            synchronized (Database.class) {
                if (instance == null) {
                    instance = new Database();
                }
            }
        }
        return instance;
    }

    public Connection establishConnection() {
        if (conn == null) {
            synchronized (Database.class) {
                if (conn == null) {
                    try {
                        Class.forName("org.postgresql.Driver");
                        conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

                        if (conn != null) {
                            System.out.println("Connection to database established.");
                        }
                    } catch (ClassNotFoundException | SQLException e) {
                        e.printStackTrace();
                        throw new RuntimeException("Failed to connect to the database");
                    }
                }
            }
        }
        return conn;
    }

    public static void closeConnection() {
        if (conn != null) {
            synchronized (Database.class) {
                if (conn != null) {
                    try {
                        conn.close();
                        conn = null; // Set to null after closing
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
