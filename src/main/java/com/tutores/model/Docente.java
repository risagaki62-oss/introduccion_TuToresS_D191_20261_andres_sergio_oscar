package com.tutores.model;

public class Docente {
    private int id, usuarioId;
    private String titulo;
    private Usuario usuario;

    public Docente() {}
    public int getId()                { return id; }
    public void setId(int v)          { this.id = v; }
    public int getUsuarioId()         { return usuarioId; }
    public void setUsuarioId(int v)   { this.usuarioId = v; }
    public String getTitulo()         { return titulo; }
    public void setTitulo(String v)   { this.titulo = v; }
    public Usuario getUsuario()       { return usuario; }
    public void setUsuario(Usuario v) { this.usuario = v; }
}
