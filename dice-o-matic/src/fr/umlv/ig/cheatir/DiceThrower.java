package fr.umlv.ig.cheatir;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import fr.umlv.ig.cheatir.model.graph.ThrowModel;
import fr.umlv.ig.cheatir.model.graph.TotalThrowModel;
import fr.umlv.ig.misc.Dice;


/**
 * This is a simple die thrower implementation. This implementation allow you to
 * throw several dice X times. X can be specify for each throw. 
 * @author Clement Lebreton & Jordane Marcel
 */
public class DiceThrower {
	private final List<Dice> dices;
	/**
	 * Constructs a die thrower containing all die in the specified list.
	 * @param dices the list whose elements are to be placed into this dice thrower
	 */
	public DiceThrower(List<Dice> dices) {
		if(dices==null)
			throw new IllegalArgumentException("dices should not be null!");
		this.dices=new LinkedList<Dice>();
		this.dices.addAll(dices);
	}
	/**
	 * Throws all the dice X times, and fill model with the result. More formally,
	 * throws X times, where X is given in throwCount parameter, before fill the
	 * model, this method clear it
	 * @param throwCount number of time each die will be throw
	 * @param model model to clear and fill with new throw result
	 * @return the model
	 */
	public TotalThrowModel throwDices(int throwCount, TotalThrowModel model){
		model.clear();
		for(int i=0;i<throwCount;i++){
			model.addRow(this.throwDicesOnce());
		}
		return model;
	}
	/**
	 * Returns an unmodifiable list of contained dice
	 * @return an unmodifiable list of contained dice
	 */
	public List<Dice> getDices() {
		return Collections.unmodifiableList(dices);
	}
	private ThrowModel throwDicesOnce(){
		LinkedList<Integer> throwList = new LinkedList<Integer>();
		for(Dice dice: dices){
			throwList.add(dice.getValue());
		}
		return new ThrowModel(throwList);
	}
}