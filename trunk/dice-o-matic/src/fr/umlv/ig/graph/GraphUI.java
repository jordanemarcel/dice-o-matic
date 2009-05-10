package fr.umlv.ig.graph;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

public abstract class GraphUI extends ComponentUI{
	/* (non-Javadoc)
	 * @see javax.swing.plaf.ComponentUI#getMaximumSize(javax.swing.JComponent)
	 */
	@Override
	public Dimension getMaximumSize(JComponent c) {
		//let the layout manager do its work
		return null;
	}
	/* (non-Javadoc)
	 * @see javax.swing.plaf.ComponentUI#getMinimumSize(javax.swing.JComponent)
	 */
	@Override
	public Dimension getMinimumSize(JComponent c) {
		//let the layout manager do its work
		return null;
	}
}