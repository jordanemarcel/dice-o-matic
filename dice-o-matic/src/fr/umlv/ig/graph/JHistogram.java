package fr.umlv.ig.graph;

import javax.swing.JComponent;
import javax.swing.event.EventListenerList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

@SuppressWarnings("serial")
public class JHistogram extends JComponent{
	//private final ResultDataGraphicModel model;
	private int sampling;
	private final TableModel model;
	private final TableModelListener tableListener = new TableModelListener(){
		@Override
		public void tableChanged(TableModelEvent e) {
			
		}
	};
	public JHistogram(TableModel tableModel,int sampling) {
		this.model = tableModel;
		
		this.sampling=sampling;
	}
	public TableModel getModel(){
		return model;
	}
	
	public void setModel(TableModel model){
		if(model==null){
			throw new IllegalArgumentException("Invalid null model");
		}
		if(this.model!=null){
			
		}
	}
}
