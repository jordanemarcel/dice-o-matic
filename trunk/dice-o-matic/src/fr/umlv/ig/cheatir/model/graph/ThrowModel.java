package fr.umlv.ig.cheatir.model.graph;

import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractListModel;

/**
 * This Model is a implementation of @link javax.swing.AbstractListModel
 * This model store dice result.
 * @author Clement Lebreton & Jordane Marcel
 */
@SuppressWarnings("serial")
public class ThrowModel extends AbstractListModel{
	private final List<Integer> results;
	/**
	 * Constructs a ThrowModel, it contains a list of value. 
	 * @param results add all this list to the model
	 */
	public ThrowModel(List<Integer> results) {
		this.results = new LinkedList<Integer>();
		this.results.addAll(results);
	}
	/**
	 * Returns the element at the given index
	 * @param diceIndex the queried index
	 * @return a the Integer value at the given index
	 */
	@Override
	public Object getElementAt(int diceIndex) {
		return results.get(diceIndex);
	}
	/**
	 * Returns the number of elements in the model
	 * @return the number of elements in the model
	 */
	@Override
	public int getSize() {
		return results.size();
	}
}
