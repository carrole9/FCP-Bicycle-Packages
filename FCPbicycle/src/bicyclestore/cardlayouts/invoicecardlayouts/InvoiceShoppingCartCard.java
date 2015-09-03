package bicyclestore.cardlayouts.invoicecardlayouts;

	import java.awt.Color;
	import java.awt.Dimension;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

	import javax.swing.Box;
	import javax.swing.BoxLayout;
	import javax.swing.JButton;
	import javax.swing.JLabel;
	import javax.swing.JOptionPane;
	import javax.swing.JPanel;
	import javax.swing.JScrollPane;
	import javax.swing.JTable;
	import javax.swing.JTextField;
	import javax.swing.border.EmptyBorder;
	import javax.swing.table.DefaultTableModel;

	import bicyclestore.Database;
	import bicyclestore.bikes.Bicycle;
import bicyclestore.cardlayouts.profitandlosscardlayout.ProfitAndLossCard;
import bicyclestore.customers.Customer;
import bicyclestore.staff.Employee;
	import bicyclestore.suppliers.Supplier;
	//import bicyclestore.transaction.PurchasingTransaction;
import bicyclestore.transaction.SalesTransaction;
import bicyclestore.transaction.ShoppingBasket;

	@SuppressWarnings("serial")
	public class InvoiceShoppingCartCard extends JPanel {
		
		private Database database;
		private ShoppingBasket basket;
		private Employee employee;
		//private Supplier supplier;
		private Customer customer;
		
		private InvoiceCardLayout cardLayout;
		private ViewInvoiceCard viewInvoice;
		private DeleteInvoice deleteInvoice;
		
		private JButton btnProcessOrder, btnReturnOrder;
		
		private Box buttonPane;
		
		private Box idPane;
		private JTextField txtId;
		private JButton btnEditId;
		
		private int transactionIdCount = 10004;
		private ProfitAndLossCard profitLossCard;
		
		private DefaultTableModel tableModel;
		private JTable orderDetailsTable;
		private static final String[] TABLE_COLS = {"Product Type","Model","Customer","Quanity"};

		public InvoiceShoppingCartCard(Database database, Employee employee, InvoiceCardLayout cardLayout, JButton btnReturnOrder) {
			this.database = database;
			this.employee = employee;
			basket = new ShoppingBasket();
			profitLossCard = new ProfitAndLossCard(database);
			this.cardLayout = cardLayout;
			this.btnReturnOrder = btnReturnOrder;
			
			initComponents();
		}
		
		private void initComponents() {
			
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

			setUpIdPane();
			setUpTable();
			setUpButtonPane();
			
			this.add(idPane);
			this.add(new JScrollPane(orderDetailsTable));
			this.add(buttonPane);
			
		}

		private void setUpIdPane() {
			idPane = Box.createHorizontalBox();
			idPane.setBorder(new EmptyBorder(20,20,20,20));
			
			// set ID field to auto-increment value, grey out colour and set editable to false
			txtId = new JTextField(transactionIdCount+"",10);
			txtId.setBackground(Color.LIGHT_GRAY);
			txtId.setEditable(false);
			
			btnEditId = new JButton("Edit ID");
			
			idPane.add(new JLabel("Transaction ID"));
			idPane.add(Box.createHorizontalStrut(100));
			idPane.add(txtId);
			idPane.add(btnEditId);
			
			btnEditId.addActionListener(new ButtonListener());
		}
		
		private void setUpTable() {
			tableModel = new DefaultTableModel();
			tableModel.setColumnIdentifiers(TABLE_COLS);
			orderDetailsTable = new JTable(tableModel);
			orderDetailsTable.setFillsViewportHeight(true);
			orderDetailsTable.setPreferredScrollableViewportSize(new Dimension(600,150));
		}
		
		private void setUpButtonPane() {
			
			btnProcessOrder = new JButton("Process Order");
			btnProcessOrder.addActionListener(new ButtonListener());
			
			buttonPane = Box.createHorizontalBox();
			buttonPane.setBorder(new EmptyBorder(20,20,20,20));
			
			buttonPane.add(Box.createHorizontalGlue());
			buttonPane.add(btnProcessOrder);
			buttonPane.add(Box.createHorizontalStrut(10));
			buttonPane.add(btnReturnOrder);
			buttonPane.add(Box.createHorizontalGlue());
		}
		
		public void addToCart(String productType, Bicycle bicycle, int quantity, Customer customer) {
			for(int i = 0; i < quantity; i++) {
				basket.add(bicycle);
			}
			
			// add to table. Product type, model, supplier, quantity
			Object[] row = {productType, bicycle.getModel(), customer.getName(), quantity};
			tableModel.addRow(row);
			this.customer = customer;
		}

		private void createSalesTransaction() throws IOException {
			
			int transactionId = Integer.parseInt(txtId.getText());
			// increment counter if auto-increment number was used
			if(transactionId == transactionIdCount)
				transactionIdCount++;
			
			double cost = calculateCost();
			
			////////////////???????????????????????????????????
			database.addSalesTransaction(new SalesTransaction(transactionId, employee, 
					customer, cost, "Account", new Date(), basket));
			
			// display confirmation dialogue to user
			JOptionPane.showMessageDialog(null, "Order processed"
					+ "\nTransaction id: "+transactionId
					+ "\nCustomer: "+ customer.getName()
					+ "\nAmount: "+cost ,
					"Order Processed", JOptionPane.INFORMATION_MESSAGE);
			
			
			String data="Invoice"
					+ "\nTransaction id: "+transactionId
					+ "\nCustomer: "+ customer.getName()
					+ "\nAmount: "+cost;
			
			String fileName= ""+transactionId;
			File file = new File(fileName);
			file.createNewFile();

	        FileWriter fr = null;
	        BufferedWriter br = null;
	        String dataWithNewLine=data+System.getProperty("line.separator");
	        try{
	            fr = new FileWriter(file);
	            br = new BufferedWriter(fr);

	                br.write(dataWithNewLine);

	        } catch (IOException e) {
	            e.printStackTrace();
	        }finally{
	            try {
	                br.close();
	                fr.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
					
			resetFields();
			
			cardLayout.newInvoiceAdded(transactionId);
		}
		
		private void resetFields() {
			txtId.setText(transactionIdCount+"");
			basket = new ShoppingBasket();
			basket.removeAll();
			// remove all table rows
			for(int i = tableModel.getRowCount() -1; i >= 0; i--) {
				tableModel.removeRow(i);
			}
			
		}
		
		private double calculateCost() {
			return basket.getTotalCostValue();
		}
		
		
		
		private class ButtonListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent event) {
				
				if(event.getSource() == btnProcessOrder) {
					// Output error dialogue if shopping card is empty
					if(basket.getShoppingList().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Shopping Cart is empty\n"
								+ "Please add some products to Cart before processing",
								"No products in shopping cart", JOptionPane.ERROR_MESSAGE);
					}
					else {
						try {
							profitLossCard.newOrderAdded();
							createSalesTransaction();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
				if(event.getSource() == btnEditId) {
					String custIdStr = JOptionPane.showInputDialog(null, "Enter customer ID",
							"Edit Customer ID", JOptionPane.INFORMATION_MESSAGE);
					if(custIdStr != null) {
						try{
							txtId.setText(Integer.parseInt(custIdStr) + "");
						}catch(NumberFormatException e) {
							JOptionPane.showMessageDialog(null, "Customer ID must contain a number",
								"Invalid Input", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
			
		}
		
	}	
