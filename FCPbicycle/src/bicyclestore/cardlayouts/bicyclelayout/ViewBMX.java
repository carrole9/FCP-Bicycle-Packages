
package bicyclestore.cardlayouts.bicyclelayout;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import bicyclestore.Database;
import bicyclestore.SystemData;
import bicyclestore.bikes.BMX;
import bicyclestore.bikes.Bicycle;
public class ViewBMX extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Bicycle> newArrayList = new ArrayList<Bicycle>();
	private JPanel bmxPanel;
	private Database database;
	private JTable table;
	private JScrollPane scrollPane;
	private String[] columnNames = { "Model", "Colour", "Frame Size", "Wheel Size", "Frame Composition", "In Stock","Cost Price",
			"Stunt Pegs" };
	private Object[][] objs = new Object[50][50];
	public ViewBMX(Database database,ViewBicycles bicyclePanel) {
		super(new GridLayout(1, 0));
		database = this.database;
		
		addBicyclesFromDB();
		addComponentstoPane();
		viewBMXTable();
		// TODO Auto-generated constructor stub
	}
	public void addComponentstoPane() {
		bmxPanel = new JPanel(new BorderLayout());
		table = new JTable(objs, columnNames);
		table.setSize(200, 200);
		table.setOpaque(true);
		table.setPreferredScrollableViewportSize(new Dimension(750, 750));
		table.setFillsViewportHeight(true);
		// Create the scroll pane and add the table to it.
		scrollPane = new JScrollPane(table);
		// bicyclePanel.add(button);
		bmxPanel.add(scrollPane, BorderLayout.PAGE_END);
		
	}
	public void addBicyclesFromDB() {
		database = new Database();
		SystemData symData = new SystemData(database);
		symData.fillDatabase();
		newArrayList = database.getBicycles();
	}
	
	public void  viewBMXTable(){
		int i = 0;
		
		for (Bicycle bicycle : newArrayList) {
			
			if (newArrayList.size() != 0 && bicycle instanceof BMX) {
				objs[i][0] = bicycle.getModel();
				objs[i][1] = bicycle.getColour();
				objs[i][2] = bicycle.getFrameSize() + "";
				/*objs[i][3] = bicycle.getWheelSize() + "";
				objs[i][4] = bicycle.getFrameComposition();
				objs[i][5] = bicycle.isInStock();
				objs[i][6] = bicycle.getCostPrice();*/
			
				
				i++;
				//table = new JTable(objs, columnNames);
				
			}
			table = new JTable(objs, columnNames);
			
		}
		
	}
}
