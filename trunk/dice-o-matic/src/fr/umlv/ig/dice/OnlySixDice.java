package fr.umlv.ig.dice;

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
