package fr.umlv.ig.cheatir.loader;

import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;

import fr.umlv.ig.dice.Dice;
import fr.umlv.ig.dice.DiceDescription;

/**
 * This is a special implementation of URLClassLoader, in fact this class loader
 * can load only classes with some specification. 
 * This class should :
 * <li>implements {@link fr.umlv.ig.dice.Dice} interface</li>
 * <li>have only ONE constructor</li>
 * <li>constructor parameter should be int, float or String and nothing else</li>
 * <li>the constructor should be annotate with {@link fr.umlv.ig.dice.DiceDescription} annotation</li>
 * </ul>
 * @author Clement Lebreton & Jordane Marcel
 */
public class DiceClassLoader extends URLClassLoader{
	/**
	 * This constructor is same as URLClassLoader(URL[] urls)
	 * @param urls urls in which this loader try to find requested class
	 */
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
					&& !type.getCanonicalName().equals("java.lang.String")){
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
	
	/**
	 * Returns if the given class is available to be load by this classLoader
	 * @param diceClass the checked class
	 * @return true if this class if available, false otherwise. More formally, to be available 
	 * this class should : 
	 * <li>-implements fr.umlv.misc.Dice interface</li>
	 * <li>-have only ONE constructor</li>
	 * <li>-constructor parameter should be int, float or String and nothing else</li>
	 * <li>-the constructor should be annotate with fr.umlv.misc.DiceDescription annotation</li>
	 *  
	 */
	public static boolean isDiceClass(Class<?> diceClass) {
		return isDiceClass(diceClass, true);
	}
}
