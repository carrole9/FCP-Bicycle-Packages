package bicyclestore.cardlayouts.ordercardlayouts;


import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import bicyclestore.Database;
import bicyclestore.bikes.BMX;
import bicyclestore.bikes.Bicycle;
import bicyclestore.bikes.Cruiser;
import bicyclestore.bikes.Hybrid;
import bicyclestore.bikes.MotorisedBike;
import bicyclestore.bikes.MountainBike;
import bicyclestore.bikes.RoadBike;
import bicyclestore.staff.Employee;
import bicyclestore.suppliers.Supplier;

@SuppressWarnings("serial")
public class OrderFromSupplierCard extends JPanel implements ItemListener {
	
	private static final String VIEW_CART = "View Shopping Cart";

	private Database database;
	private Employee employee;
	private OrdersCardLayout cardLayout;
	
	private OrderShoppingCartCard shoppingCartCard;
	
	private JPanel cards;
	
	private JButton btnAddToCart, btnViewCart, btnBackToOrder;
	private Box buttonPane;
	
	private JLabel lblSelectSupplier, lblSelectProductType, lblSelectProductModel, lblSelectQuantity;
	private JComboBox<String> supplierList, productTypeList, productModelList;
	
	private String[] productTypes = {"Please Select Product Type", "BMX", "Mountain Bike",
			"Road Bike", "Hybrid", "Cruiser", "Motorised Bike"};
	
	private String[] productModels; // product models will be empty until use selects product type
	
	private SpinnerNumberModel numberModel;
	private JSpinner quantitySpinner;
	
	public OrderFromSupplierCard(Database database, Employee employee, OrdersCardLayout cardLayout) {
		this.database = database;
		this.employee = employee;
		this.cardLayout = cardLayout;

		createCardLayoutPane();
		
	}
	
	private void createCardLayoutPane() {
		
		btnBackToOrder = new JButton("Return to product order");
		btnBackToOrder.addActionListener(new ButtonListener());
		
		cards = new JPanel(new CardLayout());
		
		shoppingCartCard = new OrderShoppingCartCard(database, employee, cardLayout, btnBackToOrder);
		
		JPanel card1 = productDetailsCard();
		JPanel card2 = shoppingCartCard;
		
		cards.add(card1);
		cards.add(card2, VIEW_CART);
		
		TitledBorder orderBorder = BorderFactory.createTitledBorder("Order from Supplier");
		orderBorder.setTitleJustification(TitledBorder.CENTER);
		setBorder(orderBorder);
		
		this.add(cards);
	}
	
	private JPanel productDetailsCard() {
		JPanel card = new JPanel();
		
		lblSelectSupplier = new JLabel("Select Supplier");
		lblSelectProductType = new JLabel("Select Product Type");
		lblSelectProductModel = new JLabel("Select Model");
		lblSelectQuantity = new JLabel("Select Quantity");
		
		// set up combo boxes
		supplierList = new JComboBox<String>(getSupplierListItems());
		productTypeList = new JComboBox<String>(productTypes);
		productTypeList.addActionListener(new ComboBoxListener());
		productModelList = new JComboBox<String>();
		
		// set up spinner to allow user to select quantity of items
		int minValue = 1;
		int maxValue = 1000;
		numberModel = new SpinnerNumberModel(minValue, minValue, maxValue, 1);
		quantitySpinner = new JSpinner(numberModel);
		
		setUpButtonPane();
		
		card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

		card.add(createOrderDetailsForm());
		card.add(buttonPane);
		
		return card;
	}
	
	private JPanel createOrderDetailsForm() {
		JPanel orderDetails = new JPanel();
		GridLayout grid = new GridLayout(4, 2);
		grid.setVgap(10);
		orderDetails.setLayout(grid);
		orderDetails.setMaximumSize(new Dimension(400, 300));
		orderDetails.add(lblSelectProductType);
		orderDetails.add(productTypeList);
		orderDetails.add(lblSelectProductModel);
		orderDetails.add(productModelList);
		orderDetails.add(lblSelectSupplier);
		orderDetails.add(supplierList);
		orderDetails.add(lblSelectQuantity);
		orderDetails.add(quantitySpinner);
		
		return orderDetails;
	}
	
