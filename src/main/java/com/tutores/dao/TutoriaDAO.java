package com.tutores.dao;

import com.tutores.config.DBConnection;
import com.tutores.model.*;

import java.sql.*;
import java.util.*;

public class TutoriaDAO {

    private static final String BASE =
        "SELECT t.id, t.franja_id, t.estudiante_id, t.descripcion, t.estado, t.fecha_reserva," +
        " f.docente_id, f.materia_id, f.dia_semana, f.hora_inicio, f.semestre, f.cubiculo, f.max_estudiantes," +
        " m.nombre AS mat_nombre," +
        " ud.nombre AS doc_nombre, ud.apellido AS doc_apellido, d.titulo AS doc_titulo," +
        " ue.nombre AS est_nombre, ue.apellido AS est_apellido, e.id AS est_id" +
        " FROM tutorias t" +
        " JOIN franja_horaria f ON f.id = t.franja_id" +
        " JOIN materias       m ON m.id = f.materia_id" +
        " JOIN docentes       d ON d.id = f.docente_id" +
        " JOIN usuarios      ud ON ud.id = d.usuario_id" +
        " JOIN estudiantes    e ON e.id = t.estudiante_id" +
        " JOIN usuarios      ue ON ue.id = e.usuario_id";

    private static Tutoria map(ResultSet rs) throws SQLException {
        Tutoria t = new Tutoria();
        t.setId(rs.getInt("id"));
        t.setFranjaId(rs.getInt("franja_id"));
        t.setEstudianteId(rs.getInt("estudiante_id"));
        t.setDescripcion(rs.getString("descripcion"));
        t.setEstado(rs.getString("estado"));
        Timestamp ts = rs.getTimestamp("fecha_reserva");
        if (ts != null) t.setFechaReserva(ts.toLocalDateTime());

        FranjaHoraria f = new FranjaHoraria();
        f.setId(rs.getInt("franja_id"));
        f.setDocenteId(rs.getInt("docente_id"));
        f.setMateriaId(rs.getInt("materia_id"));
        f.setDiaSemana(rs.getString("dia_semana"));
        f.setSemestre(rs.getString("semestre"));
        f.setCubiculo(rs.getInt("cubiculo"));
        f.setMaxEstudiantes(rs.getInt("max_estudiantes"));
        Time ti = rs.getTime("hora_inicio");
        if (ti != null) f.setHoraInicio(ti.toLocalTime());

        Materia m = new Materia(); m.setId(rs.getInt("materia_id")); m.setNombre(rs.getString("mat_nombre"));
        f.setMateria(m);

        Usuario ud = new Usuario(); ud.setNombre(rs.getString("doc_nombre")); ud.setApellido(rs.getString("doc_apellido"));
        Docente d = new Docente(); d.setId(rs.getInt("docente_id")); d.setTitulo(rs.getString("doc_titulo")); d.setUsuario(ud);
        f.setDocente(d);
        t.setFranja(f);

        Usuario ue = new Usuario(); ue.setNombre(rs.getString("est_nombre")); ue.setApellido(rs.getString("est_apellido"));
        Estudiante e = new Estudiante(); e.setId(rs.getInt("est_id")); e.setUsuario(ue);
        t.setEstudiante(e);

        return t;
    }

    private List<Tutoria> query(String sql, int param) throws SQLException {
        List<Tutoria> list = new ArrayList<>();
        try (Connection c = DBConnection.get(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, param);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public int insert(int franjaId, int estudianteId, String descripcion) throws SQLException {
        try (Connection c = DBConnection.get();
             PreparedStatement ps = c.prepareStatement(
                "INSERT INTO tutorias (franja_id, estudiante_id, descripcion) VALUES (?,?,?) RETURNING id")) {
            ps.setInt(1, franjaId); ps.setInt(2, estudianteId); ps.setString(3, descripcion);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : -1;
        }
    }

    public void updateEstado(int id, String estado) throws SQLException {
        try (Connection c = DBConnection.get();
             PreparedStatement ps = c.prepareStatement("UPDATE tutorias SET estado = ? WHERE id = ?")) {
            ps.setString(1, estado); ps.setInt(2, id); ps.executeUpdate();
        }
    }

    public Tutoria findById(int id) throws SQLException {
        try (Connection c = DBConnection.get();
             PreparedStatement ps = c.prepareStatement(BASE + " WHERE t.id = ?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? map(rs) : null;
        }
    }

    public List<Tutoria> findActivasByDocente(int docenteId) throws SQLException {
        return query(BASE + " WHERE f.docente_id = ? AND t.estado = 'ACTIVA' ORDER BY f.dia_semana, f.hora_inicio", docenteId);
    }
    public List<Tutoria> findHistorialByDocente(int docenteId) throws SQLException {
        return query(BASE + " WHERE f.docente_id = ? AND t.estado <> 'ACTIVA' ORDER BY t.fecha_reserva DESC", docenteId);
    }
    public List<Tutoria> findActivasByEstudiante(int estudianteId) throws SQLException {
        return query(BASE + " WHERE t.estudiante_id = ? AND t.estado = 'ACTIVA' ORDER BY f.dia_semana, f.hora_inicio", estudianteId);
    }
    public List<Tutoria> findHistorialByEstudiante(int estudianteId) throws SQLException {
        return query(BASE + " WHERE t.estudiante_id = ? AND t.estado <> 'ACTIVA' ORDER BY t.fecha_reserva DESC", estudianteId);
    }

    public boolean yaInscrito(int franjaId, int estudianteId) throws SQLException {
        try (Connection c = DBConnection.get();
             PreparedStatement ps = c.prepareStatement(
                "SELECT id FROM tutorias WHERE franja_id = ? AND estudiante_id = ? AND estado = 'ACTIVA'")) {
            ps.setInt(1, franjaId); ps.setInt(2, estudianteId);
            return ps.executeQuery().next();
        }
    }

    public Map<String, Integer> countByEstado() throws SQLException {
        Map<String, Integer> map = new LinkedHashMap<>();
        try (Connection c = DBConnection.get(); Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("SELECT estado, COUNT(*) total FROM tutorias GROUP BY estado ORDER BY estado")) {
            while (rs.next()) map.put(rs.getString("estado"), rs.getInt("total"));
        }
        return map;
    }

    public List<Object[]> topTutores() throws SQLException {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT u.nombre||' '||u.apellido AS nombre, COUNT(t.id) AS total " +
            "FROM docentes d JOIN usuarios u ON u.id=d.usuario_id " +
            "LEFT JOIN franja_horaria f ON f.docente_id=d.id " +
            "LEFT JOIN tutorias t ON t.franja_id=f.id " +
            "GROUP BY d.id, u.nombre, u.apellido ORDER BY total DESC LIMIT 10";
        try (Connection c = DBConnection.get(); Statement st = c.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(new Object[]{rs.getString("nombre"), rs.getInt("total")});
        }
        return list;
    }
}
