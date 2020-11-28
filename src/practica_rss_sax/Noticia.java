/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica_rss_sax;

import java.util.List;

/**
 *
 * @author Javier
 */
public class Noticia {

    private String titulo;
    private String url;
    private String fechaPub;
    private String descripcion;
    private List<String> categorias;

    public String getUrl() {
        return url;
    }

    public void setUrl(String u) {
        this.url = u;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<String> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<String> categorias) {
        this.categorias = categorias;
    }

    public void addCategoria(String categoria) {
        this.categorias.add(categoria);
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaPub() {
        return fechaPub;
    }

    public void setFechaPub(String fechaPub) {
        this.fechaPub = fechaPub;
    }

}
