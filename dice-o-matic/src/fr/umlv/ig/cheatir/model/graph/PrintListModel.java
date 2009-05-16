package fr.umlv.ig.cheatir.model.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.AbstractListModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 * This adaptor is used for print result of a TotalThrowModel in a {@link javax.swing.JList}. It also provides a shuffle mode
 * that print result in a random order. In no shuffle mode, each line to a throw of all dice, dice result are 
 * separated by colon. In shuffle mode the number of line and dice per line does not change.
 */
@SuppressWarnings("serial")
public class PrintListModel extends AbstractListModel{
	private final TotalThrowModel model;
	private final ArrayList<LinkedList<Integer>> randColList;
//	private final HashMap<Integer,Integer> randRowMap;
	private boolean shuffle;
	private boolean contentChange;
	/**
	 * Constructs a PrintListModel, its data are contains in the given TotalThrowModel.
	 * @param model - contains the data of this adaptor, model should not be null.
	 */
	public PrintListModel(TotalThrowModel model) {
		if(model==null)
			throw new IllegalArgumentException("model should not be null");
		this.model = model;
		shuffle = false;
		contentChange = false;
		randColList = new ArrayList<LinkedList<Integer>>();
//		randRowMap = new HashMap<Integer,Integer>();
		updateRandomList();
		this.model.addTableModelListener(new TableModelListener(){
			@Override
			public void tableChanged(TableModelEvent e) {
				contentChange = true;
				if(PrintListModel.this.shuffle){
					updateRandomList();
					contentChange = false;
				}
				
				fireIntervalAdded(this,e.getFirstRow(), e.getLastRow());
			}
		});
	}
	private void updateRandomList(){
		randColList.clear();
//		randRowMap.clear();
		
		for(int i=0;i<PrintListModel.this.model.getRowCount();i++){
			LinkedList<Integer> randomRow = new LinkedList<Integer>();
			for(int j=0;j<PrintListModel.this.model.getColumnCount();j++){
				randomRow.add(j);
			}
			Collections.shuffle(randomRow);
			randColList.add(randomRow);
		}
//		LinkedList<Integer> randRowList = new LinkedList<Integer>();
//		for(int i=0;i<PrintListModel.this.model.getRowCount();i++){
//			randRowList.add(i);
//		}
//		Collections.shuffle(randColList);
//		Collections.shuffle(randRowList);
//		for(int i=0;i<PrintListModel.this.model.getRowCount();i++){
//			randRowMap.put(i, randRowList.removeFirst());
//		}
	}
	/**
	 * Returns true if the shuffle mode is enable, false otherwise.
	 * @return true if the shuffle mode is enable, false otherwise.
	 */
	public boolean isShuffle() {
		return shuffle;
	}
	/**
	 * Sets the mode shuffle on / off. For enable shuffle mode set true, for disable it set false.
	 * @param bool - For enable shuffle mode set true, for disable it set false.
	 */
	public void setShuffle(boolean bool){
		if(contentChange && bool){
			updateRandomList();
			contentChange = false;
		}
		if(shuffle!=bool){
			fireContentsChanged(this, 0, getSize());
		}
		shuffle = bool;	
	}
	/**
	 * Returns the result of all dice for the given throw index. If shuffle mode is on is a mix of several
	 * dice and throw index.
	 * @return a string whiech contains the result of all dice for the given throw index separte by colon.
	 */
	@Override
	public Object getElementAt(int index) {
		StringBuilder sb = new StringBuilder();
		if(shuffle){
			Iterator<Integer> it = randColList.get(index).iterator();
			while(it.hasNext()){
				sb.append(model.getValueAt(index,it.next()));
				sb.append(" ");
			}
		}else{
			for(int i=0;i<model.getColumnCount();i++){
				sb.append(model.getValueAt(index, i));
				sb.append(" ");
			}
		}
		return sb.toString().substring(0, sb.toString().length()-1);
	}
	/**
	 * Returns the number of row, which is the number of throw of each dice
	 * @return the number of row, which is the number of throw of each dice
	 */
	@Override
	public int getSize() {
		return model.getRowCount();
	}
}
