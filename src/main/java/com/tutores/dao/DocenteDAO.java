package com.tutores.dao;

import com.tutores.config.DBConnection;
import com.tutores.model.Docente;

import java.sql.*;

public class DocenteDAO {

    public static Docente map(ResultSet rs) throws SQLException {
        Docente d = new Docente();
        d.setId(rs.getInt("id"));
        d.setUsuarioId(rs.getInt("usuario_id"));
        d.setTitulo(rs.getString("titulo"));
        return d;
    }

    public Docente findByUsuarioId(int uid) throws SQLException {
        try (Connection c = DBConnection.get();
             PreparedStatement ps = c.prepareStatement("SELECT * FROM docentes WHERE usuario_id = ?")) {
            ps.setInt(1, uid);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? map(rs) : null;
        }
    }

    public int insert(int usuarioId, String titulo) throws SQLException {
        try (Connection c = DBConnection.get();
             PreparedStatement ps = c.prepareStatement(
                "INSERT INTO docentes (usuario_id, titulo) VALUES (?, ?) RETURNING id")) {
            ps.setInt(1, usuarioId);
            ps.setString(2, titulo);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : -1;
        }
    }
}
