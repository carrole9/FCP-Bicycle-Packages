package bicyclestore.cardlayouts.ordercardlayouts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

import bicyclestore.Database;
import bicyclestore.Utilities;
import bicyclestore.transaction.PurchasingTransaction;

@SuppressWarnings("serial")
public class ViewThisWeeksCosts extends JPanel {

	private Database database;

	private JPanel txtPanel, graphPanel;
	
	private JLabel lblTotalOrders, lblOrdersThisWeek, lblTotalCost, lblWeeksCost,
						totalOrdersLabel, thisWeeksOrdersLabel, totalCostLabel, weeksCostLabel;
	
	private JButton btnWeeklyCosts;
	
	private JFreeChart numbersChart, costChart;
	
	private int ordersThisWeek;
	
	public ViewThisWeeksCosts(Database database, JButton btnWeeklyCosts) {
		this.database = database;
		this.btnWeeklyCosts = btnWeeklyCosts;
		ordersThisWeek = 0;
		setUpTxtPanel();
		graphPanel = new JPanel();
		setUpGraphPanel();
		createViewCostsCard();
	}
	
	private void createViewCostsCard() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(txtPanel);
		this.add(graphPanel);
		this.add(btnWeeklyCosts);
	}
	
	private void setUpTxtPanel() {
		// set up border for text panel
		TitledBorder border = BorderFactory.createTitledBorder("Total orders and costs");
		border.setTitleJustification(TitledBorder.CENTER);
		
		// set up layout for text panel
		GridLayout txtPanelLayout = new GridLayout(4, 2);
		txtPanelLayout.setHgap(5);
		txtPanelLayout.setVgap(5);
		txtPanel = new JPanel(txtPanelLayout);
		txtPanel.setBorder(border);
		
		setUpTxtPanelLabels();
	}
	
	private void setUpGraphPanel() {
		// set border for graph panel
		TitledBorder border = BorderFactory.createTitledBorder("Proportions of orders this week");
		border.setTitleJustification(TitledBorder.CENTER);
		
		BoxLayout boxLayout = new BoxLayout(graphPanel, BoxLayout.X_AXIS);
		graphPanel.setLayout(boxLayout);
		graphPanel.setBorder(border);
		
		setNumberChartContent();
		ChartPanel numbersChartPanel = new ChartPanel(numbersChart);
		numbersChartPanel.setPreferredSize(new Dimension(400, 200));
		
		setCostChartContent();
		ChartPanel costChartPanel = new ChartPanel(costChart);
		costChartPanel.setPreferredSize(new Dimension(400,200));
		
		graphPanel.add(numbersChartPanel);
		graphPanel.add(costChartPanel);
	}
	
	private void setNumberChartContent() {
		// get values for numbers data set
		int totalOrders = database.getPurchasingTransactions().size();
		int difference = totalOrders - ordersThisWeek;
		double percentEarlierOrders = (double)difference/totalOrders * 100;
		double percentThisWeek = (double)ordersThisWeek/totalOrders * 100;
		
		// set up pie chart that displays proportions of orders this week
		PieDataset dataSet = createChartDataSet(percentEarlierOrders, percentThisWeek, "Earlier orders", "Orders this week");
		numbersChart = createPieChart(dataSet, "Proportions of orders this week");
	}
	
	private void setCostChartContent() {
		// set up pie chart that displays cost proportions of orders this week
		PieDataset costDataset = createChartDataSet(getPercentEarlierCost(), getPercentCostThisWeek(),
				"Cost of earlier orders","Cost of this weeks orders");
		costChart = createPieChart(costDataset, "Cost of this weeks orders");
	}
	
	private void setUpTxtPanelLabels() {
		totalOrdersLabel = new JLabel("Total number of orders in system:", JLabel.CENTER);
		thisWeeksOrdersLabel = new JLabel("Number of orders this week:", JLabel.CENTER);
		totalCostLabel = new JLabel("Total cost of orders in system:", JLabel.CENTER);
		weeksCostLabel = new JLabel("Cost of this weeks orders:", JLabel.CENTER);
		
		totalOrdersLabel.setOpaque(true);
		totalOrdersLabel.setBackground(new Color(107,106,104));
		totalOrdersLabel.setForeground(Color.WHITE);
		thisWeeksOrdersLabel.setBackground(new Color(107,106,104));
		thisWeeksOrdersLabel.setForeground(Color.WHITE);
		thisWeeksOrdersLabel.setOpaque(true);
		totalCostLabel.setBackground(new Color(107,106,104));
		totalCostLabel.setForeground(Color.WHITE);
		totalCostLabel.setOpaque(true);
		weeksCostLabel.setBackground(new Color(107,106,104));
		weeksCostLabel.setForeground(Color.WHITE);
		weeksCostLabel.setOpaque(true);
		
		lblTotalOrders = new JLabel(database.getPurchasingTransactions().size()+"", JLabel.CENTER);
		lblOrdersThisWeek = new JLabel(getNoOrdersThisWeek()+"", JLabel.CENTER);
		lblTotalCost = new JLabel(String.format("€%.2f", getTotalCostOfOrders()), JLabel.CENTER);
		lblWeeksCost = new JLabel(String.format("€%.2f", getThisWeeksOrdersCost()), JLabel.CENTER);
		lblTotalOrders.setBackground(Color.LIGHT_GRAY);
		lblTotalOrders.setOpaque(true);
		lblOrdersThisWeek.setBackground(Color.LIGHT_GRAY);
		lblOrdersThisWeek.setOpaque(true);
		lblTotalCost.setBackground(Color.LIGHT_GRAY);
		lblTotalCost.setOpaque(true);
		lblWeeksCost.setBackground(Color.LIGHT_GRAY);
		lblWeeksCost.setOpaque(true);
		
		// add labels to panel
		txtPanel.add(totalOrdersLabel);
		txtPanel.add(lblTotalOrders);
		txtPanel.add(thisWeeksOrdersLabel);
		txtPanel.add(lblOrdersThisWeek);
		txtPanel.add(totalCostLabel);
		txtPanel.add(lblTotalCost);
		txtPanel.add(weeksCostLabel);
		txtPanel.add(lblWeeksCost);
	}
	
	private double  getTotalCostOfOrders() {
		double totalCost = 0.0;
		for(PurchasingTransaction order : database.getPurchasingTransactions()) {
			totalCost += order.getTransactionCost();
		}
		return totalCost;
	}
	
	public double getThisWeeksOrdersCost() {
		double totalCost = 0.0;
		for(PurchasingTransaction order : database.getPurchasingTransactions()) {
			if(Utilities.isDateInCurrentWeek(order.getTransactionDate()))
					totalCost += order.getTransactionCost();
		}
		return totalCost;
	}
	
	public int getNoOrdersThisWeek() {
		int noOrders = 0;
		// set start of week object to preceding Monday at 00:00:00
		Calendar startOfWeek = Calendar.getInstance();
		startOfWeek.set(Calendar.DAY_OF_YEAR, (Calendar.DAY_OF_YEAR-Calendar.DAY_OF_WEEK));
		for(PurchasingTransaction order : database.getPurchasingTransactions()) {
			if(Utilities.isDateInCurrentWeek(order.getTransactionDate())) {
				noOrders++;
			}
		}
		ordersThisWeek = noOrders;
		return noOrders;
	}
	
	public double getPercentEarlierCost() {
		double totalCost = 0.0;
		double earlierCost = 0.0;
		for(PurchasingTransaction order : database.getPurchasingTransactions()) {
			if(!Utilities.isDateInCurrentWeek(order.getTransactionDate())) {
				earlierCost += order.getTransactionCost();
			}
			totalCost += order.getTransactionCost();
		}
		return (double)earlierCost/totalCost *100;
	}
	
	public double getPercentCostThisWeek() {
		double weeksCost = 0.0;
		double totalCost = 0.0;
		for(PurchasingTransaction order : database.getPurchasingTransactions()) {
			if(Utilities.isDateInCurrentWeek(order.getTransactionDate())) {
				weeksCost += order.getTransactionCost();
			}
			totalCost += order.getTransactionCost();
		}
		return (double)weeksCost/totalCost *100;
	}
	
	// create data set for JFreeChart pie chart
	private PieDataset createChartDataSet(double proportion1, double proportion2, String key1, String key2) {
		DefaultPieDataset dataSet = new DefaultPieDataset();
		dataSet.setValue(key1, proportion1);
		dataSet.setValue(key2, proportion2);
		return dataSet;
	}
	
	// creates JFreeChart pie chart with data set
	private JFreeChart createPieChart(PieDataset dataSet, String title) {
		JFreeChart chart = ChartFactory.createPieChart3D(title, // chart title
				dataSet, // data
				true, // include legend
				true, false);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		return chart;
	}
	
	// update content on panel when a new order is added to the system
	public void newOrderAdded() {
		lblOrdersThisWeek.setText(getNoOrdersThisWeek()+"");
		lblTotalOrders.setText(database.getPurchasingTransactions().size()+"");
		lblWeeksCost.setText(String.format("€%.2f", getThisWeeksOrdersCost()));
		lblTotalCost.setText(String.format("€%.2f", getTotalCostOfOrders()));
		refreshCharts();
	}
	
	private void refreshCharts() {
	    graphPanel.removeAll();
	    graphPanel.revalidate(); // This removes the old charts  
	    setUpGraphPanel();
	}

}