	private void setUpButtonPane() {
		
		btnAddToCart = new JButton("Add To Cart");
		btnAddToCart.addActionListener(new ButtonListener());
		btnViewCart = new JButton(VIEW_CART);
		btnViewCart.addActionListener(new ButtonListener());
		
		buttonPane = Box.createHorizontalBox();
		buttonPane.setBorder(new EmptyBorder(30,30,30,30));
		
		buttonPane.add(Box.createHorizontalGlue());
		buttonPane.add(btnAddToCart);
		buttonPane.add(Box.createHorizontalStrut(10));
		buttonPane.add(btnViewCart);
		buttonPane.add(Box.createHorizontalGlue());
	}
	
	private String[] getSupplierListItems() {
		ArrayList<Supplier> suppliers = database.getSuppliers();
		String[] supplierListItems = new String[suppliers.size()];
		for(int i = 0; i < suppliers.size(); i++) {
			supplierListItems[i] = suppliers.get(i).getName();
		}
		return supplierListItems;
	}
	
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			// find out if user pressed process order button
			if(event.getSource() == btnAddToCart) {
				// do not attempt to save an order if no product is present in combo box
				if(productModelList.getSelectedObjects().length > 0) {
					String productType = (String)productTypeList.getSelectedItem();
					String supplierChoice = (String)supplierList.getSelectedItem();
					String model = (String)productModelList.getSelectedItem();
					int quantity = (int)quantitySpinner.getValue();
					
					shoppingCartCard.addToCart(productType, database.getBicycle(model),
							quantity, database.getSupplier(supplierChoice));
					
					JOptionPane.showMessageDialog(null, "Product added to Cart\n"
							+ "Press view shopping cart to view items in cart",
							"Item added to cart", JOptionPane.INFORMATION_MESSAGE);
					
				}
			}
			
			if(event.getSource() == btnViewCart) {
				CardLayout c1 = (CardLayout) cards.getLayout();
				//c1.show(cards, VIEW_CART);
				c1.next(cards);
			}
			
			if(event.getSource() == btnBackToOrder) {
				CardLayout c1 = (CardLayout) cards.getLayout();
				c1.previous(cards);
			}
		}	
	}
	
	private class ComboBoxListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			// populate model list combo box with bicycles of selected type
			
			// get string name for product type selected
			String productType = (String)productTypeList.getSelectedItem();
			ArrayList<String> products = new ArrayList<String>();
			
			// Fill new collection of bicycles of selected type from database
			if(productType.equals("BMX")) {
				ArrayList<Bicycle> bicycles = database.getBicycles();
				for(Bicycle bicycle : bicycles) {
					if(bicycle instanceof BMX)
						products.add(bicycle.getModel());
				}
			}
			if(productType.equals("Mountain Bike")) {
				ArrayList<Bicycle> bicycles = database.getBicycles();
				for(Bicycle bicycle : bicycles) {
					if(bicycle instanceof MountainBike)
						products.add(bicycle.getModel());
				}
			}
			if(productType.equals("Road Bike")) {
				ArrayList<Bicycle> bicycles = database.getBicycles();
				for(Bicycle bicycle : bicycles) {
					if(bicycle instanceof RoadBike)
						products.add(bicycle.getModel());
				}
			}
			if(productType.equals("Hybrid")) {
				ArrayList<Bicycle> bicycles = database.getBicycles();
				for(Bicycle bicycle : bicycles) {
					if(bicycle instanceof Hybrid)
						products.add(bicycle.getModel());
				}
			}
			if(productType.equals("Cruiser")) {
				ArrayList<Bicycle> bicycles = database.getBicycles();
				for(Bicycle bicycle : bicycles) {
					if(bicycle instanceof Cruiser)
						products.add(bicycle.getModel());
				}
			}
			if(productType.equals("Motorised Bike")) {
				ArrayList<Bicycle> bicycles = database.getBicycles();
				for(Bicycle bicycle : bicycles) {
					if(bicycle instanceof MotorisedBike)
						products.add(bicycle.getModel());
				}
			}
			
			// convert ArrayList to array and update combo box to contain selected models 
			productModels = products.toArray(new String[products.size()]);
			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(productModels);
			productModelList.setModel(model);
		}
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		CardLayout c1 = (CardLayout) cards.getLayout();
		c1.show(cards, (String)e.getItem());
	}

}