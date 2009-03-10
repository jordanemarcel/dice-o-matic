package fr.umlv.ir2.ig.diceomatic;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.CONSTRUCTOR)
@Retention(RetentionPolicy.RUNTIME)

public @interface DiceDescription {

    String[] value();
    
}
