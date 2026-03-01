package com.tutores.model;

import java.time.LocalDateTime;

public class Tutoria {
    private int id, franjaId, estudianteId;
    private String descripcion, estado;
    private LocalDateTime fechaReserva;
    private FranjaHoraria franja;
    private Estudiante estudiante;

    public Tutoria() {}
    public int getId()                         { return id; }
    public void setId(int v)                   { this.id = v; }
    public int getFranjaId()                   { return franjaId; }
    public void setFranjaId(int v)             { this.franjaId = v; }
    public int getEstudianteId()               { return estudianteId; }
    public void setEstudianteId(int v)         { this.estudianteId = v; }
    public String getDescripcion()             { return descripcion; }
    public void setDescripcion(String v)       { this.descripcion = v; }
    public String getEstado()                  { return estado; }
    public void setEstado(String v)            { this.estado = v; }
    public LocalDateTime getFechaReserva()     { return fechaReserva; }
    public void setFechaReserva(LocalDateTime v){ this.fechaReserva = v; }
    public FranjaHoraria getFranja()           { return franja; }
    public void setFranja(FranjaHoraria v)     { this.franja = v; }
    public Estudiante getEstudiante()          { return estudiante; }
    public void setEstudiante(Estudiante v)    { this.estudiante = v; }
}
