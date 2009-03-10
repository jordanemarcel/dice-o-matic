package fr.umlv.ir2.ig.diceomatic;

import java.util.Random;

public class FairDice implements Dice {

    private final static Random random=new Random();
    
    @DiceDescription({"A normal dice"})
    public FairDice() {
        /* */
    }
    
    @Override
    public int getValue() {
        return random.nextInt(6)+1;
    }

}
