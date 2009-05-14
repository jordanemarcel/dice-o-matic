package fr.umlv.ig.cheatir.model.graph;

import java.util.LinkedList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * This model is an implementation of AbstractTableModel.
 * It contains all result of all thrown dice. Each row represent a throw
 * and each column represent a dice.
 * @author Clement Lebreton & Jordane Marcel
 */
@SuppressWarnings("serial")
public class TotalThrowModel extends AbstractTableModel{
	private final List<ThrowModel> diceResults;
	private int nbDice;
	public TotalThrowModel(List<ThrowModel> diceResults){
		if(diceResults==null)
			throw new IllegalArgumentException("Argument should not be null!");
		this.diceResults = diceResults;
		nbDice = diceResults.size();
		
	}
	/**
	 * Constructs an empty  TotalThrowModel.
	 */
	public TotalThrowModel() {
		diceResults = new LinkedList<ThrowModel>();
		nbDice = -1;
	}
	/**
	 * Add a row to the model. Each row is a ThrowModel
	 * @param throwModel add this ThrowModel as a line to the model. Should not be null.
	 */
	public void addRow(ThrowModel throwModel){
		if(throwModel==null)
			throw new IllegalArgumentException("throwModel should not be null");
		if(nbDice != throwModel.getSize()){
			if(nbDice==-1){
				nbDice = throwModel.getSize();
			}else{
				throw new IllegalArgumentException("Diffrent number of element");
			}
		}
		diceResults.add(throwModel);
		fireTableRowsInserted(diceResults.size()-1, diceResults.size()-1);
	}
	/**
	 * Clear the data contains in the model
	 */
	public void clear(){
		diceResults.clear();
	}
	/**
	 * Returns the numbrer of column of the model. In fact this is the number of dice.
	 * @return the numbrer of column of the model
	 */
	@Override
	public int getColumnCount() {
		return nbDice;
	}
	/**
	 * Returns the number of row of the model. In fact this is the number of throw.
	 * @return the number of row of the model
	 */
	@Override
	public int getRowCount() {
		return diceResults.size();
	}
	/**
	 * Returns the elements at the row throwIndex and column diceIndex
	 * @return the value at the queried position, this is a Integer 
	 */
	@Override
	public Object getValueAt(int throwIndex, int diceIndex) {
		return diceResults.get(throwIndex).getElementAt(diceIndex);
	}

}
