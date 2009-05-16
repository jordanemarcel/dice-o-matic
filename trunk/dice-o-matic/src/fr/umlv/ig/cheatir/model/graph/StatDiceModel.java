package fr.umlv.ig.cheatir.model.graph;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 * This is an adaptor for a DiceSelectorModel. This adaptor compute statistic
 * over this model, the first column is the number of 1, the second the number of 2...
 * Each line is a sampling. So in first row first column the value is the number of
 * 1 in the first sampling.
 */
@SuppressWarnings("serial")
public class StatDiceModel extends AbstractTableModel{
	private final TableModel model;
	private final int sampling;
	private int row;
	private int data[][];
	/**
	 * Constructs a new StatDiceModel. Its data are based on the given model.
	 * @param model contains the data
	 * @param sampling the number of value contains in one sampling
	 */
	public StatDiceModel(DiceSelectorModel model,int sampling) {
		if(model == null){
			throw new IllegalArgumentException("The model should not be null!");
		}
		this.model = model;
//		if(model.getRowCount()<sampling) {
//			this.sampling = model.getRowCount();
//		} else {
//			this.sampling = sampling;
//		}
		this.sampling = sampling;
		this.row = (int)Math.ceil(((double)model.getRowCount())/this.sampling);
		data = new int[row][this.getColumnCount()];
		for(int i = 0;i<row;i++){
			Arrays.fill(data[i], -1);
		}
		model.addTableModelListener(new TableModelListener(){
			@Override
			public void tableChanged(TableModelEvent e) {
				row = (int)Math.ceil(((double)StatDiceModel.this.model.getRowCount())/StatDiceModel.this.sampling);
				data = new int[row][StatDiceModel.this.getColumnCount()];
				for(int i = 0;i<row;i++){
					Arrays.fill(data[i], -1);
				}
				fireTableChanged(new TableModelEvent(StatDiceModel.this));
			}
		});
	}

	/**
	 * Returns the number of columns in the model. A {@link javax.swing.JTable} uses this method to 
	 * determine how many columns it should create and display by default. 
	 * @return always 6 because this model handle only 6 faces Dice 
	 */
	@Override
	public int getColumnCount() {
		return 6;
	}
	/**
	 * Returns the number of rows in this data table.
	 * @return the number of columns in the model
	 */
	@Override
	public int getRowCount() {
		return row;
	}
	/**
	 * Returns an attribute value for the cell at rowIndex and samplingIndex. 
	 * @param rowIndex the row whose value is to be queried
	 * @param samplingIndex the sampling index whose value is to be queried 
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		LinkedList<Integer> ll = new LinkedList<Integer>();
		int start = sampling*rowIndex;
		//int row = model.getRowCount();
		int col = model.getColumnCount();
		if(data[rowIndex][columnIndex]!=-1){
			return data[rowIndex][columnIndex];
		}
		try{
			for(int i = start; i< start+sampling; i++){
				for(int j=0;j<col;j++){
					Integer val = (Integer)model.getValueAt(i, j);
					if(val==null)
						continue;
					ll.add(val);
				}
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
