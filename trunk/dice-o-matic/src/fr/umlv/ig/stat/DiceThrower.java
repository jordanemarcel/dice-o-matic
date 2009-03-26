package fr.umlv.ig.stat;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import fr.umlv.ig.misc.Dice;

public class DiceThrower {
	private final List<Dice> dices;
	
	public DiceThrower(Dice...dices) {
		this.dices=Arrays.asList(dices);
	}
	
	public List<DiceResult> throwDices(int throwCount){
		List<DiceResult> results = new LinkedList<DiceResult>();
		for(Dice dice: dices){
			results.add(throwDice(dice, throwCount));
		}
		return results;
	}
	private static DiceResult throwDice(Dice dice,int throwCount){
		LinkedList<Integer> throwList = new LinkedList<Integer>();
		for(int i=0;i<throwCount;i++){
			throwList.add(dice.getValue());
		}
		return new DiceResult(dice,throwList.toArray(new Integer[0]));
	}
}