package fr.umlv.ig.cheatir.model.config;

/**
 * This class represents a Listener of a DiceModel
 * @author Clement Lebreton & Jordane Marcel
 */
public interface DiceListener {
	/**
	 * This method is called if the number of a Dice has changed
	 */
	public void diceValueChanged();
	
	/**
	 * This method is called if a dice has been added
	 */
	public void diceAdded();
	
}
