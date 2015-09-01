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
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import bicyclestore.Database;
import bicyclestore.Utilities;
import bicyclestore.transaction.PurchasingTransaction;

@SuppressWarnings("serial")
public class ViewWeeklyCosts extends JPanel {

	private Database database;
	
	private JButton btnThisWeeksCosts;
	
	private JLabel lblAverage, lblAverageSixWeeks;

	private JPanel txtPanel, graphPanel;
	private JFreeChart lineChart;
	
	public ViewWeeklyCosts(Database database, JButton btnThisWeeks) {
		this.database = database;
		this.btnThisWeeksCosts = btnThisWeeks;
		createWeeklyCostsCard();
	}
	
	private void createWeeklyCostsCard() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		txtPanel = new JPanel();
		graphPanel = new JPanel();
		setUpGraphPanel();
		setUpTxtPanel();
		
		this.add(txtPanel);
		this.add(graphPanel);
		this.add(btnThisWeeksCosts);
	}
	
	private void setUpTxtPanel() {
		TitledBorder txtBorder = BorderFactory.createTitledBorder("Average weekly costs");
		txtBorder.setTitleJustification(TitledBorder.CENTER);
		
		GridLayout grid = new GridLayout(2,2);
		grid.setHgap(5);
		grid.setVgap(5);
		
		txtPanel.setBorder(txtBorder);
		txtPanel.setLayout(grid);
		
		JLabel averageHeading = new JLabel("Weekly Average (all time)");
		averageHeading.setOpaque(true);
		averageHeading.setBackground(new Color(107,106,104));
		averageHeading.setForeground(Color.WHITE);
		lblAverage = new JLabel(String.format("€%.2f", getWeeklyAverage()));
		lblAverage.setBackground(Color.LIGHT_GRAY);
		lblAverage.setOpaque(true);
		JLabel averageSixHeading = new JLabel("Weekly Average (last six weeks)");
		averageSixHeading.setOpaque(true);
		averageSixHeading.setBackground(new Color(107,106,104));
		averageSixHeading.setForeground(Color.WHITE);
		lblAverageSixWeeks = new JLabel(String.format("€%.2f", getLastSixWeeksAverage()));
		lblAverageSixWeeks.setBackground(Color.LIGHT_GRAY);
		lblAverageSixWeeks.setOpaque(true);
		
		txtPanel.add(averageHeading);
		txtPanel.add(lblAverage);
		txtPanel.add(averageSixHeading);
		txtPanel.add(lblAverageSixWeeks);
	}
	
	private double getWeeklyAverage() {
		int numWeeks = 0;
		// count number of weeks from first order to current date
		Calendar now = Calendar.getInstance();
		Calendar firstTransDate = Calendar.getInstance();
		firstTransDate.setTime(database.getPurchasingTransactions().get(0).getTransactionDate());
		while(firstTransDate.before(now)) {
			firstTransDate.add(Calendar.WEEK_OF_YEAR, 1);
			numWeeks++;
		}
		return Utilities.getTotalCostOfOrders(database)/numWeeks;
	}
	
	private double getLastSixWeeksAverage() {
		int numWeeks = 0;
		double totalCost = 0.0;
		// Count backwards to get last six weeks total cost
		Calendar now = Calendar.getInstance();
		int currentWeek = now.get(Calendar.WEEK_OF_YEAR);
		for(int i = database.getPurchasingTransactions().size()-1; i >= 0; i--) {
			PurchasingTransaction order = database.getPurchasingTransactions().get(i);
			Calendar orderDate = Calendar.getInstance();
			orderDate.setTime(order.getTransactionDate());
			int orderWeek = orderDate.get(Calendar.WEEK_OF_YEAR);
			if(orderWeek != currentWeek) {
				int numWeeksPassed = currentWeek - orderWeek;
				currentWeek -= numWeeksPassed;
				numWeeks += numWeeksPassed;
			}
			if(numWeeks > 6)
				break;
			totalCost += order.getTransactionCost();
			
		}
		return totalCost / 6;
	}
	
	private void setUpGraphPanel() {
		TitledBorder graphBorder = BorderFactory.createTitledBorder("Last six weeks order costs");
		graphBorder.setTitleJustification(TitledBorder.CENTER);
		
		DefaultCategoryDataset dataSet = createDataset(calculateWeeklyCosts());
		lineChart = createLineChart("Cost of orders per week", dataSet);
		
		ChartPanel chartPanel = new ChartPanel(lineChart);
		chartPanel.setPreferredSize(new Dimension(560, 300));
		
		graphPanel.add(chartPanel);
		graphPanel.setBorder(graphBorder);
	}
	
	// Calculate the cost for each week over the last 6 weeks
	private double[] calculateWeeklyCosts() {
		double[] weekCosts = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		Calendar now = Calendar.getInstance();
		int currentWeek = now.get(Calendar.WEEK_OF_YEAR);
		
		int j = weekCosts.length-1;
		
		// go backwards through orders and add cost of each week
		for(int i = database.getPurchasingTransactions().size()-1; i >= 0; i--) {
			PurchasingTransaction order = database.getPurchasingTransactions().get(i);
			Calendar orderDate = Calendar.getInstance();
			orderDate.setTime(order.getTransactionDate());
			
			if(orderDate.get(Calendar.WEEK_OF_YEAR) == currentWeek) {
				weekCosts[j] += order.getTransactionCost();
			}
			else {
				// if transaction date is earlier, go back to previous array index
				int numWeeksPassed = currentWeek - orderDate.get(Calendar.WEEK_OF_YEAR);
				currentWeek -= numWeeksPassed;
				j -= numWeeksPassed;
				if(j >= 0) 
					weekCosts[j] += order.getTransactionCost();
				else
					break; // if last 6 weeks have been passed break from loop
			}
		}
		return weekCosts;
	}

	private DefaultCategoryDataset createDataset(double[] weekCosts) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		Calendar now = Calendar.getInstance();
		int firstWeek = now.get(Calendar.WEEK_OF_YEAR)-(weekCosts.length-1);
		
		for(int i = 0; i < weekCosts.length; i++) {
			dataset.addValue(weekCosts[i], "Order cost", "Week "+firstWeek);
			firstWeek++;
		}
		return dataset;
	}

	private JFreeChart createLineChart(String chartTitle, DefaultCategoryDataset dataSet) {
		JFreeChart lineChart = ChartFactory.createLineChart(chartTitle, "Weeks", "Cost of orders", dataSet,
				PlotOrientation.VERTICAL, true, true, false);

		return lineChart;
	}

	// update content on panel when a new order is added to the system
	public void newOrderAdded() {
		lblAverage.setText(String.format("€%.2f", getWeeklyAverage()));
		lblAverageSixWeeks.setText(String.format("€%.2f", getLastSixWeeksAverage()));
		refreshCharts();
	}

	private void refreshCharts() {
		graphPanel.removeAll();
		graphPanel.revalidate(); // This removes the old charts
		setUpGraphPanel();
	}

}
