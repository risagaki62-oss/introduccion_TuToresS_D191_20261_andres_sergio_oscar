package com.tutores.dao;

import com.tutores.config.DBConnection;
import com.tutores.model.*;

import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FranjaHorariaDAO {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("HH:mm");

    private static FranjaHoraria mapView(ResultSet rs) throws SQLException {
        FranjaHoraria f = new FranjaHoraria();
        f.setId(rs.getInt("id"));
        f.setDocenteId(rs.getInt("docente_id"));
        f.setMateriaId(rs.getInt("materia_id"));
        f.setDiaSemana(rs.getString("dia_semana"));
        f.setSemestre(rs.getString("semestre"));
        f.setCubiculo(rs.getInt("cubiculo"));
        f.setMaxEstudiantes(rs.getInt("max_estudiantes"));
        f.setInscritos(rs.getInt("inscritos"));
        f.setCuposLibres(rs.getInt("cupos_libres"));
        f.setDisponibilidad(rs.getString("disponibilidad"));
        Time t = rs.getTime("hora_inicio");
        if (t != null) f.setHoraInicio(t.toLocalTime());

        Materia m = new Materia();
        m.setId(rs.getInt("materia_id"));
        m.setNombre(rs.getString("materia_nombre"));
        f.setMateria(m);

        Usuario u = new Usuario();
        u.setNombre(rs.getString("docente_nombre"));
        u.setApellido(rs.getString("docente_apellido"));
        Docente d = new Docente();
        d.setId(rs.getInt("docente_id"));
        d.setTitulo(rs.getString("docente_titulo"));
        d.setUsuario(u);
        f.setDocente(d);

        return f;
    }

    public int insert(int docenteId, int materiaId, String dia, String hora,
                      String semestre, int cubiculo, int maxEst) throws SQLException {
        String sql = "INSERT INTO franja_horaria " +
            "(docente_id, materia_id, dia_semana, hora_inicio, semestre, cubiculo, max_estudiantes) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id";
        try (Connection c = DBConnection.get();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, docenteId);
            ps.setInt(2, materiaId);
            ps.setString(3, dia);
            ps.setTime(4, Time.valueOf(LocalTime.parse(hora, FMT)));
            ps.setString(5, semestre);
            ps.setInt(6, cubiculo);
            ps.setInt(7, maxEst);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : -1;
        }
    }

    public void delete(int id) throws SQLException {
        try (Connection c = DBConnection.get();
             PreparedStatement ps = c.prepareStatement("DELETE FROM franja_horaria WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public List<FranjaHoraria> findByDocente(int docenteId, String semestre) throws SQLException {
        List<FranjaHoraria> list = new ArrayList<>();
        try (Connection c = DBConnection.get();
             PreparedStatement ps = c.prepareStatement(
                "SELECT * FROM v_franja_disp WHERE docente_id = ? AND semestre = ? " +
                "ORDER BY dia_semana, hora_inicio")) {
            ps.setInt(1, docenteId);
            ps.setString(2, semestre);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapView(rs));
        }
        return list;
    }

    public List<FranjaHoraria> findDisponiblesByMateria(int materiaId, String semestre) throws SQLException {
        List<FranjaHoraria> list = new ArrayList<>();
        try (Connection c = DBConnection.get();
             PreparedStatement ps = c.prepareStatement(
                "SELECT * FROM v_franja_disp WHERE materia_id = ? AND semestre = ? " +
                "AND disponibilidad = 'DISPONIBLE' ORDER BY dia_semana, hora_inicio")) {
            ps.setInt(1, materiaId);
            ps.setString(2, semestre);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapView(rs));
        }
        return list;
    }

    public FranjaHoraria findById(int id) throws SQLException {
        try (Connection c = DBConnection.get();
             PreparedStatement ps = c.prepareStatement("SELECT * FROM v_franja_disp WHERE id = ?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? mapView(rs) : null;
        }
    }

    public boolean cubiculoOcupado(String dia, String hora, String semestre,
                                    int cubiculo, int excludeId) throws SQLException {
        try (Connection c = DBConnection.get();
             PreparedStatement ps = c.prepareStatement(
                "SELECT id FROM franja_horaria WHERE dia_semana = ? AND hora_inicio = ? " +
                "AND semestre = ? AND cubiculo = ? AND id != ?")) {
            ps.setString(1, dia);
            ps.setTime(2, Time.valueOf(LocalTime.parse(hora, FMT)));
            ps.setString(3, semestre);
            ps.setInt(4, cubiculo);
            ps.setInt(5, excludeId);
            return ps.executeQuery().next();
        }
    }

    public boolean tieneReservasActivas(int franjaId) throws SQLException {
        try (Connection c = DBConnection.get();
             PreparedStatement ps = c.prepareStatement(
                "SELECT COUNT(*) FROM tutorias WHERE franja_id = ? AND estado = 'ACTIVA'")) {
            ps.setInt(1, franjaId);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    public List<Integer> getCubiculosOcupados(String dia, String hora, String semestre) throws SQLException {
        List<Integer> list = new ArrayList<>();
        try (Connection c = DBConnection.get();
             PreparedStatement ps = c.prepareStatement(
                "SELECT cubiculo FROM franja_horaria WHERE dia_semana = ? AND hora_inicio = ? AND semestre = ?")) {
            ps.setString(1, dia);
            ps.setTime(2, Time.valueOf(LocalTime.parse(hora, FMT)));
            ps.setString(3, semestre);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(rs.getInt("cubiculo"));
        }
        return list;
    }
}
