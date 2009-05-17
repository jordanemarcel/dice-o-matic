package fr.umlv.ig.cheatir.model.config;

import javax.swing.AbstractListModel;


/**
 * This class represents an Adapter for the DiceModel. It extends an AbstractListModel
 * in order to view all the imported dice in a JList.
 */
@SuppressWarnings("serial")
public class DiceListModel extends AbstractListModel {
	/** The DiceModel of this adapter */
	private final DiceModel model;
	
	/**
	 * The constructor of this adapter
	 * @param model - the model of the adapter
	 */
	public DiceListModel(final DiceModel model) {
		this.model = model;
		DiceListener diceListener = new DiceListener() {
			@Override
			public void diceValueChanged() {
				
			}
			@Override
			public void diceAdded() {
				fireContentsChanged(this, 0, model.getSize());
			}

		};
		model.addJarDiceListener(diceListener);
	}

	@Override
	protected void fireContentsChanged(Object source, int index0, int index1) {
		super.fireContentsChanged(source, index0, index1);
	}

	@Override
	public Object getElementAt(int index) {
		return model.getElementAt(index);
	}

	@Override
	public int getSize() {
		return model.getSize();
	}
}
