package fr.umlv.ig.cheatir.model.config;

import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import fr.umlv.ig.dice.Dice;

/**
 * This class is an adapter of the DiceModel. It extends the SpinnerNumberModel to be
 * used by JSpinner. It is used to shows the right number of each dice in the JSpinner,
 * in accordance with a correct MVC.
 *
 */
@SuppressWarnings("serial")
public class DiceSpinnerNumberModel extends SpinnerNumberModel {
	/** DiceClass of the SpinnerModel */
	private final Class<? extends Dice> diceClass;
	/** Model of this adapter */
	private final DiceModel model;
	
	/**
	 * Default constructor. It calls the default SpinnerNumberModel constructor but records a listener
	 * int the DiceModel.
	 * @param minimum - the minimum value of dice
	 * @param maximum - the maximum value of dice
	 * @param step - the step of increasing and decreasing the JSpinner
	 * @param diceClass - the class of the given dice
	 * @param model - The model of this adapter
	 */
	public DiceSpinnerNumberModel(int minimum, int maximum, int step, Class<? extends Dice> diceClass, DiceModel model) {
		super(model.getDiceNumber(diceClass), minimum, maximum, step);
		this.diceClass = diceClass;
		this.model = model;
		
		model.addSpinnerDiceListener(new DiceListener() {

			@Override
			public void diceAdded() {
				//no use
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
