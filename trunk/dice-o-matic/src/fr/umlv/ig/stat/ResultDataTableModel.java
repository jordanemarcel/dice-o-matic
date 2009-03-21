package fr.umlv.ig.stat;

import javax.swing.table.AbstractTableModel;

public class ResultDataTableModel extends AbstractTableModel{
	private final ResultDataModel rdm;
	public ResultDataTableModel(ResultDataModel rdm) {
		this.rdm=rdm;
	}
	
	@Override
	public int getColumnCount() {
		return rdm.getDiceCount();
	}

	@Override
	public int getRowCount() {
		return rdm.getThrowCount()/rdm.getDiceCount();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return rdm.getResultForDice(columnIndex).get(rowIndex);
	}
}
