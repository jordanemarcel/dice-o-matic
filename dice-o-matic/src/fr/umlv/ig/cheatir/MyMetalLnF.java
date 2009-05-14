package fr.umlv.ig.cheatir;

import javax.swing.UIDefaults;
import javax.swing.plaf.metal.MetalLookAndFeel;

@SuppressWarnings("serial")
public class MyMetalLnF extends MetalLookAndFeel{
	/* (non-Javadoc)
	 * @see javax.swing.plaf.metal.MetalLookAndFeel#getDefaults()
	 */
	@Override
	public UIDefaults getDefaults() {
		UIDefaults defaults = super.getDefaults();
		defaults.put("HistogramGraphUI", "fr.umlv.ig.graph.HistogramGraphUI");
		defaults.put("PieGraphUI", "fr.umlv.ig.graph.PieGraphUI");
		return defaults;
	}
}