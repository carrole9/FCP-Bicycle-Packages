package bicyclestore;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import bicyclestore.bikes.Bicycle;
import bicyclestore.staff.Employee;
import bicyclestore.transaction.StockControl;

public class LoginGUI {
	
	private Database database;

	private JFrame frame;
	private JPanel logInPanel;
	private JLabel lblStaffId, lblPassword;
	private JTextField txtStaffId;
	private JPasswordField txtPassword;
	private JButton btnSubmit;
	
	
	public LoginGUI() {
		
		database = new Database();

		// load database with hard coded values from SystemData class
		SystemData systemData = new SystemData(database);
		systemData.fillDatabase();
		
		initComponents();
		createLogInPanel();
		btnSubmit.addActionListener(new ButtonClickListener());
		frame.setVisible(true);
	}
	
	// Alternative constructor to allow persistence between log ins
	// database can be passed back when logging out
	public LoginGUI(Database database) {
		this.database = database;
		
		initComponents();
		createLogInPanel();
		btnSubmit.addActionListener(new ButtonClickListener());
		frame.setVisible(true);
	}
	
	private void initComponents() {
		frame = new JFrame("Bicycle Store System");
		frame.setSize(400, 400);
		//frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // set full screen
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		lblStaffId = new JLabel("Staff ID: ");
		lblPassword = new JLabel("Password: ");
		txtStaffId = new JTextField(10);
		txtStaffId.addActionListener(new ButtonClickListener());
		txtPassword = new JPasswordField(10);
		txtPassword.addActionListener(new ButtonClickListener());
		btnSubmit = new JButton("Submit");
		btnSubmit.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JPanel mainPanel = new JPanel();
		logInPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		frame.add(mainPanel, BorderLayout.NORTH);
		
		// empty panel with empty border to centre log in grid in frame
		JPanel headerPanel = new JPanel();
		headerPanel.setBorder(BorderFactory.createEmptyBorder(60,60,60,60));

		mainPanel.add(headerPanel);
		mainPanel.add(logInPanel);
		mainPanel.add(btnSubmit);
	}
	
	private void createLogInPanel() {
		TitledBorder titleBorder = BorderFactory.createTitledBorder("Enter Log in details");
		titleBorder.setTitleJustification(TitledBorder.CENTER);
		
		logInPanel.setMaximumSize(new Dimension(250, 100));
		logInPanel.setBorder(titleBorder);
		GridLayout layout = new GridLayout(2,2);
		layout.setHgap(10);
		layout.setVgap(10);
		logInPanel.setLayout(layout);
		
		logInPanel.add(lblStaffId);
		logInPanel.add(txtStaffId);
		logInPanel.add(lblPassword);
		logInPanel.add(txtPassword);
	}
	
	private void attemptLogin(String strId, char[] strPwd) {
		int staffId = -1;
		boolean gotStaffId = false;
		try{
			// attempt to parse staff id integer from string
			staffId = Integer.parseInt(strId);
			gotStaffId = true;
		}
		catch(Exception e) {
			// catch block will be executed if non numeric characters were entered into staff id field
			JOptionPane.showMessageDialog(null, "Staff id should only contain numeric characters" , 
					"Incorrect Format", JOptionPane.ERROR_MESSAGE);
		}
		if(gotStaffId) {
			// check if valid staff id and password
			if(database.isValidEmployee(staffId, strPwd)) {
				// Log user in to system
				Employee employee = database.getEmployee(staffId);
				GUIDriver mainPage = new GUIDriver(employee, database);
				mainPage.setVisible(true);
				frame.dispose();
			}
			else {
				JOptionPane.showMessageDialog(null, "Staff id or password incorrect", 
					"Log in failed", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	
	public static void main(String[] args) {
	
	new LoginGUI();
	}
	
	private class ButtonClickListener implements ActionListener {
		

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// retrieve strings from text fields
			String idStr = txtStaffId.getText();
			char[] passStr = txtPassword.getPassword();
			
			// check if text fields have been left blank
			if(idStr.length() == 0 || passStr.length == 0) {
				// no staff id or password entered, display error dialogue
				JOptionPane.showMessageDialog(null, "You must enter a staff id and password" , 
						"Incomplete content", JOptionPane.ERROR_MESSAGE);
			}
			else {
				attemptLogin(idStr, passStr);
			}
		}
		
	}

}
