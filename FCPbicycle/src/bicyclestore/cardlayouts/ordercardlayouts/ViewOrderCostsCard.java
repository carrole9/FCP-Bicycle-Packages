package bicyclestore.cardlayouts.ordercardlayouts;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import bicyclestore.Database;

/* Class uses features from JFreeChart to draw Pie Charts
 * Libraries retrieved from http://www.jfree.org/jfreechart   */

public class ViewOrderCostsCard {
	
	private Database database;
	
	private JPanel cards;
	
	private ViewWeeklyCosts weeklyCosts;
	private ViewThisWeeksCosts thisWeeksCosts;
	
	private JButton btnThisWeeks, btnWeeklyCosts;
	
	public ViewOrderCostsCard(Database database) {
		this.database = database;
		createViewCostsCard();
	}
	
	private void createViewCostsCard() {
		cards = new JPanel();
		cards.setLayout(new CardLayout());
		
		btnThisWeeks = new JButton("View proportion of orders and costs this week");
		btnThisWeeks.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnThisWeeks.addActionListener(new ButtonListener());
		btnWeeklyCosts = new JButton("View weekly cost of orders over time");
		btnWeeklyCosts.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnWeeklyCosts.addActionListener(new ButtonListener());
		
		weeklyCosts = new ViewWeeklyCosts(database, btnThisWeeks);
		thisWeeksCosts = new ViewThisWeeksCosts(database, btnWeeklyCosts);
		
		JPanel card1 = weeklyCosts;
		JPanel card2 = thisWeeksCosts;
		
		cards.add(card1);
		cards.add(card2);
	}
	
	public JPanel getCardLayout() {
		return cards;
	}
	
	// update content on panel when a new order is added to the system
	public void newOrderAdded() {
		// update graphs in weekly cost and this weeks cost
		thisWeeksCosts.newOrderAdded();
		weeklyCosts.newOrderAdded();
	}
	
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == btnThisWeeks) {
				CardLayout c1 = (CardLayout) cards.getLayout();
				c1.next(cards);
			}
			
			if (e.getSource() == btnWeeklyCosts) {
				CardLayout c1 = (CardLayout) cards.getLayout();
				c1.previous(cards);
			}
		}
	}

}
