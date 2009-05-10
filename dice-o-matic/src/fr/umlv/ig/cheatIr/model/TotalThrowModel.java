package fr.umlv.ig.cheatIr.model;

import java.util.LinkedList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

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
	public TotalThrowModel() {
		diceResults = new LinkedList<ThrowModel>();
		nbDice = -1;
	}
	public void addRow(ThrowModel throwModel){
		if(nbDice != throwModel.getSize()){
			if(nbDice==-1){
				nbDice = throwModel.getSize();
			}else{
				throw new IllegalArgumentException("Diffrent number of element");
			}
		}
		diceResults.add(throwModel);
		fireTableRowsInserted(diceResults.size(), diceResults.size());
	}
	public void clear(){
		diceResults.clear();
	}
	@Override
	public int getColumnCount() {
		return nbDice;
	}
	@Override
	public int getRowCount() {
		return diceResults.size();
	}
	@Override
	public Object getValueAt(int throwIndex, int diceIndex) {
		return diceResults.get(throwIndex).getElementAt(diceIndex);
	}
	@Override
	public void fireTableRowsInserted(int firstRow, int lastRow) {
		TableModelListener[] tab = listenerList.getListeners(TableModelListener.class);
		for(TableModelListener tmp : tab){
			tmp.tableChanged(new TableModelEvent(this,firstRow,lastRow));
		}
	}
}
