package com.tutores.dao;

import com.tutores.config.DBConnection;
import com.tutores.model.Materia;

import java.sql.*;
import java.util.*;

public class MateriaDAO {

    private static Materia map(ResultSet rs) throws SQLException {
        return new Materia(rs.getInt("id"), rs.getString("nombre"),
                           rs.getString("descripcion"), rs.getBoolean("activo"));
    }

    public List<Materia> findAll() throws SQLException {
        List<Materia> list = new ArrayList<>();
        try (Connection c = DBConnection.get();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM materias ORDER BY nombre")) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public List<Materia> findActivas() throws SQLException {
        List<Materia> list = new ArrayList<>();
        try (Connection c = DBConnection.get();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM materias WHERE activo = true ORDER BY nombre")) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public void insert(String nombre, String descripcion) throws SQLException {
        try (Connection c = DBConnection.get();
             PreparedStatement ps = c.prepareStatement(
                "INSERT INTO materias (nombre, descripcion) VALUES (?, ?)")) {
            ps.setString(1, nombre);
            ps.setString(2, descripcion);
            ps.executeUpdate();
        }
    }

    public void toggleActivo(int id) throws SQLException {
        try (Connection c = DBConnection.get();
             PreparedStatement ps = c.prepareStatement(
                "UPDATE materias SET activo = NOT activo WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public List<Object[]> topMaterias() throws SQLException {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT m.nombre, COUNT(t.id) AS total FROM materias m " +
                     "LEFT JOIN franja_horaria f ON f.materia_id = m.id " +
                     "LEFT JOIN tutorias t ON t.franja_id = f.id " +
                     "GROUP BY m.id, m.nombre ORDER BY total DESC LIMIT 10";
        try (Connection c = DBConnection.get();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(new Object[]{rs.getString("nombre"), rs.getInt("total")});
        }
        return list;
    }
}
