package fr.umlv.ig.misc;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

public class DiceListModel extends AbstractListModel{	
	private final ArrayList<Class<? extends Dice>> diceList = new ArrayList<Class<? extends Dice>>();
	
	public DiceListModel() {
		this.addElement(FairDice.class);
		this.addElement(FakeDice.class);
	}
	public void addElement(Class<? extends Dice> diceClass) {
		try{
			if(DiceClassLoader.isDiceClass(diceClass)){
				diceList.contains(diceClass);
				diceList.add(diceClass);
			}
		}catch (IllegalArgumentException e) {
			//Don't add this class cause it's not a Dice
		}
	}
	@Override
	public Object getElementAt(int index) {
		return diceList.get(index);
	}
	@Override
	public int getSize() {
		return diceList.size();
	}
}