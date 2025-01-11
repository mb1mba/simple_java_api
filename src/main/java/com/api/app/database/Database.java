package com.api.app.database;

import java.sql.*;

public class Database {
    private final String URL = "jdbc:postgresql://localhost:5432/database";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public PreparedStatement prepareStatement(Connection conn, String query) throws SQLException {
        return conn.prepareStatement(query);
    }

    public void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection");
            }
        }
    }

    public void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                System.out.println("Error closing statement.");
            }
        }
    }

    public void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.out.println("Error closing result set.");

            }
        }
    }
}
