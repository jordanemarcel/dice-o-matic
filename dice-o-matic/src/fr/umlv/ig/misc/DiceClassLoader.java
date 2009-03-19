package fr.umlv.ig.misc;

import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;

public class DiceClassLoader extends URLClassLoader{
	public DiceClassLoader(URL[] urls) {
		super(urls);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected synchronized Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
		Class<?> classFound = null;
		try{
			// Use for load all needed classes before the searched class
			classFound = getParent().loadClass(name);
		}catch (ClassNotFoundException e) {
			if(classFound==null){
				classFound = findClass(name);
			}
			try{
				DiceClassLoader.isDiceClass(classFound);
			}catch (IllegalArgumentException iae) {
				classFound=null;
				throw new IllegalArgumentException("This Dice is not valid : " + iae.getMessage());
			}
		}
		return classFound;
	}
	public static boolean isDiceClass(Class<?> diceClass) throws IllegalArgumentException{
		Constructor<?> constructors[] = diceClass.getConstructors();
		if(constructors.length != 1)
			throw new IllegalArgumentException("This Dice have more than one constructor.");
		DiceDescription annotation = constructors[0].getAnnotation(DiceDescription.class);
		if(annotation==null){
			throw new IllegalArgumentException("The constructor don't have the DiceDescription Annotation");
		}
		Class<?> types[] = constructors[0].getParameterTypes();
		for(Class<?> type : types){
			if(!type.getCanonicalName().equals("int")
					&& !type.getCanonicalName().equals("float")
					&& !type.getCanonicalName().equals("String"))
				throw new IllegalArgumentException("Incorrect parameter type");
		}
		if(annotation.value().length != (types.length+1))
			throw new IllegalArgumentException("Incorrect parameter number");
		return true;
	}
}
