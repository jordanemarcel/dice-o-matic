package fr.umlv.ig.cheatir.model.graph;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;


/**
 * An adaptor for a TotalThrowModel. This adaptor enable to choose which columns in the TotalThrowModel can be 
 * read from this adaptor.
 * @author Clement Lebreton & Jordane Marcel
 */
@SuppressWarnings("serial")
public class DiceSelectorModel extends AbstractTableModel{
	private HashMap<Integer, Integer> corresponding;
	final private TotalThrowModel model;
	private boolean isCorrespondingInit;
	/**
	 * Constructs a DiceSelectorModel base on the given TotalThrowModel.
	 * @param model - the model which contains data.
	 */
	public DiceSelectorModel(TotalThrowModel model) {
		isCorrespondingInit = false;
		if(model==null)
			throw new IllegalArgumentException("The model should not be null!");
		this.model = model;
		corresponding = new HashMap<Integer, Integer>();
		for(int i=0 ;i<model.getColumnCount(); i++){
			corresponding.put(i,i);
			isCorrespondingInit = true;
		}
		model.addTableModelListener(new TableModelListener(){
			@Override
			public void tableChanged(TableModelEvent e) {
				if(isCorrespondingInit==false){
					int size = DiceSelectorModel.this.model.getColumnCount();
					for(int i=0; i<size; i++){
						corresponding.put(i, i);
					}
					isCorrespondingInit = true;
					fireTableStructureChanged();
				}
				fireTableRowsInserted(e.getFirstRow(), e.getLastRow());
			}
		});

	}
	/**
	 * Sets the selected dice. If a dice is selected throw result of this dice can be read.
	 * The selected dice after this method is only all dice column index of the given set, previous 
	 * selected value are erase.
	 * @param selection - a Set of dice column index to select. If null any dice will be select.
	 */
	public void setSelectedDices(Set<Integer> selection){
		corresponding.clear();
		if(selection==null)
			return;
		Iterator<Integer> it = selection.iterator();
		int i = 0;
		while(it.hasNext()){
			corresponding.put(i++,it.next());
		}
		fireTableStructureChanged();
	}
	/**
	 * Returns the number of columns after the selected dice filter.
	 * @return the number of columns after the selected dice filter.
	 */
	@Override
	public int getColumnCount() {
		return corresponding.size();
	}
	/**
	 * Returns the number of row, which is the number of throw of each dice.
	 * @return the number of row, which is the number of throw of each dice.
	 */
	@Override
	public int getRowCount() {
		return model.getRowCount();
	}
	/**
	 * Returns the value of the element at the specified position.
	 * @return the value of the element at the specified position. This is a Integer.
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Integer tmp = corresponding.get(columnIndex);
		if(tmp==null)
			throw new ArrayIndexOutOfBoundsException("looking for "+columnIndex+" but size is "+this.corresponding.size());
		return model.getValueAt(rowIndex, tmp);
	}
}
