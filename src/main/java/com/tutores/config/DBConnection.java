package com.tutores.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL  = "jdbc:postgresql://dpg-d6hjtan5r7bs73ev8hvg-a.oregon-postgres.render.com:5432/tutores";
    private static final String USER = "tutores_user";
    private static final String PASS = "GwBW1bNuEJgvtR7YMpHzEDAkebaEy6mx";

    static {
        try { Class.forName("org.postgresql.Driver"); }
        catch (ClassNotFoundException e) { throw new RuntimeException("Driver PostgreSQL no encontrado", e); }
    }

    public static Connection get() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
