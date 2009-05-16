package fr.umlv.ig.cheatir.loader;

import java.lang.reflect.Constructor;

public class ClassTool {

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
