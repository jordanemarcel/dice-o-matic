package fr.umlv.ig.graph;

import java.util.HashSet;

import fr.umlv.ig.stat.ResultDataTableModel;

public class DataAdapter{
	HashSet<Integer> enabledDice;
	ResultDataTableModel rdtm;
	public DataAdapter(ResultDataTableModel rdtm,int...diceNumbers) {
		//enabledDice = new ArrayList<Integer>();
		enabledDice = new HashSet<Integer>();
		for(int i : diceNumbers)
			enabledDice.add(i);
		this.rdtm = rdtm;
	}

	public int getData(int index){
		int row = rdtm.getRowCount();
		int throwCount = enabledDice.size()*rdtm.getRowCount();
		if(index>throwCount)
			throw new ArrayIndexOutOfBoundsException();
		int columnIndex = index/row;
		int rowIndex = index%row;
		while(!enabledDice.contains(columnIndex))
			columnIndex++;
		return (Integer)rdtm.getValueAt(rowIndex, columnIndex);
	}
	public int getDataCount(){
		return rdtm.getColumnCount()*rdtm.getRowCount();
	}
	public void setEnableDice(int...enabledDice){
		this.enabledDice = new HashSet<Integer>();
		for(int i : enabledDice){
			if(i>rdtm.getColumnCount()){
				throw new IllegalArgumentException("This Dice number doesn't exist! Max :"+rdtm.getColumnCount());
			}
			this.enabledDice.add(i);
		}
	}
}