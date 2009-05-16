package fr.umlv.ig.graph;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
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
 * it display the JGraph as a histogram
 * @author Clement Lebreton & Jordane Marcel
 */
public class HistogramGraphUI extends GraphUI{
	private final JGraph graph ;
	private Dimension preferedSize;
	private final LinkedList<Rectangle> recList;
	private final HashMap<Rectangle, String[]> recMap;
	private BufferedImage legend;
	private Rectangle legendShape;
	private int rectangleIndex=-1;
	private int xInset=20;
	public HistogramGraphUI(JComponent c) {
		graph = (JGraph) c;
		TableModel model = graph.getModel();
		int row = model.getRowCount();
		int col = model.getColumnCount();
		recList = new LinkedList<Rectangle>();
		recMap = new HashMap<Rectangle, String[]>();
		legend = null;
		legendShape = null;
		graph.addPropertyChangeListener("rethrow",new PropertyChangeListener(){
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				recList.clear();
				recMap.clear();
				legendShape=null;
				legend=null;
				TableModel model = graph.getModel();
				int row = model.getRowCount();
				int col = model.getColumnCount();
				preferedSize = new Dimension(row*col*15+(row*(col+2))*2+row*4+3*xInset,200);
			}
		});
		preferedSize = new Dimension(row*col*15+(row*(col+2))*2+row*4+3*xInset,200);
	}
	/**
	 * This method is called for create the UI of a component
	 * @param component Create an UI for the given component
	 * @return the created UI
	 */
	public static ComponentUI createUI(JComponent component){
		return new HistogramGraphUI(component);
	}
	
	
	private final MouseMotionListener mouseLegendAdapter = new MouseMotionListener() {
		
		
		@Override
		public void mouseMoved(MouseEvent e) {
			boolean over = false;
				for(Rectangle rectangle : recList){
//					if(legendShape !=null && legendShape.contains(e.getPoint())){
//						legend = null;
//						legendShape = null;
//						e.getComponent().repaint();
//						return;
//					}
					if(rectangle.contains(e.getPoint())){
						over = true;
						BufferedImage img = new BufferedImage(120,100,BufferedImage.TYPE_INT_ARGB);
						Graphics2D imgG = (Graphics2D) img.getGraphics();
						Color c = new Color(200,250,0,200);
						imgG.setColor(c);
						imgG.fillRect(0, 0, 120, 100);
						imgG.setColor(Color.BLACK);
						imgG.drawRect(0, 0, 119, 99);
						int col = graph.getModel().getColumnCount();
						String text[] = recMap.get(rectangle);
						for(int i=0;i<col;i++){
							imgG.drawString(text[i], 5, 15+15*i);
						}
						imgG.dispose();
						legend=img;
						legendShape = new Rectangle(120,100);
						Point p = e.getPoint();
						legendShape.setLocation(new Point(e.getX()+15,e.getY()+15));
						rectangleIndex = recList.indexOf(rectangle);
						e.getComponent().repaint(e.getX()-200, 0, 400, e.getComponent().getHeight());
						return;
					}	
				}
				if(!over) {
					legend = null;
					legendShape = null;
					e.getComponent().repaint();
					return;
				}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	};

	/**
	 * Configures the specified component appropriate for the look and feel.
	 * This install a mouse listener which is responsible for paint legend on right click.
	 * @param c the component where this UI delegate is being installed    
	 */
	@Override
	public void installUI(JComponent c) {
		graph.addMouseMotionListener(mouseLegendAdapter);
	}
	/**
	 * Reverses configuration which was done on the specified component during installUI.
	 * @param c the component from which this UI delegate is being removed.
	 */
	@Override
	public void uninstallUI(JComponent c) {
		graph.removeMouseMotionListener(mouseLegendAdapter);
	}

	/**
	 * Returns the specified component's preferred size appropriate for the look and feel
	 * @param c the component whose preferred size is being queried; this argument is often
	 * @return a Dimension object with the preferred size
	 */
	@Override
	public Dimension getPreferredSize(JComponent c) {
		return preferedSize;
	}
	private double roundOff(double val) {return (Math.floor(val*100.0))/100;} 
	
	/**
	 * Paints the component UI. This UI is a JGraph paint as a histogram 
	 * @param g paints the component of this graphics
	 * @param c paints this component on the graphics g
	 */
	@Override
	public void paint(Graphics g, JComponent c) {
		Insets insets = graph.getInsets();
		int w = graph.getWidth() - insets.right - insets.left;
		int h = graph.getHeight() - insets.bottom - insets.top;
		TableModel model = graph.getModel();
		int row = model.getRowCount();
		int col = model.getColumnCount();

		int barWidth = 15;
		int Ymarge=h/10;
		int separator = 2;
		int thickness=3;
		double max = -1;
		double maxPercent=0;
		int Xmarge=xInset*2;
		Graphics2D g2d =(Graphics2D) g;
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, w, h);	
		g2d.setColor(Color.BLACK);
		
		//draw X axis
		g2d.fillRect(Xmarge,h-Ymarge,w-3*xInset,thickness);
		//draw Y axis
		g2d.fillRect(Xmarge, Ymarge, thickness, h-(Ymarge*2));
		g2d.drawString("%", Xmarge-10, Ymarge-5);
		for(int i=0;i<row;i++){
			Integer val = null;
			int localMax = -1;
			int localSum = 0;
			for(int j = 0; j<col; j++){
				val = (Integer)model.getValueAt(i,j);
				if(val==null)
					continue;
				if(val>localMax)
					localMax=val;
				localSum = localSum + val;
			}
			if(localMax>max){
				max = localMax;
				maxPercent = (double)localMax*100/localSum;
			}
		}
		if(max<=0)//TODO
			return;
		for(int i=10;i<maxPercent;i=i+10){
			double tmp = i*max/maxPercent*(h-Ymarge*2);
			int barHeight = (int)tmp/(int)max;
			g2d.drawString(Integer.toString(i),xInset-5,h-Ymarge-barHeight+6 );
			g2d.fillRect(Xmarge-5,h-Ymarge-barHeight,13,thickness);
			g2d.fillRect(Xmarge, h-Ymarge-barHeight+1, w-xInset*3, thickness-2);
		}
		int currentX = Xmarge + separator*2;
		for(int i=0;i<row;i++){
			int start = currentX;
			int localMax = -1;
			String[] legendText = new String[col];
			for(int j=0;j<col;j++){
				Integer val = (Integer)model.getValueAt(i,j);
				if(val==null)
					continue;
				int tmp = val*(h-Ymarge*2);
				int barHeight = tmp/(int)max;
				if(barHeight>localMax)
					localMax = barHeight;
				g2d.setColor(graph.getColor());
				g2d.fillRect(currentX,h-Ymarge-barHeight,barWidth,barHeight);
				g2d.setColor(Color.black);
				g2d.drawString(Integer.toString(j+1), currentX+2, h-3);
				currentX = currentX + (barWidth+separator);
				legendText[j]=j+1+" :"+val+"=>"+roundOff(val*maxPercent/max)+"%";
			}
			if(recList.size()!=model.getRowCount()){
				Rectangle rec = new Rectangle(currentX-start,localMax);
				Color alphaColor = new Color(0,0,0,0);
				g2d.setColor(alphaColor);
				recList.add(rec);
				rec.setLocation(start, Ymarge+h-Ymarge*2-localMax);
				recMap.put(rec, legendText);
			}
			currentX = currentX + separator*2;
			g2d.setColor(Color.black);
			if(i!=row-1)
				g2d.fillRect(currentX-separator,h-Ymarge-h/100,separator/2,h/100*3);
			currentX = currentX + separator*2;
		}
		if(legend!=null){
			Rectangle rec = recList.get(rectangleIndex);
			g2d.setColor(new Color(200,200,10,100));
			g2d.fillRect(rec.x, rec.y, rec.width, rec.height);
			g2d.setColor(Color.BLACK);
			g2d.drawRect(rec.x, rec.y, rec.width, rec.height);
			if((legendShape.y+legendShape.height)>c.getHeight())
				legendShape.y = c.getHeight()-legendShape.height;
			if((legendShape.x+legendShape.width)>c.getWidth())
				legendShape.x = c.getWidth()-legendShape.width;
			g2d.drawImage(legend, legendShape.x, legendShape.y, null);
			g2d.drawRect(legendShape.x, legendShape.y, legendShape.width, legendShape.height);
		}
		
	}
}