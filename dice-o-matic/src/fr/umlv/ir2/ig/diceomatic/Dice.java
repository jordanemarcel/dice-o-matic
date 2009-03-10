package fr.umlv.ir2.ig.diceomatic;

public interface Dice {

    /**
     * NOTE: a class implementing the Dice interface is not supposed to declare 
     *       more than one constructor. This constructor is supposed to be annotated
     *       with a String array containing a description of the dice followed by a 
     *       description of the constructor's parameters.
     */

    
    /**
     * @return an integer in [1;6]
     */
    public int getValue();
    
}
