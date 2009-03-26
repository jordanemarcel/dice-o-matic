package fr.umlv.ig.graph;

import fr.umlv.ig.stat.ResultDataTableModel;

public class ResultDataGraphicModel{
	private final DataAdapter adapter;
	public ResultDataGraphicModel(ResultDataTableModel rdtm) {
		adapter = new DataAdapter(rdtm);
	}
	public int getThrowCount(){
		return adapter.getDataCount();
	}
	public int getData(int index){
		return adapter.getData(index);
	}
	
}
