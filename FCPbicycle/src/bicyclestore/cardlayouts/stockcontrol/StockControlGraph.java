package bicyclestore.cardlayouts.stockcontrol;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JFrame;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;

import bicyclestore.Database;
import bicyclestore.SystemData;
import bicyclestore.transaction.StockControl;

public class StockControlGraph extends JPanel
{
    private int graphHeight = 200;
    private int barWidth = 50;
    private int barGap = 25;
    private JPanel graphPanel;
    private JPanel barPanel;
    private JPanel labelPanel;
    private GraphIcon icon;
    private List<Bar> bars = new ArrayList<Bar>();

    public StockControlGraph()
    {
    	//GraphIcon icon = new GraphIcon(null, 0, 0);
    	SetUpGraph();
    	//icon.createAndShowGUI();
    	
    }
    	 public void SetUpGraph(){
    	
        setBorder( new EmptyBorder(10, 10, 10, 10) );
        setLayout( new BorderLayout() );

        barPanel = new JPanel( new GridLayout(1, 0, barGap, 0) );
        Border inside= new EmptyBorder(10, 10, 0, 10);
        Border outside = new MatteBorder(1, 1, 1, 1, Color.BLACK);
        Border compound = new CompoundBorder(outside, inside);
        barPanel.setBorder(compound);

        labelPanel = new JPanel( new GridLayout(1, 0, barGap, 0) );
        labelPanel.setBorder( new EmptyBorder(10, 10, 0, 10) );

       add(barPanel, BorderLayout.CENTER);
       add(labelPanel, BorderLayout.PAGE_END);
		
    }

    public void addColumn(String label, int value, Color color)
    {
        Bar bar = new Bar(label, value, color);
        bars.add( bar );
    }
    
    public void removeColumn()
    {
        
        bars.clear();
    }

    public void layoutGraph()
    {
        barPanel.removeAll();
        labelPanel.removeAll();

        int maxValue = 0;

        for (Bar bar: bars)
            maxValue = Math.max(maxValue, bar.getValue());

        for (Bar bar: bars)
        {
            JLabel tag = new JLabel(bar.getValue() + "");
            tag.setHorizontalTextPosition(JLabel.CENTER);
            tag.setHorizontalAlignment(JLabel.CENTER);
            tag.setVerticalTextPosition(JLabel.TOP);
            tag.setVerticalAlignment(JLabel.BOTTOM);
            int barHeight = (bar.getValue() * graphHeight) / maxValue;
            GraphIcon icon = new GraphIcon(bar.getColor(), barWidth, barHeight);
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
        private int value;
        private Color color;

        public Bar(String tag, int value, Color color)
        {
            this.tag = tag;
            this.value = value;
            this.color = color;
        }

        public String getLabel()
        {
            return tag;
        }

        public int getValue()
        {
            return value;
        }

        public Color getColor()
        {
            return color;
        }
    }

}

 class GraphIcon implements Icon
    {
	 	private Color color;
        private int width;
        private int height;
        private Database database;
        private StockControlGraph panel;
        private JPanel graph;
        private StockControl stock;

        public GraphIcon(Color color, int width, int height)
        {
            this.color = color;
            this.width = width;
            this.height = height;
            stock = new StockControl();
            database=new Database();
            SystemData data=new SystemData(database);
    		data.fillDatabase();
    		 stock.calculateStock(null, database);
           
        }

        public int getIconWidth()
        {
            return width;
        }

        public int getIconHeight()
        {
            return height;
        }

        public void paintIcon(Component c, Graphics g, int x, int y)
        {
           
        	g.setColor(color);
            g.fillRect(x, y, width, height);
            g.setColor(Color.BLACK);
            g.fillRect(x + width , y , 0, height);
        }
    

    public Component createAndShowGUI()
    {
    	panel = new StockControlGraph();
    	
		
        panel.addColumn("BMX", stock.getBmx(), Color.RED);
        panel.addColumn("Motorised Bike", stock.getMotorizedBike(), Color.ORANGE);
        panel.addColumn("Mountain Bike", stock.getMountainBike(), Color.YELLOW);
        panel.addColumn("Road Bike", stock.getRoadBike(), Color.GREEN);
        panel.addColumn("Cruiser Bike", stock.getCruiserBike(), Color.PINK);
        panel.addColumn("Hybrid", stock.getHybridBike(), Color.BLUE);
        
        panel.layoutGraph();
		return panel;	
    }
    
    public void refreshCharts(){
    	
    	//stock = new StockControl();
    	//stock.clearStock();
    	//stock.calculateStock(null, database);
		//stock.predictions(database);
        //System.out.println(stock.getBmx());
    	panel.removeColumn();
    	panel.removeAll();
    	panel.repaint();
    	//panel.SetUpGraph();
    	//createAndShowGUI();
    	
    	
    	

    	/*//stock = new StockControl();
    	//stock.clearStock();
    	stock = new StockControl();
    	stock.calculateStock(null, database);
		stock.predictions(database);
        System.out.println(stock.getBmx());
    	panel.removeAll();
    	panel.revalidate();
    	panel.SetUpGraph();*/
    	//createAndShowGUI();
    }

    }
