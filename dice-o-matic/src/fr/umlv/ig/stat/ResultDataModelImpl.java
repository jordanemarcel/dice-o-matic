package fr.umlv.ig.stat;

import java.util.Arrays;
import java.util.List;

public class ResultDataModelImpl implements ResultDataModel{
	private final List<DiceResult> diceResults;
	private final int nbThrow;
	public ResultDataModelImpl(DiceResult...diceResults) {
		if(diceResults.length==0)
			throw new IllegalArgumentException();//TODO
		int nb = diceResults[0].getResultCount();
		for(DiceResult dr : diceResults){
			if(dr.getResultCount()!=nb)
				throw new IllegalArgumentException("All DiceResult should have the same number of result");
		}
		nbThrow=nb;
		this.diceResults=Arrays.asList(diceResults);
	}
	@Override
	public List<Integer> getResultForDice(int index) {
		return diceResults.get(index).getResults();
	}
	@Override
	public int getThrowCount() {
		return nbThrow;
	}
	@Override
	public int getDiceCount() {
		return diceResults.size();
	}
}
