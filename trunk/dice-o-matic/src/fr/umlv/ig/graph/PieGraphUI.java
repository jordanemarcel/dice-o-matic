package fr.umlv.ig.graph;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.table.TableModel;
/**
 * This is a implementation of @link fr.umlv.ig.graph.GraphUI
 * This implementation is a UI for @link fr.umlv.ig.graph.JGraph
 * it display the JGraph as a pie chart
 * @author Clement Lebreton & Jordane Marcel
 */
public class PieGraphUI extends GraphUI{
	private final JGraph graph;
	private Dimension preferedSize;
	
	private final LinkedList<Ellipse2D> circleList;
	private final HashMap<Ellipse2D, String[]> circleMap;
	private BufferedImage legend;
	private Rectangle legendShape;
	private int circleIndex;
	public PieGraphUI(JComponent c){
		graph = (JGraph) c;
		Insets insets = c.getInsets();
		int h = 200-insets.top-insets.bottom;
		int separator = h/10;
		TableModel model = graph.getModel();
		int row = model.getRowCount();
		preferedSize = new Dimension(row*3*h/4+(row+2)*separator,200);
		circleList = new LinkedList<Ellipse2D>();
		circleMap = new HashMap<Ellipse2D, String[]>();
		legend = null;
		legendShape = null;
		
		graph.addPropertyChangeListener("rethrow",new PropertyChangeListener(){
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				circleList.clear();
				circleMap.clear();
				legendShape=null;
				legend=null;
				TableModel model = graph.getModel();
				int row = model.getRowCount();
				Insets insets = graph.getInsets();
				int h = 200-insets.top-insets.bottom;
				int separator = h/10;
				preferedSize = new Dimension(row*3*h/4+(row+2)*separator,200);
			}
		});
	}
	/**
	 * Configures the specified component appropriate for the look and feel.
	 * This install a mouse listener which is responsible for paint legend on right click.
	 * @param c the component where this UI delegate is being installed    
	 */
	@Override
	public void installUI(JComponent c) {
		graph.addMouseListener(mouseLegendAdapter);
	}
	/**
	 * Reverses configuration which was done on the specified component during installUI.
	 * @param c the component from which this UI delegate is being removed.
	 */
	@Override
	public void uninstallUI(JComponent c) {
		graph.removeMouseListener(mouseLegendAdapter);
	}
	/**
	 * This method is called for create the UI of a component
	 * @param component Create an UI for the given component
	 * @return the created UI
	 */
	public static ComponentUI createUI(JComponent c){
		return new PieGraphUI(c);
	}
	MouseAdapter mouseLegendAdapter = new MouseAdapter(){
		@Override
		public void mousePressed(MouseEvent e) {
			if(e.getButton()==MouseEvent.BUTTON3){
				for(Ellipse2D circle : circleList){
					if(legendShape !=null && legendShape.contains(e.getPoint())){
						legend = null;
						legendShape = null;
						e.getComponent().repaint();
						return;
					}
					if(circle.contains(e.getPoint())){
						BufferedImage img = new BufferedImage(100,100,BufferedImage.TYPE_INT_ARGB);
						Graphics2D imgG = (Graphics2D) img.getGraphics();
						FontMetrics fm = imgG.getFontMetrics();
						Color c = new Color(200,250,0,200);
						imgG.setColor(c);
						imgG.fillRect(0, 0, 100, 100);
						imgG.setColor(Color.BLACK);
						imgG.drawRect(0, 0, 99, 99);
						int col = graph.getModel().getColumnCount();
						String text[] = circleMap.get(circle);
						int maxWidth = -1;
						for(int i=0;i<col;i++){
							int tmp;
							if ((tmp = fm.stringWidth(text[i]))>maxWidth)
								maxWidth = tmp;
							imgG.drawString(text[i], 5, 15+15*i);
						}
						
						imgG.dispose();
						legend=img;
						legendShape = new Rectangle(100,100);
						legendShape.setLocation(e.getPoint());
						circleIndex = circleList.indexOf(circle);
						e.getComponent().repaint();
						return;
					}	
				}
			}
		}
	};
	/**
	 * Returns the specified component's preferred size appropriate for the look and feel
	 * @param c the component whose preferred size is being queried; this argument is often
	 * @return a Dimension object with the preferred size
	 */
	@Override
	public Dimension getPreferredSize(JComponent c) {
		return preferedSize;
	}

	/**
	 * Paints the component UI. This UI is a JGraph paint as a pie chart 
	 * @param g paints the component of this graphics
	 * @param c paints this component on the graphics g
	 */
	@Override
	public void paint(Graphics g, JComponent c) {
		Graphics2D g2 = (Graphics2D)g;
		TableModel model = graph.getModel();
		Insets insets = c.getInsets();
		Dimension d = preferedSize;
		int w = d.width-insets.left-insets.right;
		int h = c.getHeight()-insets.top-insets.bottom;
		int col = model.getColumnCount();
		int row = model.getRowCount();
		double startAngle = 0;
		double arcAngle = 0;
		int graphRaduis = h-h/4;
		int separator = h/10;
		int xPos = separator;
		int yPos = (h-graphRaduis)/2;
		int sum = 0;
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, w, h);
		for(int j = 0; j<row; j++){
			Integer val = null;
			for(int i = 0; i<col; i++){
				val = (Integer)model.getValueAt(j,i);
				if(val==null)
					break;
				sum = sum + val;
			}
			Ellipse2D circle = new Ellipse2D.Double(xPos,yPos,graphRaduis,graphRaduis);
			circleList.add(circle);
			String[] legendText = new String[model.getColumnCount()];
			for(int i = 0; i<col; i++){
				val = (Integer)model.getValueAt(j,i);
				if(val==null)
					break;
				arcAngle = (val*360)/sum ;
				g2.setColor(graph.getColor());
				if(i==col-1)
					arcAngle=360-startAngle;
				g2.fillArc(xPos, yPos, graphRaduis, graphRaduis,(int)startAngle, (int)arcAngle);
				startAngle = startAngle + arcAngle;
				legendText[i]=i+1+" :"+val+"=>"+roundOff((double)val*100/(double)sum)+"%";
			}
			circleMap.put(circle, legendText);
			if(val==null)
				break;
			xPos = graphRaduis + xPos + separator;
			startAngle = 0;
			sum = 0;
		}
		if(legend!=null){
			Ellipse2D cir = circleList.get(circleIndex);
			g2.setColor(new Color(200,200,10,100));
			g2.fillRect((int)cir.getX(), (int)cir.getY(), (int)cir.getWidth(), (int)cir.getHeight());
			g2.setColor(Color.BLACK);
			g2.drawRect((int)cir.getX(), (int)cir.getY(), (int)cir.getWidth(), (int)cir.getHeight());
			if((legendShape.y+legendShape.height)>c.getHeight())
				legendShape.y = c.getHeight()-legendShape.height;
			if((legendShape.x+legendShape.width)>c.getWidth())
				legendShape.x = c.getWidth()-legendShape.width;
			g2.drawImage(legend, legendShape.x, legendShape.y, null);
			g2.drawRect(legendShape.x, legendShape.y, legendShape.width, legendShape.height);
		}
	}
	private double roundOff(double val) {return (Math.floor(val*100.0))/100;} 
}
