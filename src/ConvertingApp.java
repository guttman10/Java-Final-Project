import javax.swing.*;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.table.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.*;
public class ConvertingApp extends JFrame {

    private static final long serialVersionUID = 1L;
    private static JTable table;
    Map<String, Double> rates;
    String cExchangeSource, cExchangeTarget;

    Object[][] data = new Object[14][2]; //number of currencies doesn't change
    Object[] columnNames ={"Currency","ILS value"};

    //amount formats
    private NumberFormat amountFormat;
    private JFormattedTextField amountField;
    private double amount;

    //buttons
    private JRadioButton[] jRadioBtnSet1 = new JRadioButton[14];
    private JRadioButton[] jRadioBtnSet2 = new JRadioButton[14];
    private ButtonGroup g1 = new ButtonGroup();
    private ButtonGroup g2 = new ButtonGroup();
    private JButton exchangeBtn;

    // Declaration of object of  JLabel  class.
    private JLabel sourceLabel, targetLabel, amountLabel, resultLabel;

    public ConvertingApp() {
        int i = 0;
        //add labels
        setLabels();
        try {
            rates=XMLhandler.parseXML();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        for(Map.Entry<String, Double> entry : rates.entrySet()) {
            //sets the  jtable info also places and sets the buttons
            String key = entry.getKey();
            Double value = entry.getValue();
            //for jtable data
            data[i][0]=key;
            data[i][1]=value;

            //create buttons set bounds text and add event listener
            jRadioBtnSet1[i] = new JRadioButton();
            jRadioBtnSet1[i].setText(key);
            jRadioBtnSet1[i].setBounds(100, 300+i*20, 50, 20);
            jRadioBtnSet1[i].addActionListener(e -> cExchangeSource = ((JRadioButton)e.getSource()).getText());

            jRadioBtnSet2[i] = new JRadioButton();
            jRadioBtnSet2[i].setText(key);
            jRadioBtnSet2[i].setBounds(400, 300+i*20, 50, 20);
            jRadioBtnSet2[i].addActionListener(e -> cExchangeTarget = ((JRadioButton)e.getSource()).getText());

            //group them
            g1.add(jRadioBtnSet1[i]);
            g2.add(jRadioBtnSet2[i]);
            //draw them
            this.add(jRadioBtnSet1[i]);
            this.add(jRadioBtnSet2[i]);
            i++;
        }
        //create and add the input field limit it to "double"
        amountFormat = NumberFormat.getNumberInstance();
        amountField = new JFormattedTextField(amountFormat);
        amountField.setValue(amount);
        amountField.setColumns(10);
        amountField.addPropertyChangeListener("value", evt -> {
            Object source = evt.getSource();
            if (source == amountField) {
                amount = ((Number)amountField.getValue()).doubleValue();
            }
        });
        amountField.setBounds(100,600,200,20);
        this.add(amountField);

        //create and add the button
        exchangeBtn = new JButton("Exchange");
        exchangeBtn.setBounds(235,340,100,100);
        exchangeBtn.addActionListener(e -> {
        //convert the value in the field
            double res=1;
            Convertor c = (cName, rates, num) -> num*rates.get(cName);
            try {
                res = c.toILS(cExchangeSource, rates, res);
                res = c.convert(cExchangeTarget, rates, res);
                resultLabel.setText("Result =" + amount * res);
            }
            catch (NullPointerException n){
                resultLabel.setText("Please select currencies");
            }
        });
        this.add(exchangeBtn);


        //create the jtable
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        table = new JTable(model) {


            /*@Override
            two extra overrides to prevent moving the grid and editing
            }*/
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return Double.class;
                    default:
                        return String.class;
                }
            }
            public boolean isCellEditable(int row, int column){
                return false;
            }
            @Override
            public void columnMoved(TableColumnModelEvent tcme) { }

        };
        //draw it and add the header
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        table.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane);
    }

    private void setLabels(){
        sourceLabel = new JLabel("Source");
        sourceLabel.setBounds(100, 250, 150, 50);
        targetLabel = new JLabel("Target");
        targetLabel.setBounds(400, 250, 150, 50);
        amountLabel = new JLabel("Amount:");
        amountLabel.setBounds(50 ,585, 100, 50);
        resultLabel = new JLabel("Result = NaN");
        resultLabel.setBounds(50 ,635, 400, 50);
        resultLabel.setFont(new Font("Serif", Font.PLAIN, 32));
        this.add(sourceLabel);
        this.add(targetLabel);
        this.add(amountLabel);
        this.add(resultLabel);
    }
    public static void updateTable(Object arr[]){
        int i;
        for (i=0;i<14;i++) {
            table.setValueAt(arr[i],i,1);
        }
    }
}