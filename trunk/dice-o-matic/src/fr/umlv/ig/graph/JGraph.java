package fr.umlv.ig.graph;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.Scrollable;
import javax.swing.UIManager;
import javax.swing.table.TableModel;

@SuppressWarnings("serial")
public class JGraph extends JComponent implements Scrollable{
	public enum GraphType{
		HISTOGRAM{
			/* (non-Javadoc)
			 * @see fr.umlv.ig.graph.JGraph.GraphType#getUIClassID()
			 */
			@Override
			public String getUIClassID(){
				return "HistogramGraphUI";
			}
		},
		PIE{
			/* (non-Javadoc)
			 * @see fr.umlv.ig.graph.JGraph.GraphType#getUIClassID()
			 */
			@Override
			public String getUIClassID(){
				return "PieGraphUI";
			}
		};
		/**
		 * Get the UIClassID corresponding to the type
		 * @return the UIClassID of the type
		 */
		public abstract String getUIClassID();
	};
	private Color[] colors;
	private GraphType currentType;
	private TableModel model;
	private int colorIndex;
	public JGraph(TableModel model) {
		this(model,(Color[])null);
	}
	/**
	 * Instantiate a new JGraph
	 * @param model : The graph will be based on the {@see TableModel}, 
	 * each row is a separated sampling and each column is a different value on
	 * the graph
	 * @param colors : A table of {@see Color} witch will be use to draw Graph,
	 * for a best style the number of {@see Color} should be same as column number
	 */
	public JGraph(TableModel model, Color...colors) {
		this.model = model;
		if(colors!=null && colors.length>0){
			this.colors = colors;
		}else{
			this.colors = new Color[]{Color.BLUE,Color.RED,Color.YELLOW,Color.GREEN,Color.ORANGE,Color.PINK};
		}
		this.colorIndex = 0;
		this.currentType = GraphType.PIE;
		this.updateUI();
	}
	public GraphType getGraphType(){
		return currentType;
	}
	public void setGraphType(GraphType type){
		currentType = type;
		updateUI();
	}
	public TableModel getModel() {
		return model;
	}
	public void setColors(Color...colors){
		this.colors = colors;
	}
	public Color[] getColors() {
		return colors;
	}
	protected Color getColor(){
		if(colors==null){
			return Color.BLACK;
		}
		if(colorIndex>=colors.length)
			colorIndex=0;
		return colors[colorIndex++];
	}
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#updateUI()
	 */
	@Override public void updateUI() {
	    setUI(UIManager.getUI(this));
	}
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getUIClassID()
	 */
	@Override
	public String getUIClassID() {
	    return currentType.getUIClassID();
	}
	/* (non-Javadoc)
	 * @see javax.swing.Scrollable#getPreferredScrollableViewportSize()
	 */
	@Override
	public Dimension getPreferredScrollableViewportSize() {
		Dimension d =  getPreferredSize();
		d.width = 0;
		return d;
	}
	/* (non-Javadoc)
	 * @see javax.swing.Scrollable#getScrollableBlockIncrement(java.awt.Rectangle, int, int)
	 */
	@Override
	public int getScrollableBlockIncrement(Rectangle visibleRect,
			int orientation, int direction) {
		return 100;
	}
	/* (non-Javadoc)
	 * @see javax.swing.Scrollable#getScrollableTracksViewportHeight()
	 */
	@Override
	public boolean getScrollableTracksViewportHeight() {
		return true;
	}
	/* (non-Javadoc)
	 * @see javax.swing.Scrollable#getScrollableTracksViewportWidth()
	 */
	@Override
	public boolean getScrollableTracksViewportWidth() {
		return false;
	}
	/* (non-Javadoc)
	 * @see javax.swing.Scrollable#getScrollableUnitIncrement(java.awt.Rectangle, int, int)
	 */
	@Override
	public int getScrollableUnitIncrement(Rectangle visibleRect,
			int orientation, int direction) {
		return 10;
	}
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getMaximumSize()
	 */
	@Override
	public Dimension getMaximumSize() {
		return UIManager.getUI(this).getMaximumSize(this);
	}
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getPreferredSize()
	 */
	@Override
	public Dimension getPreferredSize() {
		return UIManager.getUI(this).getPreferredSize(this);
	}
}