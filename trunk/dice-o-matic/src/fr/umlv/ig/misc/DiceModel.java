package fr.umlv.ig.misc;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class DiceModel {
	private final LinkedHashMap<Class<? extends Dice>, Integer> diceMap = new LinkedHashMap<Class<? extends Dice>, Integer>();
	private final LinkedList<DiceListener> jarListenerList= new LinkedList<DiceListener>();
	private final LinkedList<DiceListener> spinnerListenerList= new LinkedList<DiceListener>();
	
	private boolean firing = false;
	
	public DiceModel() {
		this.diceMap.put(FairDice.class, 0);
		//this.diceMap.put(FakeDice.class, 0);
	}

	public int getSize() {
		return diceMap.size();
	}
	
	public void addJarDiceListener(DiceListener diceListener) {
		jarListenerList.add(diceListener);
	}
	
	public void addSpinnerDiceListener(DiceListener diceListener) {
		spinnerListenerList.add(diceListener);
	}
	
	public void newSession() {
		Iterator<Class<? extends Dice>> it = this.getIterator();
		while(it.hasNext()) {
			Class<? extends Dice> diceClass = it.next();
			diceMap.put(diceClass, 0);
		}
		if(firing)
			throw new IllegalStateException();
		spinnerListenerList.clear();
		fireElementAdded();
	}
	
	protected void fireElementAdded() {
		try {
			firing = true;
			for(DiceListener diceListener: jarListenerList) {
				diceListener.diceAdded();
			}
		}
		finally {
			firing = false;
		}
	}
	
	protected void fireDiceValueChanged() {
		try {
			firing = true;
			for(DiceListener diceListener: spinnerListenerList) {
				System.out.println("Model: CHAAAAAAANGE!!!!");
				diceListener.diceValueChanged();
			}
		}
		finally {
			firing = false;
		}
	}
	
	public Object getElementAt(int index) {
		if(index>=this.getSize())
			throw new ArrayIndexOutOfBoundsException("Trying to access an object out of bounds!");
		return diceMap.keySet().toArray()[index];
	}

	public void addElement(Class<? extends Dice> diceClass) {
		if(!DiceClassLoader.isDiceClass(diceClass))
			throw new IllegalArgumentException("The class "+diceClass+" is not inherited from "+Dice.class);
		if(this.contains(diceClass))
			throw new IllegalArgumentException("The class "+diceClass+" is already loaded in the model.");
		diceMap.put(diceClass, 0);
		fireElementAdded();
	}

	public void changeElement(Class<? extends Dice> diceClass, int value) {
		if(diceMap.get(diceClass)==null)
			throw new IllegalStateException("Can't change the value of "+diceClass+". The class does not exist in the model.");
		diceMap.put(diceClass, value);
		System.out.println("New value: "+value);
		System.out.println("for class: "+diceClass);
		fireDiceValueChanged();
	}

	public Iterator<Class<? extends Dice>> getIterator() {
		return diceMap.keySet().iterator();
	}
	
	public int getDiceNumber(Class<? extends Dice> diceClass) {
		return diceMap.get(diceClass);
	}

	private boolean contains(Class<? extends Dice> diceClass){
		Iterator<Class<? extends Dice>> iterator = this.getIterator();
		while(iterator.hasNext()) {
			Class<? extends Dice> clazz = iterator.next();
			if(clazz.getCanonicalName().equals(diceClass.getCanonicalName()))
				return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public void addJar(String jarAbsolutePath) {
		File file = new File(jarAbsolutePath);
		if(!file.isAbsolute() || !file.isFile()){
			throw new IllegalArgumentException("This should be an absolute path to a file.");
		}
		JarFile jf;
		try {
			jf = new JarFile(file);
			Enumeration<JarEntry> entries = jf.entries();
			ArrayList<String> classes = new ArrayList<String>();
			while (entries.hasMoreElements()){
				JarEntry entry = entries.nextElement();
				if(entry.isDirectory())
					continue;
				String name = entry.getName();
				if(name.endsWith(".class")){
					String className = name.substring(name.lastIndexOf(File.separator)+1, name.lastIndexOf("."));
					classes.add(className);
				}
			}
			URL[] url = new URL[1];
			url[0]= new URL("file://"+jarAbsolutePath);
			DiceClassLoader loader = new DiceClassLoader(url);
			for (String clazz : classes){
				try {
					Class<? extends Dice> toAdd = (Class<? extends Dice>)loader.loadClass("fr.umlv.ig.misc."+clazz);
					addElement(toAdd);
				} catch (ClassNotFoundException e) {
					//This should no be possible, unless 
					//a non-class file has an extension .class
				}
			}
		} catch (IOException e) {
			//TODO 
		}
	}
}
