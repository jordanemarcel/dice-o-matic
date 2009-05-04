package fr.umlv.ig.misc;

import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class DiceSpinnerNumberModel extends SpinnerNumberModel {
	private static final long serialVersionUID = 1L;
	private final Class<? extends Dice> diceClass;
	private final DiceModel model;
	
	public DiceSpinnerNumberModel(int minimum, int maximum, int step, Class<? extends Dice> diceClass, DiceModel model) {
		super(model.getDiceNumber(diceClass), minimum, maximum, step);
		this.diceClass = diceClass;
		this.model = model;
		
		model.addSpinnerDiceListener(new DiceListener() {

			@Override
			public void diceAdded() {
				/* */
			}

			@Override
			public void diceValueChanged() {
				fireStateChanged();
			}
			
		});
	}
	
	
	@Override
	protected void fireStateChanged() {
		for(ChangeListener cl: this.getChangeListeners()) {
			cl.stateChanged(new ChangeEvent(this));
		}
	}
	
	@Override
	public Object getNextValue() {
		int nextValue = this.getNumber().intValue()+this.getStepSize().intValue();
		return nextValue;
		
	}
	
	@Override
	public Object getPreviousValue() {
		int previousValue = this.getNumber().intValue()-this.getStepSize().intValue();
		return previousValue;
		
	}
	
	@SuppressWarnings("unchecked")
	private boolean isInBounds(int value) {
		if(this.getMinimum().compareTo(value)>0) {
			return false;
		}
		if(this.getMaximum().compareTo(value)<=0) {
			return false;
		}
		return true;
	}
	
	@Override
	public Object getValue() {
		return model.getDiceNumber(diceClass);
	}
	
	
	@Override
	public void setValue(Object value) {
		if((Integer)value==this.getNumber())
			return;
		if(isInBounds((Integer)value)) {
			model.changeElement(diceClass, (Integer)value);
		}
	}
	
	@Override
	public Number getNumber() {
		return model.getDiceNumber(diceClass);
	}

}
