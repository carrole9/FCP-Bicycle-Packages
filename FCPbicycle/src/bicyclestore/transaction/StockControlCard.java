package bicyclestore.transaction;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
public class StockControlCard implements ItemListener {
    JPanel cards; //a panel that uses CardLayout
    final static String TEXTPANEL = "Stock Control";
    final static String GRAPHPANEL = "View as a Graph";
    final static String PredictionPANEL = "Sales Forcast";
    JLabel bmx, motorised, mountain, hybrid, road;
	JLabel sales, predictions;
    StockControl stock= new StockControl();
	GraphIcon graph = new GraphIcon(null, 0, 0);
     
    public void addComponentToPane(Container pane) {
        //Put the JComboBox in a JPanel to get a nicer look.
        JPanel comboBoxPane = new JPanel(); //use FlowLayout
        String comboBoxItems[] = { TEXTPANEL, GRAPHPANEL };
        JComboBox cb = new JComboBox(comboBoxItems);
    	JButton salesPrediction = new JButton(PredictionPANEL);
    	JButton back = new JButton("Previous Page");
        cb.setEditable(false);
        cb.addItemListener(this);
        comboBoxPane.add(cb);
         
        //Create the "cards".
        JPanel card1 = new JPanel();
        
        
         
        JPanel card2 = new JPanel();
        card2.add(graph.createAndShowGUI());
        
        JPanel card3 = new JPanel();
         
        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        cards.add(card1, TEXTPANEL);
        cards.add(card2, GRAPHPANEL);
        cards.add(card3, PredictionPANEL);
         
        pane.add(comboBoxPane, BorderLayout.PAGE_START);
        pane.add(cards, BorderLayout.CENTER);
        bmx = new JLabel("Bmx bikes: ");
		motorised = new JLabel("Motorised Bike: ");
		mountain = new JLabel("Mountain Bikes: ");
		//hybrid = new JLabel("Hybrid Bikes: " + stock.get);
		road = new JLabel("Road Bike:" );
		
		card1.add(bmx);
	    card1.add(new JTextField("" + stock.getBmx(), 15));
		card1.add(motorised );
		card1.add(new JTextField("" + stock.getMotorizedBike(), 15));
		card1.add(mountain);
		card1.add(new JTextField("" + stock.getMountainBike(), 15));
		card1.add(road);
		card1.add(new JTextField("" + stock.getRoadBike(), 15));
		card1.add(salesPrediction);
		card1.setLayout(new BoxLayout(card1, BoxLayout.Y_AXIS));
	
        card3.add(new JLabel("Number of bikes sold last week: "));
        card3.add(new JTextField("" + stock.getNoOfbikesSold(), 15));
        
        card3.add(new JLabel("Sales Forcast for next week: "));
        card3.add(new JTextField("" + stock.getPredictions(), 15));
        
        card3.add(back);
		
       
        
        card3.setLayout(new BoxLayout(card3, BoxLayout.Y_AXIS));
      
        
		
        
       salesPrediction.addActionListener(new ActionListener() {
        	CardLayout cl = (CardLayout)(cards.getLayout());
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cl.show(cards, PredictionPANEL);
			}
		});
		back.addActionListener(new ActionListener() {
			CardLayout cl = (CardLayout)(cards.getLayout());
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cl.show(cards, TEXTPANEL);
			}
		});
        
    }
     
    public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String)evt.getItem());
    }
     
  
    public static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("CardLayoutDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
        //Create and set up the content pane.
        StockControlCard demo = new StockControlCard();
        demo.addComponentToPane(frame.getContentPane());
         
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
