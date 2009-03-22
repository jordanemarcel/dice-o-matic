package fr.umlv.ig.misc;

import javax.swing.AbstractListModel;

public class DiceListModel extends AbstractListModel {
	private static final long serialVersionUID = 1L;
	private final DiceModel model;
	
	public DiceListModel(final DiceModel model) {
		this.model = model;
		DiceListener diceListener = new DiceListener() {

			@Override
			public void elementAdded() {
				fireContentsChanged(this, 0, model.getSize());
			}

		};
		model.addDiceListener(diceListener);
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
