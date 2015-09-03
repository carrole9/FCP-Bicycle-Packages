
	package bicyclestore.cardlayouts.profitandlosscardlayout;

import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.Caret;

import bicyclestore.Database;
import bicyclestore.SystemData;
//import bicyclestore.cardlayouts.profitandlosscardlayout.ProfitAndLossGraph.BarChartComponent;
import bicyclestore.transaction.ProfitAndLoss;
 
public class ProfitAndLossCard implements ItemListener {
	
    JPanel cardLayoutPane, cards; //a panel that uses CardLayout
    final static String TEXTPANEL = "Profit And Loss Accounts";
    final static String GRAPHPANEL = "Profit And Loss Graph";
    final static String PredictionPANEL = "Sales Forcast";
    JLabel expenditure, income, surplus, deficit, breakeven;
	JLabel purchases, wages, bills, sales, totalexpenditure, totalincome;
	private static Database data;
	private ProfitAndLossGraphIcon graph;
	
	public ProfitAndLossCard(Database data) {
		this.data=data;
		cardLayoutPane = new JPanel(new BorderLayout());
		graph= new ProfitAndLossGraphIcon(null, 0, 0, data);
		SetUpTable();
		
	}
     
    public void SetUpTable() {
    	ProfitAndLoss account= new ProfitAndLoss(data);
    	account.updateAccounts();
    	
        JPanel comboBoxPane = new JPanel(); //use FlowLayout
        String comboBoxItems[] = { TEXTPANEL, GRAPHPANEL };
        JComboBox<String> cb = new JComboBox<String>(comboBoxItems);
        cb.setEditable(false);
        cb.addItemListener(this);
        comboBoxPane.add(cb);
         
        //Create the "cards".
        JPanel card1 = new JPanel();
        JPanel card2 = new JPanel();
        //graph = new ProfitAndLossGraphIcon(null, 0, 0, data);
        card2.add(graph.createAndShowGUI());
        
        JPanel card3 = new JPanel();
         
        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        cards.add(card1, TEXTPANEL);
        cards.add(card2, GRAPHPANEL);
     
         
        cardLayoutPane.add(comboBoxPane, BorderLayout.PAGE_START);
        cardLayoutPane.add(cards, BorderLayout.CENTER);
        
        expenditure = new JLabel("--------EXPENDITURE----------- ");
        purchases = new JLabel("Purchases from Supplier: ");
        wages = new JLabel("Employees Wages: ");
		bills = new JLabel("Bills:");
		totalexpenditure = new JLabel("Total Expenditure:");
		income = new JLabel("--------INCOME---------------- ");
		sales = new JLabel("From sale of items: ");
		totalincome = new JLabel("Total Income:");
		surplus = new JLabel("--------BALANCE SURPLUS-------- ");
		deficit = new JLabel("--------BALANCE DEFICIT-------- ");
        DecimalFormat df = new DecimalFormat("0.00");
        if(account.getOverallBalance()>0){
        card1.add(expenditure);
       	 expenditure.setBackground(Color.LIGHT_GRAY);
       	 expenditure.setOpaque(true);
        card1.add(purchases);
        card1.add(new JTextField("€" + df.format(account.getPurchasingTransactionValue()), 15)).setFocusable(false);
        card1.add(wages);
        card1.add(new JTextField("€" + df.format(account.getEmployeeWages()), 15)).setFocusable(false);
        card1.add(bills);
	    card1.add(new JTextField("€" + df.format(account.getBills()), 15));
	    card1.add(totalexpenditure);
	    card1.add(new JTextField("€" + df.format(account.getTotalExpenditure()), 15)).setFocusable(false);
		card1.add(income );
		card1.add(sales);
		card1.add(new JTextField("€" + df.format(account.getTotalIncome()), 15)).setFocusable(false);
		card1.add(totalincome);
	    card1.add(new JTextField("€" + df.format(account.getTotalIncome()), 15)).setFocusable(false);
		card1.add(surplus);
		card1.add(new JTextField("€" + df.format(account.getOverallBalance()), 15)).setFocusable(false);
		card1.setLayout(new BoxLayout(card1, BoxLayout.Y_AXIS));
        }
        
        else if(account.getOverallBalance()<0){
        	card1.add(expenditure);
        	expenditure.setBackground(Color.LIGHT_GRAY);
        	expenditure.setOpaque(true);
            card1.add(purchases);
            card1.add(new JTextField("€" + df.format(account.getPurchasingTransactionValue()), 15)).setFocusable(false);
            card1.add(wages);
            card1.add(new JTextField("€" + df.format(account.getEmployeeWages()), 15)).setFocusable(false);
            card1.add(bills);
     	    card1.add(new JTextField("€" + df.format(account.getBills()), 15)).setFocusable(false);
     	    card1.add(totalexpenditure);
   	        card1.add(new JTextField("€" + df.format(account.getTotalExpenditure()), 15)).setFocusable(false);
     		card1.add(income );
     		income.setBackground(Color.LIGHT_GRAY);
       	    income.setOpaque(true);
     		card1.add(sales);
     		card1.add(new JTextField("€" + df.format(account.getTotalIncome()), 15)).setFocusable(false);
     		card1.add(totalincome);
   	        card1.add(new JTextField("€" + df.format(account.getTotalIncome()), 15)).setFocusable(false);
    		card1.add(deficit);
    		deficit.setBackground(Color.LIGHT_GRAY);
    		deficit.setOpaque(true);
    		card1.add(new JTextField("€" + df.format(account.getOverallBalance()), 15)).setFocusable(false);
    		card1.setLayout(new BoxLayout(card1, BoxLayout.Y_AXIS));
        }
        else if(account.getOverallBalance()==0){
        	 card1.add(expenditure);
        	 expenditure.setBackground(Color.LIGHT_GRAY);
         	 expenditure.setOpaque(true);
             card1.add(purchases);
             card1.add(new JTextField("€" + df.format(account.getPurchasingTransactionValue()), 15)).setFocusable(false);
             card1.add(wages);
             card1.add(new JTextField("€" + df.format(account.getEmployeeWages()), 15)).setFocusable(false);
             card1.add(bills);
     	    card1.add(new JTextField("€" + df.format(account.getBills()), 15));
     	    card1.add(totalexpenditure);
   	        card1.add(new JTextField("€" + df.format(account.getTotalExpenditure()), 15)).setFocusable(false);
     		card1.add(income );
     		income.setBackground(Color.LIGHT_GRAY);
       	    income.setOpaque(true);
     		card1.add(sales);
     		card1.add(new JTextField("€" + df.format(account.getTotalIncome()), 15)).setFocusable(false);
     		card1.add(totalincome);
   	        card1.add(new JTextField("€" + df.format(account.getTotalIncome()), 15)).setFocusable(false);
    		card1.add(breakeven);
    		breakeven.setBackground(Color.LIGHT_GRAY);
    		breakeven.setOpaque(true);
    		card1.add(new JTextField("€" + df.format(account.getOverallBalance()), 15)).setFocusable(false);
    		card1.setLayout(new BoxLayout(card1, BoxLayout.Y_AXIS));
        }
        
   
    
        
    }
     
    public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String)evt.getItem());
    }
     
  
   public void orderAdded(){
	ProfitAndLoss profit = new ProfitAndLoss(data);
    profit.updateAccounts();
    System.out.println(profit.getOverallBalance());
	cardLayoutPane.removeAll();
	cardLayoutPane.repaint();
	SetUpTable();
	graph.refresh();
   }
   
   
   public void invoiceAdded(){
		ProfitAndLoss profit = new ProfitAndLoss(data);
	    profit.updateAccounts();
	    System.out.println(profit.getOverallBalance());
		cardLayoutPane.removeAll();
		cardLayoutPane.repaint();
		SetUpTable();
	   graph.refresh();
   }
    
    public JPanel getStockControlCardLayout() {
    	return cardLayoutPane;
    }
    

}