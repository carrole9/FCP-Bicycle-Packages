package bicyclestore.cardlayouts.bicyclelayout;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
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
import bicyclestore.cardlayouts.customercardlayouts.CustomersCardLayout;



public class AddBicycles extends JPanel  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Database database;
	private BicycleTableSorter bicycleTableSorter;
	private BicycleCardLayout bicycleCardLayout;
	private JTextField txtType, txtModel, txtColour, txtFrameSize, txtWheelSize,txtFrameComposition,txtCostPrice,txtSalePrice;
	private JButton addBicycleButton;
	private ArrayList<Bicycle> newArrayList; 
	
	public AddBicycles(Database database,BicycleCardLayout bicycleCardLayout ) {
		
		this.database = database;
		this.bicycleCardLayout = bicycleCardLayout;
	
		
		initComponents();
		createAddCustomerCard();
		
		
	}
	private void initComponents() {
	
		
		txtType = new JTextField(10);
		txtModel = new JTextField(10);
		txtColour = new JTextField(10);
		txtFrameSize = new JTextField(10);
		txtWheelSize = new JTextField(10);
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
		GridLayout gridLayout = new GridLayout(8,2);
		gridLayout.setVgap(10);
		bicycleDetailsGrid.setBorder(new EmptyBorder(20,20,20,20));
		bicycleDetailsGrid.setLayout(gridLayout);
		bicycleDetailsGrid.setMaximumSize(new Dimension(400, 400));
		
		bicycleDetailsGrid.add(new JLabel("Bicycle Type"));
		bicycleDetailsGrid.add(txtType);
		bicycleDetailsGrid.add(new JLabel("Bicycle Model"));
		bicycleDetailsGrid.add(txtModel);
		bicycleDetailsGrid.add(new JLabel("Bicycle Colour"));
		bicycleDetailsGrid.add(txtColour);
		bicycleDetailsGrid.add(new JLabel("Bicycle Frame Size"));
		bicycleDetailsGrid.add(txtFrameSize);
		bicycleDetailsGrid.add(new JLabel("Bicycle Wheel Size"));
		bicycleDetailsGrid.add(txtWheelSize);
		bicycleDetailsGrid.add(new JLabel("Bicycle Frame Composition"));
		bicycleDetailsGrid.add(txtFrameComposition);
		bicycleDetailsGrid.add(new JLabel("Bicycle Cost Price"));
		bicycleDetailsGrid.add(txtCostPrice);
		bicycleDetailsGrid.add(new JLabel("Bicycle Sale Price"));
		bicycleDetailsGrid.add(txtSalePrice);
		
		return bicycleDetailsGrid;
		
		}
	public void checkFieldsComplete(){
		
		// get bicycle details from text fields
				String type = txtType.getText();
				String model = txtModel.getText();
				String colour = txtColour.getText();
				String frameSize = txtFrameSize.getText();
				String wheelSize = txtWheelSize.getText();
				String frameComposition = txtFrameComposition.getText();
				String costPrice = txtCostPrice.getText();
				String salePrice = txtSalePrice.getText();
				
				int intFrameSize;
				int intWheelSize;
				double doubleCostPrice;
				double doubleSalePrice;
				intFrameSize = Integer.parseInt(frameSize);
				intWheelSize = Integer.parseInt(wheelSize);
				doubleCostPrice = Double.parseDouble(costPrice);
				doubleSalePrice = Double.parseDouble(salePrice);
				
				
				// check if any text fields have been left blank and output error message if true
				if(type.length() == 0 || model.length() == 0 || colour.length() == 0 
						|| frameSize.length() == 0 || wheelSize.length() == 0 || frameComposition.length() == 0 || costPrice.length() == 0 || salePrice.length() == 0 ) {
					JOptionPane.showMessageDialog(null, "Please Enter All Details", 
							"Incomplete content", JOptionPane.ERROR_MESSAGE);
				}
				// attempt to add bicycle if all fields are complete
				else if(type.equals("BMX")){
				
					database.addBicycle(new BMX(model, colour, intFrameSize, intWheelSize,frameComposition, doubleCostPrice, doubleSalePrice));
					addBicycle (model, colour, intFrameSize, intWheelSize,frameComposition,doubleCostPrice,doubleSalePrice);
				}
				else if(type.equals("Cruiser"))
				{
					database.addBicycle(new Cruiser(model, colour, intFrameSize, intWheelSize,frameComposition, doubleCostPrice, doubleSalePrice));
					addBicycle (model, colour, intFrameSize, intWheelSize,frameComposition,doubleCostPrice,doubleSalePrice);
				}
		}
	
	public void addBicycle(String model,String colour,int intFrameSize,int intWheelSize,String frameComposition, double doubleCostPrice,double doubleSalePrice  ){
		
			
		//if(type.equals("BMX")){
			
		//database.addBicycle(new BMX(model, colour, intFrameSize, intWheelSize,frameComposition, doubleCostPrice, doubleSalePrice));
		
		//}
		txtType.setText("");
		txtModel.setText("");
		txtColour.setText("");
		txtFrameSize.setText("");
		txtWheelSize.setText("");
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
				checkFieldsComplete();
			System.out.println("button pressed");
			}
			
				
			
	
		}
	}
	
}

	
