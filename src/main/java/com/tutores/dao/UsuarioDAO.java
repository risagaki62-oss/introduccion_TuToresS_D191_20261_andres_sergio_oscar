package com.tutores.dao;

import com.tutores.config.DBConnection;
import com.tutores.model.Usuario;

import java.sql.*;
import java.util.*;

public class UsuarioDAO {

    private static Usuario map(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setId(rs.getInt("id"));
        u.setNombre(rs.getString("nombre"));
        u.setApellido(rs.getString("apellido"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password"));
        u.setRol(rs.getString("rol"));
        u.setEstado(rs.getString("estado"));
        Timestamp ts = rs.getTimestamp("fecha_registro");
        if (ts != null) u.setFechaRegistro(ts.toLocalDateTime());
        return u;
    }

    public Usuario findByEmail(String email) throws SQLException {
        try (Connection c = DBConnection.get();
             PreparedStatement ps = c.prepareStatement("SELECT * FROM usuarios WHERE email = ?")) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? map(rs) : null;
        }
    }

    public Usuario findById(int id) throws SQLException {
        try (Connection c = DBConnection.get();
             PreparedStatement ps = c.prepareStatement("SELECT * FROM usuarios WHERE id = ?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? map(rs) : null;
        }
    }

    /** Inserta un usuario y devuelve su id generado. */
    public int insert(Usuario u) throws SQLException {
        String sql = "INSERT INTO usuarios (nombre, apellido, email, password, rol, estado) " +
                     "VALUES (?, ?, ?, ?, ?, ?) RETURNING id";
        try (Connection c = DBConnection.get();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getPassword());
            ps.setString(5, u.getRol());
            ps.setString(6, u.getEstado());
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : -1;
        }
    }

    public void updateEstado(int id, String estado) throws SQLException {
        try (Connection c = DBConnection.get();
             PreparedStatement ps = c.prepareStatement("UPDATE usuarios SET estado = ? WHERE id = ?")) {
            ps.setString(1, estado);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        try (Connection c = DBConnection.get();
             PreparedStatement ps = c.prepareStatement("DELETE FROM usuarios WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public List<Usuario> findByEstado(String estado) throws SQLException {
        List<Usuario> list = new ArrayList<>();
        try (Connection c = DBConnection.get();
             PreparedStatement ps = c.prepareStatement(
                "SELECT * FROM usuarios WHERE estado = ? ORDER BY fecha_registro DESC")) {
            ps.setString(1, estado);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    /** Todos los usuarios excepto los ADMIN, para gestión. */
    public List<Usuario> findAllExceptAdmin() throws SQLException {
        List<Usuario> list = new ArrayList<>();
        try (Connection c = DBConnection.get();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(
                "SELECT * FROM usuarios WHERE rol != 'ADMIN' ORDER BY rol, estado, nombre")) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public Map<String, Integer> countByRol() throws SQLException {
        Map<String, Integer> map = new LinkedHashMap<>();
        try (Connection c = DBConnection.get();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(
                "SELECT rol, COUNT(*) total FROM usuarios WHERE estado = 'APROBADO' GROUP BY rol ORDER BY rol")) {
            while (rs.next()) map.put(rs.getString("rol"), rs.getInt("total"));
        }
        return map;
    }

    public int countPendientes() throws SQLException {
        try (Connection c = DBConnection.get();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM usuarios WHERE estado = 'PENDIENTE'")) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }
}
