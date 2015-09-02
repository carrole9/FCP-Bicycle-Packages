package bicyclestore.cardlayouts.ordercardlayouts;


import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashSet;

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
import bicyclestore.Utilities;
import bicyclestore.bikes.Bicycle;
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
		
		shoppingCartCard = new OrderShoppingCartCard(database, employee, cardLayout, btnBackToOrder, this);
		
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
		supplierList = new JComboBox<String>();
		productTypeList = new JComboBox<String>();
		productTypeList.addActionListener(new ComboBoxListener());
		productModelList = new JComboBox<String>();
		productModelList.addActionListener(new ComboBoxListener());
		setProductTypeOptions();
		
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
	
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			// find out if user pressed process order button
			if(event.getSource() == btnAddToCart) {
				// do not attempt to save an order if no product or supplier is present in combo box
				if(productModelList.getSelectedIndex() > 0 && productTypeList.getSelectedIndex() > 0) {
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
				else {
					JOptionPane.showMessageDialog(null, "You must select a product type, model and"
							+ " a supplier before proceding",
							"Incomplete fields", JOptionPane.ERROR_MESSAGE);
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
			
			if(e.getSource() == productTypeList) {
				setBicycleModelOptions();
			}
			
			if(e.getSource() == productModelList) {
				setSupplierOptions();
			}
		}
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		CardLayout c1 = (CardLayout) cards.getLayout();
		c1.show(cards, (String)e.getItem());
	}
	
	// populate the type combo box with available product types
	public void setProductTypeOptions() {
		HashSet<String> productTypeSet = new HashSet<String>();
		if (shoppingCartCard.isShoppingCartEmpty()) {
			// add all product types if shopping cart is empty
			for (Supplier supplier : database.getSuppliers()) {
				for (Bicycle bike : supplier.getProducts()) {
					productTypeSet.add(Utilities.splitCamelCase(bike.getClass().getSimpleName()));
				}
			}
			supplierList.setModel(new DefaultComboBoxModel<String>(new String[0]));
		} else {
			// only allow user to select product types provided by one supplier
			for (Bicycle bike : shoppingCartCard.getSupplier().getProducts()) {
				productTypeSet.add(Utilities.splitCamelCase(bike.getClass().getSimpleName()));
			}
			setSupplierOptions();
		}
		// convert ArrayList to array and update combo box to contain selected models
		ArrayList<String> productTypeArrayList = new ArrayList<String>(productTypeSet);
		productTypeArrayList.add(0, "Select a product type");
		String[] productTypes = productTypeArrayList.toArray(new String[productTypeArrayList.size()]);
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(productTypes);
		productTypeList.setModel(model);
		
		// set supplier combo box blank until user selects a model
		productModelList.setModel(new DefaultComboBoxModel<String>(new String[0]));
		
	}
	
	// populate model list combo box with available bicycles of selected type
	private void setBicycleModelOptions() {
		// get string name for product type selected
		String productType = (String) productTypeList.getSelectedItem();
		// use a set to store available models so that there is no repetition
		HashSet<String> products = new HashSet<String>();
		
		// fill set with all available models in suppliers catalogue that match product type
		for(Supplier supplier : database.getSuppliers()) {
			for(Bicycle bike : supplier.getProducts()) {
				if(productType.equals(Utilities.splitCamelCase(bike.getClass().getSimpleName()))) {
					products.add(bike.getModel());
				}
			}
		}

		// convert ArrayList to array and update combo box to contain selected models
		ArrayList<String> productNames = new ArrayList<String>(products);
		productNames.add(0, "Select a model");
		String[] productModels = productNames.toArray(new String[productNames.size()]);
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(productModels);
		productModelList.setModel(model);
		
		if(shoppingCartCard.isShoppingCartEmpty()) {
			// set supplier combo box blank until user selects a model
			supplierList.setModel(new DefaultComboBoxModel<String>(new String[0]));
		}
		else
			setSupplierOptions();
		
	}
	
	// populate supplier list combo box with suppliers that sell the selected model
	private void setSupplierOptions() {

		if (shoppingCartCard.isShoppingCartEmpty()) {
			String bikeModel = (String) productModelList.getSelectedItem();
			ArrayList<String> supplierNames = new ArrayList<String>();

			if (!bikeModel.equals("Select a model")) {

				// fill array list with available suppliers who stock the selected model
				for (Supplier supplier : database.getSuppliers()) {
					for (Bicycle bike : supplier.getProducts()) {
						if (bikeModel.equals(bike.getModel())) {
							supplierNames.add(supplier.getName());
							break;
						}
					}
				}
			}
			// convert ArrayList to array and update combo box to contain
			// selected models
			String[] suppliers = new String[supplierNames.size() + 1];
			suppliers[0] = "Select a supplier";
			for (int i = 1; i < suppliers.length; i++) {
				suppliers[i] = supplierNames.get(i - 1);
			}
			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(suppliers);
			supplierList.setModel(model);
		}
		else {
			String[] suppliers = {shoppingCartCard.getSupplier().getName()};
			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(suppliers);
			supplierList.setModel(model);
		}

	}

}