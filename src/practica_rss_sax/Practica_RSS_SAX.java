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
public class Practica_RSS_SAX {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){

        String[] urlEntrada = {"https://e00-elmundo.uecdn.es/elmundo/rss/portada.xml",
            "https://rss.nytimes.com/services/xml/rss/nyt/World.xml"};
        
        run(urlEntrada[0]);
    }

    public static void run(String urlString){
        XMLManager xManager =  new XMLManager();
        
        xManager.leerFichero(urlString);
        xManager.mostrarInfoPrincipal();
        xManager.mostrarNoticias();
        xManager.crearDocXML();
    }
}
