package org.NAK;

import java.sql.Connection;

public class Main {

    public static void main(String[] args) {
        try {
            Connection conn = Database.getConnection();

            if (conn != null && !conn.isClosed()) {
                System.out.println("Connected to the database successfully!");
            } else {
                System.out.println("Failed to connect to the database.");
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred while trying to connect to the database.");
        }
    }
}
