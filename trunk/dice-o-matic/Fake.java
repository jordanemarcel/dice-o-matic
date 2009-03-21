package fr.umlv.ig.misc;

import java.util.Random;

public class Fake implements Dice {

    private final static Random random = new Random();
    private final int value;
    private final float probability;

    /**
     * Creates a fake dice that will give 'value' with the given probability.
     * The five remaining values will have the same probability.
     * 
     * @param value
     * @param probability
     */
    @DiceDescription({"A fake dice with a preferred value","Special value","Probability [0;1]"})
    public Fake(int value, float probability) {
        if (value < 1 || value > 6) {
            throw new IllegalArgumentException("The dice value must be in [1;6]");
        }
        if (probability < 0 || probability > 1) {
            throw new IllegalArgumentException("The probability must be in [0;1]");
        }
        this.value = value;
        this.probability = probability;
    }

    @Override
    public int getValue() {
        if (random.nextFloat() <= probability)
            return value;
        int val = random.nextInt(5) + 1;
        if (value == 6)
            return val;
        if (val == value)
            return 6;
        return val;
    }
    
}

