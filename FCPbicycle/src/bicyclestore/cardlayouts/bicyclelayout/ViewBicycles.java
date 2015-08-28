package bicyclestore.cardlayouts.bicyclelayout;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableRowSorter;
import bicyclestore.Database;
import bicyclestore.SystemData;
import bicyclestore.bikes.Bicycle;
public class ViewBicycles extends JPanel implements ItemListener {
	
	private static final String ALL_BICYCLES = "All Bicycles";
	
	private static final String ALL_BMX = "BMX";
	private ArrayList<Bicycle> newArrayList = new ArrayList<Bicycle>();
	private JPanel comboBoxPane;
	private Database database;
	private JPanel bicyclePanel,cards;
	private JTable table;
	private JScrollPane scrollPane,scrollPane2;
	private String[] bicycleStrings = {ALL_BICYCLES,ALL_BMX,"Cruiser","Hybrid","Motorised Bike","Mountain Bike","Road Bike" };
	private String[] columnNames = { "Model", "Colour", "Frame Size", "Wheel Size", "Frame Composition","In Stock","Cost Price" };
	private Object[][] objs = new Object[50][50];
	private ViewBMX viewBMXCard;
	
	public ViewBicycles(Database database) {
		super(new GridLayout(1, 0));
		
		database = this.database;
		addBicyclesFromDB();
		addComponentstoPane();
		viewBicyclesTable();
	
		
		// TODO Auto-generated constructor stub
	}
	public void addComponentstoPane(){
		
		bicyclePanel = new JPanel(new BorderLayout());
		cards = new JPanel(new CardLayout());
		createComboBoxPane();
		
	
		table = new JTable(objs, columnNames);
		table.setSize(200,200);
		table.setOpaque(true);
		
		
		
		viewBMXCard = new ViewBMX(database,this);
		// create cards to make up card layout
				JPanel card1 = new JPanel() ;
				JPanel card2 = viewBMXCard;
				
				
				
				cards.add(card1,ALL_BICYCLES);
				cards.add(card2, ALL_BMX);
			
	
	
		table.setPreferredScrollableViewportSize(new Dimension(750, 750));
		table.setFillsViewportHeight(true);
		// Create the scroll pane and add the table to it.
		scrollPane = new JScrollPane(table);
		scrollPane2 = new JScrollPane(table);
		
		card1.add(scrollPane);
		card2.add(scrollPane2);
		
		//bicyclePanel.add(button);
		bicyclePanel.add(cards, BorderLayout.CENTER);
		bicyclePanel.add(comboBoxPane,BorderLayout.NORTH);
		//bicyclePanel.add(scrollPane,BorderLayout.PAGE_END);
		
		
		
	}
	
	public void addBicyclesFromDB(){
		
		database = new Database();
		SystemData symData = new SystemData(database);
		symData.fillDatabase();
		newArrayList = database.getBicycles();
	}
	
	
	public void  viewBicyclesTable(){
		
		int i = 0;
		for (Bicycle bicycle : newArrayList) {
			if (newArrayList.size() != 0) {
				objs[i][0] = bicycle.getModel();
				objs[i][1] = bicycle.getColour();
				objs[i][2] = bicycle.getFrameSize() + "";
				objs[i][3] = bicycle.getWheelSize() + "";
				objs[i][4] = bicycle.getFrameComposition();
				objs[i][5] = bicycle.isInStock();
				objs[i][6] = bicycle.getCostPrice();
				
				i++;
				//table = new JTable(objs, columnNames);
				
			}
			table = new JTable(objs, columnNames);
			
		
			
			}
	}
	
	private void createComboBoxPane() {
		// set up titled border for combo box pane
		TitledBorder comboBoxBorder = BorderFactory.createTitledBorder("Select View Bicycle Option");
		comboBoxBorder.setTitleJustification(TitledBorder.CENTER);
		comboBoxPane = new JPanel();
		comboBoxPane.setPreferredSize(new Dimension(750, 65));
		comboBoxPane.setBorder(comboBoxBorder);
		
		// add items and item listener to combo box
        JComboBox<String> cb1 = new JComboBox<String>(bicycleStrings);
		cb1.setEditable(false);
        cb1.addItemListener(this);
        comboBoxPane.add(cb1);
	}
	public JPanel getBicycleLayoutPane() {
		return bicyclePanel;
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String)e.getItem());
        
		
	}
		
	}
