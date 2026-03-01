package com.tutores.model;

public class Materia {
    private int id;
    private String nombre, descripcion;
    private boolean activo;

    public Materia() {}
    public Materia(int id, String nombre, String descripcion, boolean activo) {
        this.id = id; this.nombre = nombre;
        this.descripcion = descripcion; this.activo = activo;
    }
    public int getId()                  { return id; }
    public void setId(int v)            { this.id = v; }
    public String getNombre()           { return nombre; }
    public void setNombre(String v)     { this.nombre = v; }
    public String getDescripcion()      { return descripcion; }
    public void setDescripcion(String v){ this.descripcion = v; }
    public boolean isActivo()           { return activo; }
    public void setActivo(boolean v)    { this.activo = v; }
}
