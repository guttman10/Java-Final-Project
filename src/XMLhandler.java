import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class XMLhandler extends Thread  {
    private Object valArr[] = new Object[14];

    public static void downloadXML(final String filename, final String urlString) throws IOException {
        //receives a filename and a URL then copies it locally with the file name chosen

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
    static Map<String,Double> parseXML(){
        //Parse's the XML and returns a dictionary with the relevant info from it I.E the Currency name and Covert value
        Map<String,Double> rates = new HashMap<>();
        try {
            File fXmlFile = new File("Currncies.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            //optional, but recommended
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("CURRENCY");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                Element eElement = (Element) nNode;

                rates.put(eElement.getElementsByTagName("CURRENCYCODE").item(0).getTextContent(), new Double(eElement.getElementsByTagName("RATE").item(0).getTextContent()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rates;
    }
    void getUpdatedValues(){
        Map<String,Double> rates=XMLhandler.parseXML();
        int i=0;
        for(Map.Entry<String, Double> entry : rates.entrySet()) {
            Double value = entry.getValue();
            valArr[i]=value;
            i++;
        }
    }
    @Override
    public void run() {
        //handels the xml refreshing
        while(true){
            try {
                Thread.sleep(300000);//wait 3 minute before refreshing, then repeat
                downloadXML("Currncies.xml", "https://www.boi.org.il/currency.xml");
                getUpdatedValues();
                ConvertingApp.updateTable(valArr);
            }
            catch (MalformedURLException e){
                System.out.println("Error in Url");
                System.exit(1);
            }
            catch (IOException e){
                System.out.println("Error in IO");
                System.exit(2);
            }
            catch (Exception e){
                System.out.println("Unknown Error");
                e.printStackTrace();
                System.exit(-1);
            }

        }
    }


}

