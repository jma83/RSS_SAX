/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica_rss_sax;

/**
 *
 * @author Javier Martinez Arias
 */
public class CanalNoticias {

    private String titulo;
    private String url;
    private String descripcion;

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
