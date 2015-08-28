package bicyclestore.cardlayouts.bicyclelayout;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import bicyclestore.Database;
import bicyclestore.SystemData;
import bicyclestore.bikes.Bicycle;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.Component;
public class BicycleTableSorter extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Bicycle> newArrayList = new ArrayList<Bicycle>();
	private boolean DEBUG = false;
	private JTable table;
	private JPanel bicyclePanel;
	private JTextField filterText;
	private JTextField statusText;
	private TableRowSorter<TableModel> sorter;
	private Database database;
	TableModel newTable = new TableModel();
	Object[][] objs = newTable.getObjs();
	String [] columnNames = newTable.getColumnNames();
	public BicycleTableSorter(Database database) {
		super(new GridLayout(1, 0));
		database = this.database;
		addBicyclesFromDB();
		addComponents();
		getBicyclesTable();
		
	}
	public void addComponents() {
		bicyclePanel = new JPanel();
		//setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		// Create a table with a sorter.
		TableModel model = new TableModel();
        sorter = new TableRowSorter<TableModel>(model);
        table = new JTable(model);
		table.setRowSorter(sorter);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		// For the purposes of this example, better to have a single
		// selection.
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// When selection changes, provide user with row numbers for
		// both view and model.
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				int viewRow = table.getSelectedRow();
				if (viewRow < 0) {
					// Selection got filtered away.
					statusText.setText("");
				} else {
					int modelRow = table.convertRowIndexToModel(viewRow);
					statusText.setText(String.format("Selected Row in view: %d. " + "Selected Row in model: %d.",
							viewRow, modelRow));
				}
			}
		});
		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(table);
		//// Add the scroll pane to this panel.
		bicyclePanel.add(scrollPane,BorderLayout.CENTER);
		// add(scrollPane);
		// Create a separate form for filterText and statusText
		JPanel form = new JPanel(new SpringLayout());
		JLabel l1 = new JLabel("Filter Text:", SwingConstants.TRAILING);
		form.add(l1);
		filterText = new JTextField();
		// Whenever filterText changes, invoke newFilter.
		filterText.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				newFilter();
			}
			public void insertUpdate(DocumentEvent e) {
				newFilter();
			}
			public void removeUpdate(DocumentEvent e) {
				newFilter();
			}
		});
		l1.setLabelFor(filterText);
		form.add(filterText);
		JLabel l2 = new JLabel("Status:", SwingConstants.TRAILING);
		form.add(l2);
		statusText = new JTextField();
		l2.setLabelFor(statusText);
		form.add(statusText);
		SpringUtilities.makeCompactGrid(form, 2, 2, 6, 6, 6, 6);
		//add(form);
	
		bicyclePanel.add(form,BorderLayout.PAGE_END);
	}
	public JPanel getBicycleLayoutPane() {
		return bicyclePanel;
	}
	public void addBicyclesFromDB() {
		database = new Database();
		SystemData symData = new SystemData(database);
		symData.fillDatabase();
		newArrayList = database.getBicycles();
	}
	public void viewBicycles() {
		TableModel viewBicycle = new TableModel();
	}
	/**
	 * Update the row filter regular expression from the expression in the text
	 * box.
	 */
	private void newFilter() {
		RowFilter<TableModel, Object> rf = null;
		// If current expression doesn't parse, don't update.
		try {
			rf = RowFilter.regexFilter(filterText.getText(), 0);
		} catch (java.util.regex.PatternSyntaxException e) {
			return;
		}
		sorter.setRowFilter(rf);
	}
		
		public void getBicyclesTable() {
		/*	int i = 0;
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
				}
				table = new JTable(objs, columnNames);
			}*/
			// table = new JTable(objs, columnNames);
		}
		/*
		 * JTable uses this method to determine the default renderer/ editor for
		 * each cell. If we didn't implement this method, then the last column
		 * would contain text ("true"/"false"), rather than a check box.
		 */
		/*
		 * Don't need to implement this method unless your table's editable.
		 */
		public boolean isCellEditable(int row, int col) {
			// Note that the data/cell address is constant,
			// no matter where the cell appears onscreen.
			if (col < 2) {
				return false;
			} else {
				return true;
			}
		}
		
		
	}
