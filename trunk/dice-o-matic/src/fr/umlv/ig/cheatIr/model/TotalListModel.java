package fr.umlv.ig.cheatIr.model;

import javax.swing.AbstractListModel;

@SuppressWarnings("serial")
public class TotalListModel extends AbstractListModel{
	private TotalThrowModel model;
	public TotalListModel(TotalThrowModel model) {
		this.model = model;
	}
	@Override
	public Object getElementAt(int arg0) {
		StringBuilder sb = new StringBuilder();
		for(int j=0;j<model.getColumnCount();j++){
			for(int i=0;i<model.getRowCount();i++){
				sb.append(model.getValueAt(i, j));
				sb.append(" ");
			}
		}
		return sb.toString();
	}

	@Override
	public int getSize() {
		return 0;
	}

}
