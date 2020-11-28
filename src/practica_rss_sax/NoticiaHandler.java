/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica_rss_sax;

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Javier Martinez Arias
 */
public class NoticiaHandler extends DefaultHandler {

    private static final String ELEMENT_ITEM = "item";
    private static final String ELEMENT_TITULO = "title";
    private static final String ELEMENT_URL = "link";
    private static final String ELEMENT_DESCRIPCION = "description";
    private static final String ELEMENT_FECHA_PUB = "pubDate";
    private static final String ELEMENT_CATEGORIA = "category";

    private Noticia currentNoticia;
    private StringBuilder buffer = new StringBuilder();
    private CanalNoticiasHandler canalNoticiasHandler;
    private List<Noticia> noticias = new ArrayList<>();

    public NoticiaHandler(CanalNoticiasHandler canalNoticiasHandler) {

        this.canalNoticiasHandler = canalNoticiasHandler;

    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes att) {
        
        if ("".equals(localName))
            localName = qName;
        switch (localName) {

            case ELEMENT_TITULO:
                currentNoticia = new Noticia();
                currentNoticia.setTitulo(new String());
                currentNoticia.setDescripcion(new String());
                currentNoticia.setUrl(new String());
                currentNoticia.setFechaPub(new String());
                currentNoticia.setCategorias(new ArrayList<String>());
            case ELEMENT_URL:
            case ELEMENT_DESCRIPCION:
            case ELEMENT_FECHA_PUB:
            case ELEMENT_CATEGORIA:
                buffer.delete(0, buffer.length());
                break;
        }

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        buffer.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        
        if ("".equals(localName))
            localName = qName;
        switch (localName) {
            case ELEMENT_ITEM:
                noticias.add(currentNoticia);
                canalNoticiasHandler.endElement(uri, localName, qName);
                canalNoticiasHandler.restore();
                break;
            case ELEMENT_TITULO:
                currentNoticia.setTitulo(buffer.toString());
                break;
            case ELEMENT_URL:
                currentNoticia.setUrl(buffer.toString());
                break;
            case ELEMENT_DESCRIPCION:
                currentNoticia.setDescripcion(buffer.toString());
                break;
            case ELEMENT_FECHA_PUB:
                currentNoticia.setFechaPub(buffer.toString());
                break;
            case ELEMENT_CATEGORIA:
                currentNoticia.addCategoria(buffer.toString());
                break;
        }
    }

    public List<Noticia> getNoticias() {
        return noticias;
    }
}
