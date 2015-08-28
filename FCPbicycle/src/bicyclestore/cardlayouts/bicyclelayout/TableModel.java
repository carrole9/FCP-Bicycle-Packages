package bicyclestore.cardlayouts.bicyclelayout;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import bicyclestore.Database;
public class TableModel extends AbstractTableModel {
	private Database database;
	private String[] columnNames = { "Model", "Colour", "Frame Size", "Wheel Size", "Frame Composition", "In Stock",
			"Cost Price", "Stunt Pegs" };
	private Object[][] objs = new Object[5][5];	
	public TableModel(){
		
	}
	public TableModel(String[] columnNames,Object[][] objs){
		
		columnNames = this.columnNames;
		objs = this.objs;
	}
	
	public String[] getColumnNames() {
		return columnNames;
	}
	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}
	public Object[][] getObjs() {
		return objs;
	}
	public void setObjs(Object[][] objs) {
		this.objs = objs;
	}
	public int getColumnCount() {
		return columnNames.length;
	}
	public int getRowCount() {
		return objs.length;
	}
	public String getColumnName(int col) {
		return columnNames[col];
	}
	public Object getValueAt(int row, int col) {
		return objs[row][col];
	}
		// TODO Auto-generated constructor stub
	}

