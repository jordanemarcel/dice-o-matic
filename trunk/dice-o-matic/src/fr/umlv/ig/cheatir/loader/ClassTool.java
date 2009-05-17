package fr.umlv.ig.cheatir.loader;

import java.lang.reflect.Constructor;

/**
 * The ClassTool contains method that returns interesting informations about
 * Class objects.
 * @author Clément Lebreton, Jordane Marcel
 *
 */
public class ClassTool {

	/**
	 * Return true if the given class has a unique constructor, and if this constructor
	 * has no parameters.
	 * @param clazz - the given clazz to be tested
	 * @return a boolean
	 */
	public static boolean hasUniqueConstructorArguments(Class<?> clazz) {
		Constructor<?>[] constructors = clazz.getConstructors();
		if(constructors.length!=1) {
			return false;
		}
		if(constructors[0].getParameterTypes().length==0) {
			return true;
		}
		return false;
	}
	
}
