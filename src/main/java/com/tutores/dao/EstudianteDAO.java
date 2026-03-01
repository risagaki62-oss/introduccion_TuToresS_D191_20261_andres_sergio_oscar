package com.tutores.dao;

import com.tutores.config.DBConnection;
import com.tutores.model.Estudiante;

import java.sql.*;

public class EstudianteDAO {

    public static Estudiante map(ResultSet rs) throws SQLException {
        Estudiante e = new Estudiante();
        e.setId(rs.getInt("id"));
        e.setUsuarioId(rs.getInt("usuario_id"));
        return e;
    }

    public Estudiante findByUsuarioId(int uid) throws SQLException {
        try (Connection c = DBConnection.get();
             PreparedStatement ps = c.prepareStatement("SELECT * FROM estudiantes WHERE usuario_id = ?")) {
            ps.setInt(1, uid);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? map(rs) : null;
        }
    }

    public int insert(int usuarioId) throws SQLException {
        try (Connection c = DBConnection.get();
             PreparedStatement ps = c.prepareStatement(
                "INSERT INTO estudiantes (usuario_id) VALUES (?) RETURNING id")) {
            ps.setInt(1, usuarioId);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : -1;
        }
    }
}
