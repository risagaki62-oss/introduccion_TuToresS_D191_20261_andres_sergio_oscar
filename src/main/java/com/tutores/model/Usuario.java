package com.tutores.model;

import java.time.LocalDateTime;

public class Usuario {
    private int id;
    private String nombre, apellido, email, password, rol, estado;
    private LocalDateTime fechaRegistro;

    public Usuario() {}

    public int getId()                       { return id; }
    public void setId(int id)                { this.id = id; }
    public String getNombre()                { return nombre; }
    public void setNombre(String v)          { this.nombre = v; }
    public String getApellido()              { return apellido; }
    public void setApellido(String v)        { this.apellido = v; }
    public String getNombreCompleto()        { return nombre + " " + apellido; }
    public String getEmail()                 { return email; }
    public void setEmail(String v)           { this.email = v; }
    public String getPassword()              { return password; }
    public void setPassword(String v)        { this.password = v; }
    public String getRol()                   { return rol; }
    public void setRol(String v)             { this.rol = v; }
    public String getEstado()                { return estado; }
    public void setEstado(String v)          { this.estado = v; }
    public LocalDateTime getFechaRegistro()  { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime v) { this.fechaRegistro = v; }
}
