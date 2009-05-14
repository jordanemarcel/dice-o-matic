package fr.umlv.ig.cheatir;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import fr.umlv.ig.cheatIr.model.graph.ThrowModel;
import fr.umlv.ig.cheatIr.model.graph.TotalThrowModel;
import fr.umlv.ig.misc.Dice;

public class DiceThrower {
	private List<Dice> dices;
	public DiceThrower(List<Dice> dices) {
		if(dices==null)
			throw new IllegalArgumentException("dices should not be null!");
		this.dices=Collections.unmodifiableList(dices);
	}
	public TotalThrowModel throwDices(int throwCount, TotalThrowModel model){
		model.clear();
		for(int i=0;i<throwCount;i++){
			model.addRow(this.throwDicesOnce());
		}
		return model;
	}
	public void setDices(List<Dice> dices) {
		this.dices=Collections.unmodifiableList(dices);
	}
	public List<Dice> getDices() {
		return dices;
	}
	private ThrowModel throwDicesOnce(){
		LinkedList<Integer> throwList = new LinkedList<Integer>();
		for(Dice dice: dices){
			throwList.add(dice.getValue());
		}
		return new ThrowModel(throwList.toArray(new Integer[0]));
	}
}