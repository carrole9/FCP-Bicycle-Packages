package bicyclestore.cardlayouts.profitandlosscardlayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.text.DecimalFormat;

import javax.swing.Icon;
import javax.swing.JFrame;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;

import bicyclestore.Database;
import bicyclestore.SystemData;
import bicyclestore.transaction.ProfitAndLoss;
import bicyclestore.transaction.StockControl;
//import bicyclestore.transaction.profitControl;

public class ProfitAndLossGraph extends JPanel
{
    private int graphHeight = 200;
    private int barWidth = 50;
    private int barGap = 25;

    private JPanel barPanel;
    private JPanel labelPanel;
    
    private Database data;
    private ProfitAndLossGraphIcon graph;

    private List<Bar> bars = new ArrayList<Bar>();

    public ProfitAndLossGraph(Database data){
    	this.data=data;
    	//graph= new ProfitAndLossGraphIcon(null, 0, 0, data);
    	setUpGraph();
    	
    	
    }
    public void setUpGraph(){
    	this.data=data;
    	setBorder( new EmptyBorder(0, 0, 0, 0) );
       // setBorder( new EmptyBorder(10, 10, 10, 10) );
        setLayout( new BorderLayout() );

        barPanel = new JPanel( new GridLayout(1, 0, barGap, 0) );
        Border inside= new EmptyBorder(0, 0, 0, 0);
        Border outside = new MatteBorder(1, 1, 1, 1, Color.BLACK);
        Border compound = new CompoundBorder(outside, inside);
        barPanel.setBorder(compound);

        labelPanel = new JPanel( new GridLayout(1, 0, barGap, 0) );
        labelPanel.setBorder( new EmptyBorder(10, 10, 0, 10) );

        add(barPanel, BorderLayout.CENTER);
        add(labelPanel, BorderLayout.PAGE_END);
        
        
    }

    public void addColumn(String label, double value, Color color)
    {
        Bar bar = new Bar(label, value, color);
        bars.add( bar );
    }

    public void layoutGraph()
    {
        barPanel.removeAll();
        labelPanel.removeAll();

        int maxValue = 0;

        for (Bar bar: bars)
            maxValue = (int) Math.max(maxValue, bar.getValue());

        for (Bar bar: bars)
        {
            JLabel tag = new JLabel("");
            tag.setHorizontalTextPosition(JLabel.CENTER);
            tag.setHorizontalAlignment(JLabel.CENTER);
            tag.setVerticalTextPosition(JLabel.TOP);
            tag.setVerticalAlignment(JLabel.BOTTOM);
            double barHeight = (bar.getValue() * graphHeight) / maxValue;
            ProfitAndLossGraphIcon icon = new ProfitAndLossGraphIcon(bar.getColor(), barWidth, barHeight, data);
            tag.setIcon( icon );
            barPanel.add( tag );

            JLabel barLabel = new JLabel( bar.getLabel() );
            barLabel.setHorizontalAlignment(JLabel.CENTER);
            labelPanel.add( barLabel );
            
            
           
        }
    }

    private class Bar
    {
        private String tag;
        private double value;
        private Color color;

        public Bar(String tag, double value, Color color)
        {
            this.tag = tag;
            this.value = value;
            this.color = color;
        }

        public String getLabel()
        {
            return tag;
        }
       

        public double getValue()
        {
            return value;
        }

        public Color getColor()
        {
            return color;
        }
    }

}

 class ProfitAndLossGraphIcon implements Icon
    {
	 	private Color color;
        private int width;
        private double height;
        private Database data;
        private ProfitAndLossGraph panel;

        public ProfitAndLossGraphIcon(Color color, int width, double barHeight, Database data)
        {
            this.color = color;
            this.width = width;
            this.height = barHeight;
            this.data=data;
            panel = new ProfitAndLossGraph(data);
        }

        public int getIconWidth()
        {
            return width;
        }

        public int getIconHeight()
        {
            return (int) height;
        }

        public void paintIcon(Component c, Graphics g, int x, int y)
        {
           
        	g.setColor(color);
            g.fillRect(x, y, width, (int) height);
            g.setColor(Color.BLACK);
            g.fillRect(x + width , y , 0, (int) height);
        }
    

    public Component createAndShowGUI()
    {   ProfitAndLoss profit = new ProfitAndLoss(data);
    	profit.updateAccounts();
    	DecimalFormat df = new DecimalFormat("0.00");
        double first=(int) Math.round(profit.getTotalExpenditure());
        double second=(int) Math.round(profit.getTotalIncome());
        double third=(int) profit.getOverallBalance();
        String expenditureAmount= df.format(profit.getTotalExpenditure());
        String incomeAmount=df.format(profit.getTotalIncome());
        String balanceAmount=df.format(profit.getOverallBalance());
               
        if(profit.getOverallBalance()>0){
        	String one = "Total Expenditure: € " + expenditureAmount;
        	String two = "Total Income: €" + incomeAmount;
        	String three = "Overall Profit: €" + balanceAmount;
        	panel.addColumn(one, first, Color.RED);
            panel.addColumn(two, second, Color.GREEN);
            panel.addColumn(three, third, Color.GREEN);
        }
        else if(profit.getOverallBalance()<0){
            third= Math.abs(third);
            String one = "Total Expenditure: €" + first;
        	String two = "Total Income: €" + incomeAmount;
        	String three = "Overall Loss: €" + third;
            panel.addColumn(one, first, Color.RED);
            panel.addColumn(two, second, Color.GREEN);
            panel.addColumn(three, third, Color.RED);
        	
        }
        else if(profit.getOverallBalance()==0){
        	 third= Math.abs(third);
             String one = "Total Expenditure: €" + expenditureAmount;
         	String two = "Total Income: €" + incomeAmount;
         	String three = "Break Even: " + balanceAmount;
             panel.addColumn(one, first, Color.RED);
             panel.addColumn(two, second, Color.GREEN);
             panel.addColumn(three, third, Color.RED);
        }
        
        
       
        
        panel.layoutGraph();
		return panel;	
    }
    
    public void refresh(){
    	ProfitAndLoss profit = new ProfitAndLoss(data);
     	profit.updateAccounts();
     	System.out.println(profit.getOverallBalance());
    	panel.removeAll();
    	panel.revalidate();
    	createAndShowGUI();
    }

    }
