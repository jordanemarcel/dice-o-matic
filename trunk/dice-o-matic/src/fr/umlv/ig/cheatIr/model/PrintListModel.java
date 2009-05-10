package fr.umlv.ig.cheatIr.model;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.AbstractListModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

@SuppressWarnings("serial")
public class PrintListModel extends AbstractListModel{
	private final TotalThrowModel model;
	private boolean shuffle;
	private LinkedList<Integer> randList;
	public PrintListModel(TotalThrowModel model) {
		this.model = model;
		shuffle = false;
		randList = new LinkedList<Integer>();
		model.addTableModelListener(new TableModelListener(){
			@Override
			public void tableChanged(TableModelEvent e) {
				PrintListModel.this.fireIntervalAdded(this,e.getFirstRow(), e.getLastRow());
				randList.clear();
				for(int i=0;i<PrintListModel.this.model.getColumnCount();i++){
					randList.add(i);
				}
				Collections.shuffle(randList);
			}
		});
	}
	public boolean isShuffle() {
		return shuffle;
	}
	public void setShuffle(boolean bool){
		shuffle = bool;
		fireContentsChanged(this, 0, getSize());
	}
	@Override
	public Object getElementAt(int index) {
		StringBuilder sb = new StringBuilder();
		if(shuffle){
			Iterator<Integer> it = randList.iterator();
			while(it.hasNext()){
				sb.append(model.getValueAt(index,it.next()));
				sb.append(",");
			}
		}else{
			for(int i=0;i<model.getColumnCount();i++){
				sb.append(model.getValueAt(index, i));
				sb.append(",");
			}
		}
		return sb.toString().substring(0, sb.toString().length()-1);
	}
	@Override
	public int getSize() {
		return model.getRowCount();
	}
}
