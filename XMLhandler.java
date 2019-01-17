import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class XMLhandler extends Thread  {
    public static void saveUrl(final String filename, final String urlString) throws IOException {
        /*receives a filename and a URL then copies it locally with the file name chosen*/

        BufferedInputStream in = null;
        FileOutputStream fout = null;
        try {
            in = new BufferedInputStream(new URL(urlString).openStream());
            fout = new FileOutputStream(filename);

            final byte data[] = new byte[1024];
            int count;
            while ((count = in.read(data, 0, 1024)) != -1) {
                fout.write(data, 0, count);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (fout != null) {
                fout.close();
            }
        }
    }
    public static Map<String,Double> parseXML(final String filename){
        //Parse's the XML and returns a dictionary with the relevant info from it I.E the Currency name and Covert value
        Map<String,Double> currencies = new HashMap<>();
        try {
            File fXmlFile = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            //optional, but recommended
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("CURRENCY");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                Element eElement = (Element) nNode;

                currencies.put(eElement.getElementsByTagName("CURRENCYCODE").item(0).getTextContent(), new Double(eElement.getElementsByTagName("RATE").item(0).getTextContent()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currencies;
    }
}

