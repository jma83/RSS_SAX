/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica_rss_sax;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 *
 * @author Javier Martinez Arias
 */
public class XMLManager {

    private CanalNoticiasHandler canalNoticiasHandler = null;
    private String ficheroOut = null;
    
    private static final String ELEMENT_NOTICIAS = "noticias";
    private static final String ELEMENT_NOTICIA = "noticia";
    private static final String ELEMENT_CANAL = "canal";

    public void leerFichero(String urlString) {
        try {
            URL urlXML = new URL(urlString);
            URLConnection conn = urlXML.openConnection();
            InputStream xmlFile = conn.getInputStream();

            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser;
            saxParser = saxParserFactory.newSAXParser();

            canalNoticiasHandler = new CanalNoticiasHandler(saxParser.getXMLReader());
            saxParser.parse(xmlFile, canalNoticiasHandler);
            String salida = this.cleanString(canalNoticiasHandler.getCanal().getTitulo());
            ficheroOut = ELEMENT_NOTICIAS + "_" + salida;
        } catch (MalformedURLException ex) {
            System.err.println("Error: " + ex.getMessage());
            ex.getStackTrace();
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            System.err.println("Error: " + ex.getMessage());
            ex.getStackTrace();
        }
    }

    private String cleanString(String str) {
        String result = str.replaceAll("[^a-zA-Z0-9]", "");
        result = result.replaceAll("\\s", "");
        return result;
    }

    public void mostrarInfoPrincipal() {
        if (canalNoticiasHandler != null) {
            System.out.println("**INFORMACIÓN GENERAL**");
            System.out.println("Titulo: " + canalNoticiasHandler.getCanal().getTitulo());
            System.out.println("Descripcion: " + canalNoticiasHandler.getCanal().getDescripcion());
            System.out.println("URL: " + canalNoticiasHandler.getCanal().getUrl());
            System.out.println();
        }
    }

    public void mostrarNoticias() {
        System.out.println("**NOTICIAS**");
        for (int i = 0; i < canalNoticiasHandler.getNoticias().size(); i++) {
            String titulo = canalNoticiasHandler.getNoticias().get(i).getTitulo();
            String descripcion = canalNoticiasHandler.getNoticias().get(i).getDescripcion();
            String urlNoticia = canalNoticiasHandler.getNoticias().get(i).getUrl();
            String fechaPub = canalNoticiasHandler.getNoticias().get(i).getFechaPub();
            List<String> categorias = canalNoticiasHandler.getNoticias().get(i).getCategorias();
            
            System.out.println("Noticia nº: " + (i + 1));
            System.out.println("Titulo: " + titulo);
            System.out.println("Descripcion: " + descripcion);
            System.out.println("URL: " + urlNoticia);
            System.out.println("Fecha Publicación: " + fechaPub);

            System.out.print("Categorías: ");
            for (int j = 0; j < categorias.size(); j++) {
                System.out.print(categorias.get(j));
                if (j != categorias.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("\n");
        }
    }

    public void crearDocXML() {

        FileWriter fileWriter = null;
        try {
            SAXTransformerFactory saxTransformerFactory = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
            TransformerHandler transformerHandler = saxTransformerFactory.newTransformerHandler();
            Transformer transformer = transformerHandler.getTransformer();
            transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            
            File f = new File(ficheroOut + ".xml");
            fileWriter = new FileWriter(f);
            Result result = new StreamResult(fileWriter);
            transformerHandler.setResult(result);
            
            generarFicheroSalida(transformerHandler);
            
            fileWriter.flush();
            fileWriter.close();
        } catch (TransformerConfigurationException | IOException ex) {
            System.err.println("Error: " + ex.getMessage());
            ex.getStackTrace();
        }

    }

    private void generarFicheroSalida(TransformerHandler transformerHandler) {
        AttributesImpl atts = new AttributesImpl();
        String canal = canalNoticiasHandler.getCanal().getTitulo();

        try {
            transformerHandler.startDocument();

            atts.addAttribute("", "", ELEMENT_CANAL, "String", canal);
            transformerHandler.startElement("", "", ELEMENT_NOTICIAS, atts);
            atts.clear();

            for (int i = 0; i < canalNoticiasHandler.getNoticias().size(); i++) {

                transformerHandler.startElement("", "", ELEMENT_NOTICIA, atts);
                String caracteres = canalNoticiasHandler.getNoticias().get(i).getTitulo();
                transformerHandler.characters(caracteres.toCharArray(), 0, caracteres.length());
                transformerHandler.endElement("", "", ELEMENT_NOTICIA);
            }

            transformerHandler.endElement("", "", ELEMENT_NOTICIAS);
            transformerHandler.endDocument();
        } catch (SAXException ex) {
            System.err.println("Error: " + ex.getMessage());
            ex.getStackTrace();
        }
    }
}
