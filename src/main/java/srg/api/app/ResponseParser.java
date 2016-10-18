package srg.api.app;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey on 13.10.2016.
 */
public class ResponseParser {

//    public static List<Album> parseAlbumsFromAlbumsLink(String xml) throws ResponseParsingException {
//        List<Album> albumLists = new ArrayList<>();
//
//        try {
//            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//
//            Document xmldoc = dBuilder.parse(new InputSource(new StringReader(xml)));
//            xmldoc.getDocumentElement().normalize();
//            NodeList list = xmldoc.getElementsByTagName("entry");
//            for (int i = 0; i < list.getLength(); i++) {
//                Album album = new Album();
//                Element e = (Element) list.item(i);
//
//                album.setId(e.getElementsByTagName("id").item(0).getTextContent());
//                album.setTitle(e.getElementsByTagName("title").item(0).getTextContent());
//
//                Element imgCount = (Element) e.getElementsByTagName("f:image-count").item(0);
//                album.setImageCount(Integer.parseInt(imgCount.getAttribute("value")));
//
//                NodeList links = e.getElementsByTagName("link");
//                for (int j = 0; j < links.getLength(); j++) {
//                    Element linkElem = (Element) links.item(j);
//                    if (linkElem.getAttribute("rel").equals("self")) {
//                        album.setUrl(linkElem.getAttribute("href"));
//                    } else if (linkElem.getAttribute("rel").equals("photos")) {
//                        album.setUrlToPhotos(linkElem.getAttribute("href"));
//                    }
//                }
//                albumLists.add(album);
//            }
//        } catch (IOException | ParserConfigurationException | SAXException e) {
//            throw new ResponseParsingException("Exception while parsing albums ", e);
//        }
//        return albumLists;
//    }

    public static String parseAlbumsLinkFromServiceDoc(String xml) throws ResponseParsingException {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document xmldoc = dBuilder.parse(new InputSource(new StringReader(xml)));
            xmldoc.getDocumentElement().normalize();
            NodeList list = xmldoc.getElementsByTagName("app:collection");

            for (int i = 0; i < list.getLength(); i++) {
                Element e = (Element) list.item(i);
                String id = e.getAttribute("id");
                if (id.equals("album-list")) {
                    return e.getAttribute("href");
                }
            }
        } catch (IOException | ParserConfigurationException | SAXException e) {
            throw new ResponseParsingException("Exception while parsing album link", e);
        }
        return "";
    }
}
