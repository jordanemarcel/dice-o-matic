package test;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class DiceTableModel extends DefaultTableModel{
	public DiceTableModel() {
		Vector<Integer> v;
		
	}
	public int getThrowCount(){
		return getColumnCount()*getRowCount();
	}
}
