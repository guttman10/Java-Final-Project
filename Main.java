import java.util.HashMap;
import java.util.Map;

public class Main {


    public static void main(String argv[]) {
        //just shows you can download and parse the xml
        Map<String, Double> currencies = new HashMap<>();
        try {
            XMLhandler.saveUrl("c.xml", "https://www.boi.org.il/currency.xml");
            currencies=XMLhandler.parseXML("c.xml");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        for(Map.Entry<String, Double> entry : currencies.entrySet()) {
            String key = entry.getKey();
            Double value = entry.getValue();
            System.out.println(key+" "+value);
        }
    }

}