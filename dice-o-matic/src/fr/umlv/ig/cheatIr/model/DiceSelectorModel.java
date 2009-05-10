package fr.umlv.ig.cheatIr.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class DiceSelectorModel extends AbstractTableModel{
	private HashMap<Integer, Integer> corresponding;
	final private TotalThrowModel model;
	private boolean isCorrespondingInit;
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

	public void setSelectedDices(Set<Integer> selection){
		corresponding.clear();
		Iterator<Integer> it = selection.iterator();
		int i = 0;
		while(it.hasNext()){
			corresponding.put(i++,it.next());
		}
		fireTableStructureChanged();
	}
	@Override
	public int getColumnCount() {
		return corresponding.size();
	}
	@Override
	public int getRowCount() {
		return model.getRowCount();
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Integer tmp = corresponding.get(columnIndex);
		if(tmp==null)
			throw new ArrayIndexOutOfBoundsException();
		return model.getValueAt(rowIndex, tmp);
	}
}