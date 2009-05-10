package fr.umlv.ig.misc;

import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;

public class DiceClassLoader extends URLClassLoader{
	public DiceClassLoader(URL[] urls) {
		super(urls);
	}
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		try{
			Class<?> findClass = super.findClass(name);
			isDiceClass(findClass,false);
			return findClass;
		}catch(IllegalArgumentException e){
			throw new ClassNotFoundException(e.getMessage());
		}
	}
	private static boolean isDiceClass(Class<?> diceClass,boolean useBoolean){
		boolean haveDiceInterface=false;
		for (Class<?> clazz :diceClass.getInterfaces()){
			if(clazz.equals(Dice.class))
				haveDiceInterface=true;
		}
		if(!haveDiceInterface){
			if(useBoolean)
				return false;
			throw new IllegalArgumentException("Any dice should implement Dice interface");
		}
		Constructor<?> constructors[] = diceClass.getConstructors();
		if(constructors.length != 1){
			if(useBoolean)
				return false;
			throw new IllegalArgumentException("This Dice have more than one constructor.");
		}
		DiceDescription annotation = constructors[0].getAnnotation(DiceDescription.class);
		if(annotation==null){
			if(useBoolean)
				return false;
			throw new IllegalArgumentException("The constructor don't have the DiceDescription Annotation");
		}
		Class<?> types[] = constructors[0].getParameterTypes();
		for(Class<?> type : types){
			if(!type.getCanonicalName().equals("int")
					&& !type.getCanonicalName().equals("float")
					&& !type.getCanonicalName().equals("String")){
				if(useBoolean)
					return false;
				throw new IllegalArgumentException("Incorrect parameter type");
			}
		}
		if(annotation.value().length != (types.length+1)){
			if(useBoolean)
				return false;
			throw new IllegalArgumentException("Incorrect parameter number");
		}
		return true;
	}
	public static boolean isDiceClass(Class<?> diceClass) {
		return isDiceClass(diceClass, true);
	}
}
