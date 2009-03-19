package fr.umlv.ig.misc;

public class OnlySixDice implements Dice {

    @DiceDescription({"A dice that only makes six."})
    public OnlySixDice() {
        /* */
    }

    @Override
    public int getValue() {
        return 6;
    }
    
}
