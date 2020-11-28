/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica_rss_sax;

import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Javier Martinez Arias
 */
public class CanalNoticiasHandler extends DefaultHandler {

    private static final String ELEMENT_ITEM = "item";
    private static final String ELEMENT_TITULO = "title";
    private static final String ELEMENT_URL = "link";
    private static final String ELEMENT_DESCRIPCION = "description";
    private static final String ELEMENT_CANAL = "channel";
    
    private NoticiaHandler noticiaHandler;
    private List<Noticia> noticias;
    private CanalNoticias canal;
    private StringBuilder buffer = new StringBuilder();

    private XMLReader xmlReader;

    public CanalNoticiasHandler(XMLReader xmlReader) {

        this.xmlReader = xmlReader;
        noticiaHandler = new NoticiaHandler(this);
    }

    @Override
    public void startElement(String uri,
            String localName,
            String qName,
            Attributes att) throws SAXException {
        switch (qName) {

            case ELEMENT_ITEM:
                xmlReader.setContentHandler(noticiaHandler);
                break;

            case ELEMENT_CANAL:
                canal = new CanalNoticias();
                canal.setTitulo(new String());
                canal.setDescripcion(new String());
                canal.setUrl(new String());
                break;
            case ELEMENT_TITULO:
            case ELEMENT_URL:
            case ELEMENT_DESCRIPCION:
                buffer.delete(0, buffer.length());
                break;

        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        buffer.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case ELEMENT_ITEM:
                this.noticias = (List<Noticia>) noticiaHandler.getNoticias();
                break;
            case ELEMENT_TITULO:
                this.canal.setTitulo(buffer.toString());
                break;
            case ELEMENT_URL:
                this.canal.setUrl(buffer.toString());
                break;
            case ELEMENT_DESCRIPCION:
                this.canal.setDescripcion(buffer.toString());
                break;
        }

    }

    public void restore() {
        this.xmlReader.setContentHandler(this);
    }

    public List<Noticia> getNoticias() {
        return noticias;
    }

    public CanalNoticias getCanal() {
        return canal;
    }

    public void error(SAXParseException e) throws SAXException {
        e.printStackTrace();
    }
}
