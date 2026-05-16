package com.cayetano.biblioteca.dto;

public class LibroCreateDTO {
    public String titulo;
    public Integer anioPublicacion;
    public String autorNombre;
    public Long estadoId;
    public Integer valoracion;
    public String descripcion;
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public Integer getAnioPublicacion() {
        return anioPublicacion;
    }
    public void setAnioPublicacion(Integer anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }
    public String getAutorNombre() {
        return autorNombre;
    }
    public void setAutorNombre(String autorNombre) {
        this.autorNombre = autorNombre;
    }
    public Long getEstadoId() {
        return estadoId;
    }
    public void setEstadoId(Long estadoId) {
        this.estadoId = estadoId;
    }
    public Integer getValoracion() {
        return valoracion;
    }
    public void setValoracion(Integer valoracion) {
        this.valoracion = valoracion;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}

