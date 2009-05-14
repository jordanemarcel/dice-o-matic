package fr.umlv.ig.cheatIr.model.graph;

import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractListModel;

@SuppressWarnings("serial")
public class ThrowModel extends AbstractListModel{
	private final List<Integer> results;
	public ThrowModel(Integer... results) {
		this.results=Arrays.asList(results);
	}
	@Override
	public Object getElementAt(int diceIndex) {
		return results.get(diceIndex);
	}
	@Override
	public int getSize() {
		return results.size();
	}
}
