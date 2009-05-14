package fr.umlv.ig.cheatir;

import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.Scrollable;

/**
 * A implementation of JPanel which Implements scrollable. This panel allow
 * only vertical scroll. All other feature of JPanel stay same.
 * @author Clement Lebreton & Jordane Marcel
 */
@SuppressWarnings("serial")
public class JVerticalScrollablePanel  extends JPanel implements Scrollable{
	/**
	 * Constructs a vertical only scrollable panel, all properties are same
	 * as a default JPanel.
	 */
	public JVerticalScrollablePanel() {
		super();
	}
	/**
	 * Constructs a vertical only scrollable panel, all properties are same
	 * as a default JPanel with the specified LayoutManager
	 * @param layout the LayoutManager to use
	 */
	public JVerticalScrollablePanel(LayoutManager layout) {
		super(layout);
	}
	@Override
	public Dimension getPreferredScrollableViewportSize() {
		return getPreferredSize();
	}
	@Override
	public int getScrollableBlockIncrement(Rectangle visibleRect,
			int orientation, int direction) {
		return 10;
	}
	@Override
	public boolean getScrollableTracksViewportHeight() {
		return false;
	}
	@Override
	public boolean getScrollableTracksViewportWidth() {
		return true;
	}
	@Override
	public int getScrollableUnitIncrement(Rectangle visibleRect,
			int orientation, int direction) {
		return 1;
	}
	
}
