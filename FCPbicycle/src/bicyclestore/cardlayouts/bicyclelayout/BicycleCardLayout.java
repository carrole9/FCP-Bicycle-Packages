package bicyclestore.cardlayouts.bicyclelayout;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import bicyclestore.Database;
import bicyclestore.cardlayouts.stockcontrol.StockControlCard;


public class BicycleCardLayout extends JPanel implements ItemListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Database database;
	private JPanel  comboBoxPane, cards;
	private  JPanel bicyclePanel;
	
	private static final String VIEW_BICYCLES = "View Bicycles";
	private static final String ADD_BICYCLES = "Add Bicycles";
	private String[] comboBoxItems = {VIEW_BICYCLES,ADD_BICYCLES};
	
	private BicycleTableSorter bicycleTableSorter;
	private AddBicycles addBicycles;
	private StockControlCard stock;
	
	
	
	
	public BicycleCardLayout(Database database,StockControlCard stock) {
		
		this.database = database;
		this.stock=stock;
		createCardLayoutPane();
	}
	
	public void createCardLayoutPane(){
		
		// create panels which make up card layouts
				this.setLayout(new BorderLayout());
				createComboBoxPane();
				cards = new JPanel(new CardLayout());
				
				bicycleTableSorter = new BicycleTableSorter(database,this);
				addBicycles = new AddBicycles(database,this, stock);
				
				// create cards to make up card layout
				JPanel card1 = bicycleTableSorter.getBicycleLayoutPane();//.getBicycleLayoutPane();
				JPanel card2 = addBicycles;
				
				cards.add(card1,VIEW_BICYCLES);
				cards.add(card2,ADD_BICYCLES);
				
				
				this.add(comboBoxPane, BorderLayout.NORTH);
				this.add(cards, BorderLayout.CENTER);
				
	}
	private void createComboBoxPane() {
		// set up titled border for combo box pane
		TitledBorder comboBoxBorder = BorderFactory.createTitledBorder("Select an option");
		comboBoxBorder.setTitleJustification(TitledBorder.CENTER);
		comboBoxPane = new JPanel();
		comboBoxPane.setPreferredSize(new Dimension(750, 65));
		comboBoxPane.setBorder(comboBoxBorder);
		
		// add items and item listener to combo box
        JComboBox<String> cb = new JComboBox<String>(comboBoxItems);
		cb.setEditable(false);
        cb.addItemListener(this);
        comboBoxPane.add(cb);
	}
	public void newBicyclerAdded() {
		// refresh order details on all other cards
		bicycleTableSorter.refresh();
	}
	

	@Override
	public void itemStateChanged(ItemEvent e) {
		CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String)e.getItem());
	}

}
