
package bicyclestore.cardlayouts.bicyclelayout;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import bicyclestore.Database;
import bicyclestore.SystemData;
import bicyclestore.bikes.Bicycle;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.ArrayList;


public class BicycleTableSorter {
    private boolean DEBUG = false;
    private JTable table;
    private static JPanel bicyclePanel;
    private JTextField filterText;
    private JTextField statusText;
    private TableRowSorter<MyTableModel> sorter;
    

    public BicycleTableSorter() {
    	bicyclePanel = new JPanel(new BorderLayout());

      
        //Create a table with a sorter.
        MyTableModel model = new MyTableModel();
        sorter = new TableRowSorter<MyTableModel>(model);
        table = new JTable(model);
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


        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);
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

    /** 
     * Update the row filter regular expression from the expression in
     * the text box.
     */
    private void newFilter() {
        RowFilter<MyTableModel, Object> rf = null;
        //If current expression doesn't parse, don't update.
        try {
            rf = RowFilter.regexFilter(filterText.getText(), 0);
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf);
        
        
    }
	public JPanel getBicycleLayoutPane() {
		return bicyclePanel;
	}
	




    class MyTableModel extends AbstractTableModel {
        private String[] columnNames = {"Model", "Colour", "Frame Size", "Wheel Size", "Frame Composition"};
        private Object[][] data = new Object [50][50];
        private ArrayList<Bicycle> newArrayList = new ArrayList<Bicycle>();
        private Database database;

        public MyTableModel(){
        	addBicyclesFromDB();
        	viewBicyclesTable();
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

    				data[i][0] = bicycle.getSimpleName();
    				data[i][1] = bicycle.getColour();
    				data[i][2] = bicycle.getFrameSize() + "";
    				data[i][3] = bicycle.getWheelSize() + "";
    				data[i][4] = bicycle.getFrameComposition();
    				data[i][5] = bicycle.isInStock();
    				data[i][6] = bicycle.getCostPrice();
    				
    				i++;
    				//table = new JTable(objs, columnNames);
    				

    			}
    			table = new JTable(data, columnNames);
    		}
        }
    
    			
        

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.length;
        }

        public String getColumnName(int col) {
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
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col < 2) {
                return false;
            } else {
                return true;
            }
        }

        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        public void setValueAt(Object value, int row, int col) {
            if (DEBUG) {
                System.out.println("Setting value at " + row + "," + col
                                   + " to " + value
                                   + " (an instance of "
                                   + value.getClass() + ")");
            }

            data[row][col] = value;
            fireTableCellUpdated(row, col);

            if (DEBUG) {
                System.out.println("New value of data:");
                printDebugData();
            }
        }

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


}

