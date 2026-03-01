package com.tutores.config;

import com.tutores.util.PasswordUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Se ejecuta al arrancar Tomcat.
 * Crea el usuario admin@admin con contraseña "admin" si no existe.
 * También verifica que la estructura de BD esté disponible.
 */
public class DatabaseInitializer implements ServletContextListener {

    private static final Logger LOG = Logger.getLogger(DatabaseInitializer.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOG.info("TUToreS: Inicializando base de datos...");
        try {
            ensureAdminExists();
            LOG.info("TUToreS: Base de datos lista.");
        } catch (Exception e) {
            LOG.log(Level.SEVERE,
                "TUToreS ERROR: No se pudo inicializar la BD. " +
                "Verifica que PostgreSQL esté corriendo en localhost:5432 con la base de datos 'TUToreS'.", e);
        }
    }

    private void ensureAdminExists() throws SQLException {
        String checkSql  = "SELECT id FROM usuarios WHERE email = 'admin@admin'";
        String insertSql = "INSERT INTO usuarios (nombre, apellido, email, password_hash, rol, estado) " +
                           "VALUES ('Administrador', 'UTS', 'admin@admin', ?, 'ADMIN', 'APROBADO') " +
                           "ON CONFLICT (email) DO NOTHING";

        try (Connection conn = DBConnection.get()) {
            // Verificar si el admin ya existe
            try (Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery(checkSql)) {
                if (rs.next()) {
                    LOG.info("TUToreS: Admin ya existe, omitiendo creación.");
                    return;
                }
            }
            // Crear admin con BCrypt en caliente (hash REAL, no hardcodeado)
            String hash = PasswordUtil.hash("admin");
            try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                ps.setString(1, hash);
                ps.executeUpdate();
                LOG.info("TUToreS: Usuario admin@admin creado con contraseña 'admin'.");
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Nada que limpiar
    }
}
