import javax.swing.*;


import java.io.IOException;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class Main {

    public static void main(String argv[]) {
        try {
            //first get the XML file
            XMLhandler.downloadXML("Currncies.xml", "https://www.boi.org.il/currency.xml");
        }
        catch (IOException e){
            System.out.println("Error in IO");
            System.exit(2);
        }
        //create the thread to handle refreshing it
        Thread t = new XMLhandler();
        t.start();
        //create the App
        SwingUtilities.invokeLater(() -> {
            ConvertingApp frame = new ConvertingApp();
            frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
            frame.setTitle("App");
            frame.setSize(600,900);
            frame.setLocation(150, 150);
            frame.setVisible(true);
        });

    }

}