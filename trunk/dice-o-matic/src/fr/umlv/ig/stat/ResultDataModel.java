package fr.umlv.ig.stat;

import java.util.List;

public interface ResultDataModel {
	public int getDiceCount();
	public int getThrowCount();
	public List<Integer> getResultForDice(int index);
}
