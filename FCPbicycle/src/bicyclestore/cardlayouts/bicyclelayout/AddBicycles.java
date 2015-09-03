package bicyclestore.cardlayouts.bicyclelayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
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
import bicyclestore.cardlayouts.customercardlayouts.CustomersCardLayout;
//import bicyclestore.cardlayouts.ordercardlayouts.OrderFromSupplierCard.ComboBoxListener;



public class AddBicycles extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Database database;
	private String cbItemName;
	private BicycleCardLayout bicycleCardLayout;
	private JTextField txtModel, txtColour, txtFrameSize, txtWheelSize,txtFrameComposition,txtCostPrice,txtSalePrice,txtNoOfGears;
	private JButton addBicycleButton;
	private JComboBox<String> productBox;
	private String[] productItems = {"Select a Product Type","BMX","Cruiser","Hybrid","Motorised Bike","Mountain Bike","Road Bike"};
	
	public AddBicycles(Database database,BicycleCardLayout bicycleCardLayout ) {
		
		this.database = database;
		this.bicycleCardLayout = bicycleCardLayout;
	
		
		initComponents();
		createAddCustomerCard();
		
		
	}
	private void initComponents() {
	
		
		//txtType = new JTextField(10);
		txtModel = new JTextField(10);
		txtColour = new JTextField(10);
		txtFrameSize = new JTextField(10);
		txtWheelSize = new JTextField(10);
		txtNoOfGears = new JTextField(10);
		txtFrameComposition = new JTextField(10);
		txtCostPrice = new JTextField(10);
		txtSalePrice = new JTextField(10);
		
		
		
		addBicycleButton = new JButton("Add Bicycle");
		addBicycleButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		addBicycleButton.addActionListener(new ButtonActionListener());
	}
	
	public void createAddCustomerCard(){
		TitledBorder addBicycleBorder = BorderFactory.createTitledBorder("Enter Bicycle Details");
		addBicycleBorder.setTitleJustification(TitledBorder.CENTER);
		setBorder(addBicycleBorder);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(createBicycleDetailsForm());
		add(addBicycleButton);
	}
	public JPanel createBicycleDetailsForm(){
		
		JPanel bicycleDetailsGrid = new JPanel();
		GridLayout gridLayout = new GridLayout(9,2);
		gridLayout.setVgap(10);
		bicycleDetailsGrid.setBorder(new EmptyBorder(20,20,20,20));
		bicycleDetailsGrid.setLayout(gridLayout);
		bicycleDetailsGrid.setMaximumSize(new Dimension(400, 400));
		
		bicycleDetailsGrid.add(new JLabel("Bicycle Type"));
		productBox = new JComboBox(productItems);
		productBox.addActionListener(new ComboBoxListener()); 

		

		bicycleDetailsGrid.add(productBox);
		//bicycleDetailsGrid.add(txtType);
		bicycleDetailsGrid.add(new JLabel("Bicycle Model"));
		bicycleDetailsGrid.add(txtModel);
		bicycleDetailsGrid.add(new JLabel("Bicycle Colour"));
		bicycleDetailsGrid.add(txtColour);
		bicycleDetailsGrid.add(new JLabel("Bicycle Frame Size"));
		bicycleDetailsGrid.add(txtFrameSize);
		bicycleDetailsGrid.add(new JLabel("Bicycle Wheel Size"));
		bicycleDetailsGrid.add(txtWheelSize);
		bicycleDetailsGrid.add(new JLabel("No. Of Gears"));
		bicycleDetailsGrid.add(txtNoOfGears);
		bicycleDetailsGrid.add(new JLabel("Bicycle Frame Composition"));
		bicycleDetailsGrid.add(txtFrameComposition);
		bicycleDetailsGrid.add(new JLabel("Bicycle Cost Price"));
		bicycleDetailsGrid.add(txtCostPrice);
		bicycleDetailsGrid.add(new JLabel("Bicycle Sale Price"));
		bicycleDetailsGrid.add(txtSalePrice);
		
		
		return bicycleDetailsGrid;
		
		}
	public void getItemName(String name){
		
		cbItemName = name;
	}
	public void checkFieldsComplete(String name){
		
		
		// get bicycle details from text fields
				
				String model = txtModel.getText();
				String colour = txtColour.getText();
				String frameSize = txtFrameSize.getText();
				String wheelSize = txtWheelSize.getText();
				String noOfGears = txtNoOfGears.getText();
				String frameComposition = txtFrameComposition.getText();
				String costPrice = txtCostPrice.getText();
				String salePrice = txtSalePrice.getText();
				
				int intNoOfGears;
				int intFrameSize;
				int intWheelSize;
				double doubleCostPrice;
				double doubleSalePrice;
				intNoOfGears = Integer.parseInt(noOfGears);
				intFrameSize = Integer.parseInt(frameSize);
				intWheelSize = Integer.parseInt(wheelSize);
				doubleCostPrice = Double.parseDouble(costPrice);
				doubleSalePrice = Double.parseDouble(salePrice);
				
				
				
				// check if any text  fields have been left blank and output error message if true
				if(name.length()==0 || model.length() == 0 || colour.length() == 0 
						|| frameSize.length() == 0 || wheelSize.length() == 0 || noOfGears.length()==0 || frameComposition.length() == 0 || costPrice.length() == 0 || salePrice.length() == 0 ) {
					JOptionPane.showMessageDialog(null, "Please Enter All Details", 
							"Incomplete content", JOptionPane.ERROR_MESSAGE);
				}
				// attempt to add bicycle if all fields are complete
				else if(cbItemName.equals("BMX")) {
				
					database.addBicycle(new BMX(model, colour, intFrameSize, intWheelSize,frameComposition, doubleCostPrice, doubleSalePrice));
					clearTextFields();
					//addBicycle (model, colour, intFrameSize, intWheelSize,frameComposition,doubleCostPrice,doubleSalePrice);
				}
				else if(cbItemName.equals("Cruiser")) {
					
					database.addBicycle(new Cruiser(model, colour, intFrameSize, intWheelSize,frameComposition, doubleCostPrice, doubleSalePrice));
					clearTextFields();
					//addBicycle (model, colour, intFrameSize, intWheelSize,frameComposition,doubleCostPrice,doubleSalePrice);
				}
				else if(cbItemName.equals("Hybrid")) {
					
					database.addBicycle(new Hybrid(intNoOfGears,model, colour, intFrameSize, intWheelSize,frameComposition, doubleCostPrice, doubleSalePrice));
					clearTextFields();
				}
				else if(cbItemName.equals("Motorised Bike")) {
					
					database.addBicycle(new MotorisedBike(model, colour, intFrameSize, intWheelSize,frameComposition, doubleCostPrice, doubleSalePrice));
					clearTextFields();
					//addBicycle(,model, colour, intFrameSize, intWheelSize,frameComposition,doubleCostPrice,doubleSalePrice);
				}
				else if(cbItemName.equals("Mountain Bike")) {
					
					database.addBicycle(new MountainBike(intNoOfGears,model, colour, intFrameSize, intWheelSize,frameComposition, doubleCostPrice, doubleSalePrice));
					clearTextFields();
					//addBicycle (model, colour, intFrameSize, intWheelSize,frameComposition,doubleCostPrice,doubleSalePrice);
				}
				else if(cbItemName.equals("Road Bike")) {
					
					database.addBicycle(new RoadBike(intNoOfGears,model, colour, intFrameSize, intWheelSize,frameComposition, doubleCostPrice, doubleSalePrice));
					clearTextFields();
					//addBicycle (model, colour, intFrameSize, intWheelSize,frameComposition,doubleCostPrice,doubleSalePrice);
				}
					
				
		}
	
	public void clearTextFields(){
		
					
		
		
		
		txtModel.setText("");
		txtColour.setText("");
		txtFrameSize.setText("");
		txtWheelSize.setText("");
		txtNoOfGears.setText("");
		txtFrameComposition.setText("");
		txtCostPrice.setText("");
		txtSalePrice.setText("");
		
		bicycleCardLayout.newBicyclerAdded();
		

		
	
		
		
		
	}
	

	private class ButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			
			// if the user pressed the add bicycle button, check if fields are all filled and attempt to add customer
			if(event.getSource() == addBicycleButton) {
				System.out.println("button pressed");
				checkFieldsComplete(cbItemName);
			System.out.println("button pressed");
			}
			
				
			
	
		}
	}
	private class ComboBoxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
        	JComboBox cb = (JComboBox)e.getSource();
        	//String modelName = (String)cb.getSelectedItem();
        	//getItemName(modelName);
            // grey out number of gears field if unavailable for selected product type
            if(cb.getSelectedItem().equals("BMX") || cb.getSelectedItem().equals("Cruiser") 
                    || cb.getSelectedItem().equals("Motorised Bike")) {
            	txtNoOfGears.setText("");
            	txtNoOfGears.setEditable(false);
            	txtNoOfGears.setOpaque(true);
            	txtNoOfGears.setBackground(Color.GRAY);
            }
            else {
            	txtNoOfGears.setEditable(true);
            	txtNoOfGears.setBackground(Color.WHITE);
            }
            String modelName = (String)cb.getSelectedItem();
        	getItemName(modelName);
            
        }
        
        
    }


	
	
}

	
