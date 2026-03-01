package com.tutores.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class FranjaHoraria {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("HH:mm");

    private int id, docenteId, materiaId, cubiculo, maxEstudiantes, inscritos, cuposLibres;
    private String diaSemana, semestre, disponibilidad;
    private LocalTime horaInicio;
    private Docente docente;
    private Materia materia;

    public FranjaHoraria() {}

    public int getId()                       { return id; }
    public void setId(int v)                 { this.id = v; }
    public int getDocenteId()                { return docenteId; }
    public void setDocenteId(int v)          { this.docenteId = v; }
    public int getMateriaId()                { return materiaId; }
    public void setMateriaId(int v)          { this.materiaId = v; }
    public int getCubiculo()                 { return cubiculo; }
    public void setCubiculo(int v)           { this.cubiculo = v; }
    public int getMaxEstudiantes()           { return maxEstudiantes; }
    public void setMaxEstudiantes(int v)     { this.maxEstudiantes = v; }
    public int getInscritos()                { return inscritos; }
    public void setInscritos(int v)          { this.inscritos = v; }
    public int getCuposLibres()              { return cuposLibres; }
    public void setCuposLibres(int v)        { this.cuposLibres = v; }
    public String getDiaSemana()             { return diaSemana; }
    public void setDiaSemana(String v)       { this.diaSemana = v; }
    public String getSemestre()              { return semestre; }
    public void setSemestre(String v)        { this.semestre = v; }
    public String getDisponibilidad()        { return disponibilidad; }
    public void setDisponibilidad(String v)  { this.disponibilidad = v; }
    public LocalTime getHoraInicio()         { return horaInicio; }
    public void setHoraInicio(LocalTime v)   { this.horaInicio = v; }
    public Docente getDocente()              { return docente; }
    public void setDocente(Docente v)        { this.docente = v; }
    public Materia getMateria()              { return materia; }
    public void setMateria(Materia v)        { this.materia = v; }

    /** Hora inicio formateada HH:mm */
    public String getHoraInicioStr() {
        return horaInicio != null ? horaInicio.format(FMT) : "";
    }
    /** Hora fin formateada HH:mm (inicio + 44 min) */
    public String getHoraFinStr() {
        return horaInicio != null ? horaInicio.plusMinutes(44).format(FMT) : "";
    }
    public boolean isDisponible() {
        return "DISPONIBLE".equals(disponibilidad);
    }
}
