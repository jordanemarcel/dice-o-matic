package fr.umlv.ig.cheatIr.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

@SuppressWarnings("serial")
public class StatDiceModel extends AbstractTableModel{
	private TableModel model;
	private int sampling;
	private int row;
	private int data[][];
	public StatDiceModel(DiceSelectorModel model,int sampling) {
		if(model == null){
			throw new IllegalArgumentException("The model should not be null!");
		}
		this.model = model;
		this.sampling = sampling;
		this.row = (int)Math.ceil(((double)model.getRowCount()*model.getColumnCount())/sampling);
		data = new int[row][this.getColumnCount()];
		for(int i = 0;i<row;i++){
			Arrays.fill(data[i], -1);
		}
	}
	
	/**
	 * Returns the number of columns in the model. A {@see JTable} uses this method to 
	 * determine how many columns it should create and display by default. 
	 * @return always 6 because this model handle only 6 faces Dice 
	 */
	@Override
	public int getColumnCount() {
		return 6;
	}
	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return row;
	}
	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		LinkedList<Integer> ll = new LinkedList<Integer>();
		int start = sampling*rowIndex;
		int row = model.getRowCount();
		if(data[rowIndex][columnIndex]!=-1){
			return data[rowIndex][columnIndex];
		}
		try{
			for(int i = start; i< start+sampling; i++){
				Integer val = (Integer)model.getValueAt(i%row, i/row);
				if(val==null)
					continue;
				ll.add(val);
			}
		}catch (ArrayIndexOutOfBoundsException e) {
			if(ll.size()==0)
				return null;
		}
		data[rowIndex][columnIndex] = Collections.frequency(ll, columnIndex+1);
		return data[rowIndex][columnIndex];
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<this.getRowCount();i++){
			for(int j=0;j<this.getColumnCount();j++){
				sb.append(this.getValueAt(i, j));
				sb.append(" ");
			}
			sb.append("\n");
		}		
		return sb.toString();
	}

}