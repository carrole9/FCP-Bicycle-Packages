package bicyclestore.cardlayouts.stockcontrol;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import bicyclestore.Database;
import bicyclestore.SystemData;
//import bicyclestore.cardlayouts.customercardlayouts.AddCustomerCard;
//import bicyclestore.cardlayouts.customercardlayouts.EditCustomerCard;
//import bicyclestore.cardlayouts.customercardlayouts.ViewCustomerCard;
import bicyclestore.transaction.StockControl;
 
public class StockControlCard implements ItemListener {
	
    JPanel cardLayoutPane, cards; //a panel that uses CardLayout
    final static String TEXTPANEL = "Stock Control";
    final static String GRAPHPANEL = "View as a Graph";
    final static String PredictionPANEL = "Sales Forcast";
    JLabel bmx, motorised, mountain, hybrid, road, cruiser;
	JLabel sales, predictions;
	GraphIcon graph = new GraphIcon(null, 0, 0);
	
	//private JPanel comboBoxPane;
	
	public StockControlCard() {
		cardLayoutPane = new JPanel(new BorderLayout());
		addComponentToPane(cardLayoutPane);
	}
     
    public void addComponentToPane(Container pane) {
    	StockControl stock = new StockControl();
        StockControlGraph panel = new StockControlGraph();
		Database database= new Database();
		SystemData data=new SystemData(database);
		data.fillDatabase();
		stock.calculateStock(null, database);
		stock.predictions(database);
		
    	
    	
        //Put the JComboBox in a JPanel to get a nicer look.
        JPanel comboBoxPane = new JPanel(); //use FlowLayout
        String comboBoxItems[] = { TEXTPANEL, GRAPHPANEL };
        JComboBox<String> cb = new JComboBox<String>(comboBoxItems);
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
		road = new JLabel("Road Bike:" );
		cruiser = new JLabel("Cruiser Bikes: ");
		hybrid = new JLabel("Hybrid Bikes: ");
		
		card1.add(bmx).setBackground(Color.LIGHT_GRAY);
	    card1.add(new JTextField("" + stock.getBmx(), 15)).setFocusable(false);;
		card1.add(motorised ).setBackground(Color.LIGHT_GRAY);
		card1.add(new JTextField("" + stock.getMotorizedBike(), 15)).setFocusable(false);
		card1.add(mountain).setBackground(Color.LIGHT_GRAY);
		card1.add(new JTextField("" + stock.getMountainBike(), 15)).setFocusable(false);
		card1.add(road).setBackground(Color.LIGHT_GRAY);
		card1.add(new JTextField("" + stock.getRoadBike(), 15)).setFocusable(false);
		card1.add(cruiser);
		card1.add(new JTextField("" + stock.getCruiserBike(), 15)).setFocusable(false);
		card1.add(hybrid).setBackground(Color.LIGHT_GRAY);
		card1.add(new JTextField("" + stock.getHybridBike(), 15)).setFocusable(false);;
		card1.add(salesPrediction);
		card1.setLayout(new BoxLayout(card1, BoxLayout.Y_AXIS));
	
        card3.add(new JLabel("Number of bikes sold last week: "));
        card3.add(new JTextField("" + stock.getNoOfbikesSold(), 15)).setFocusable(false);;
        
        card3.add(new JLabel("Sales Forcast for next week: "));
        card3.add(new JTextField("" + stock.getPredictions(), 15)).setFocusable(false);;
        
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
        //demo.addComponentToPane(cardLayoutPane); 
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
    public JPanel getStockControlCardLayout() {
    	return cardLayoutPane;
    }
    

}
