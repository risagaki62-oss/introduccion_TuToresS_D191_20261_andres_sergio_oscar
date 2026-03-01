package com.tutores.model;

public class Estudiante {
    private int id, usuarioId;
    private Usuario usuario;

    public Estudiante() {}
    public int getId()                { return id; }
    public void setId(int v)          { this.id = v; }
    public int getUsuarioId()         { return usuarioId; }
    public void setUsuarioId(int v)   { this.usuarioId = v; }
    public Usuario getUsuario()       { return usuario; }
    public void setUsuario(Usuario v) { this.usuario = v; }
}
