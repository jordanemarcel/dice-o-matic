package fr.umlv.ig.stat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import fr.umlv.ig.misc.Dice;

public class DiceResult {
	private final Dice dice;
	private final List<Integer> results;
	public DiceResult(Dice dice, Integer... results) {
		this.dice=dice;
		this.results=Arrays.asList(results);
	}
	public List<Integer> getResults(){
		return Collections.unmodifiableList(results);
	}
	public int getResult(int throwNb){
		return results.get(throwNb);
	}
	public int getResultCount(){
		return results.size();
	}
	public Dice getDice(){
		return dice;
	}
}
