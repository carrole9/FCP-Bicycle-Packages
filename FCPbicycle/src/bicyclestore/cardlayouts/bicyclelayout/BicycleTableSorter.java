
package bicyclestore.cardlayouts.bicyclelayout;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import bicyclestore.Database;
import bicyclestore.SystemData;
import bicyclestore.bikes.Bicycle;
import bicyclestore.bikes.MotorisedBike;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class BicycleTableSorter extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean DEBUG = false;
    private JTable table;
    private JPanel bicyclePanel;
    JScrollPane scrollPane;
    
    private JTextField filterText;
    private JTextField statusText;
    private DefaultTableModel model;
    private TableRowSorter<TableModel> sorter;
    private Database database;
    private Object[] columnNames = {"Product Type","Model", "Colour", "Frame Size", "Wheel Size","Frame Composition","Cost Price","Sale Price"};
    private Object[][] data = new Object [50][50];
    private ArrayList<Bicycle> newArrayList;
   
    

    public BicycleTableSorter(Database database,BicycleCardLayout bicycleCardLayout) {
    	
    	this.database = database;
    	bicyclePanel = new JPanel(new BorderLayout());
    	
    	createBicycleTable();
    	
        
       
    }
    
    public void createBicycleTable(){
    	table = new JTable() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
    
        //Create a table with a sorter.
        model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);
        
       
        sorter = new TableRowSorter<TableModel>(model);
        table.setModel(model);
        table.setRowSorter(sorter);
        table.setPreferredScrollableViewportSize(new Dimension(500, 200));
        table.setFillsViewportHeight(true);

        //For the purposes of this example, better to have a single
        //selection.
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        
        //When selection changes, provide user with row numbers for
        //both view and model.
        table.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent event) {
                        int viewRow = table.getSelectedRow();
                        if (viewRow < 0) {
                            //Selection got filtered away.
                            statusText.setText("");
                        } else {
                            int modelRow = 
                                table.convertRowIndexToModel(viewRow);
                            statusText.setText(
                                String.format("Selected Row in view: %d. " +
                                    "Selected Row in model: %d.", 
                                    viewRow, modelRow));
                        }
                    }
                }
        );
        addBicyclesFromDB();
    	
    	viewBicyclesTable();
    


        //Create the scroll pane and add the table to it.
        scrollPane = new JScrollPane(table);
        scrollPane.setViewportView(table);
        bicyclePanel.add(scrollPane,BorderLayout.CENTER);
  
        
        //add(scrollPane, BorderLayout.CENTER);

        //Add the scroll pane to this panel.
       // add(scrollPane);

        //Create a separate form for filterText and statusText
        JPanel form = new JPanel(new SpringLayout());
        JLabel l1 = new JLabel("Filter Text:", SwingConstants.TRAILING);
        form.add(l1);
        filterText = new JTextField();
        //Whenever filterText changes, invoke newFilter.
        filterText.getDocument().addDocumentListener(
                new DocumentListener() {
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
        bicyclePanel.add(form,BorderLayout.SOUTH);
        
    }
    public void addBicyclesFromDB(){
		
	

		newArrayList = database.getBicycles();
		
	} 

    public void  viewBicyclesTable(){
		
		for (Bicycle bicycle : newArrayList) {
			
			
				Object [] row = {bicycle.getClass().getSimpleName(), bicycle.getModel()
						,bicycle.getColour(),bicycle.getFrameSize() + "",bicycle.getWheelSize() + "", bicycle.getFrameComposition()
						,bicycle.getCostPrice(),bicycle.getSalePrice()};
				model.addRow(row);
				
			
				

		
		
			}
		
		}
    
    public void refresh(){
    	
    	bicyclePanel.removeAll();
    	bicyclePanel.revalidate();
    	createBicycleTable();
    	
    }
    
    

    /** 
     * Update the row filter regular expression from the expression in
     * the text box.
     */
    private void newFilter() {
        RowFilter<TableModel, Object> rf = null;
        //If current expression doesn't parse, don't update.
        try {
            rf = RowFilter.regexFilter(filterText.getText(), 0);
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf);
    }
        
        
  
        
    	public void addNewBicycle(){
     		
    		database = new Database();

    		SystemData symData = new SystemData(database);
    		symData.fillDatabase();

    		newArrayList = database.getBicycles();
     		
     	}
     	public JPanel getBicycleLayoutPane() {
     		return bicyclePanel;
     	}
    
    			
        

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.length;
        }

        public Object getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }
        

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }
        

        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        
       

    

        private void printDebugData() {
            int numRows = getRowCount();
            int numCols = getColumnCount();

            for (int i=0; i < numRows; i++) {
                System.out.print("    row " + i + ":");
                for (int j=0; j < numCols; j++) {
                    System.out.print("  " + data[i][j]);
                }
                System.out.println();
            }
            System.out.println("--------------------------");
        }
    
    

 	
 
 	
    }
    



