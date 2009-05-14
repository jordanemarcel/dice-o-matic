package fr.umlv.ig.cheatir;

import javax.swing.UIDefaults;
import javax.swing.plaf.metal.MetalLookAndFeel;

/**
 * A specific implementation of MetalLookAndFeel with some new ComonentUI for
 * the new JComponent {@link fr.umlv.ig.graph JGraph}.
 * @author Clement Lebreton & Jordane Marcel
 */
@SuppressWarnings("serial")
public class MyMetalLnF extends MetalLookAndFeel{
	/**
	 * Same as @link {@link javax.swing.plaf.metal.MetalLookAndFeel#MetalLookAndFeel()}
	 */
	public MyMetalLnF() {
		super();
	}
	/**
	 * Returns the defaults UI map.It's the same map returns by {@link javax.swing.plaf.metal.MetalLookAndFeel#getDefaults()}
	 * but with two more UI for the {@link fr.umlv.ig.graph.JGraph}
	 */
	@Override
	public UIDefaults getDefaults() {
		
		UIDefaults defaults = super.getDefaults();
		defaults.put("HistogramGraphUI", "fr.umlv.ig.graph.HistogramGraphUI");
		defaults.put("PieGraphUI", "fr.umlv.ig.graph.PieGraphUI");
		return defaults;
	}
}