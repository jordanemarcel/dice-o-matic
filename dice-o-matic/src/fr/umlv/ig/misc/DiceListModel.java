package fr.umlv.ig.misc;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.swing.AbstractListModel;

public class DiceListModel extends AbstractListModel{
	private static final long serialVersionUID = 1L;
	private final ArrayList<Class<? extends Dice>> diceList = new ArrayList<Class<? extends Dice>>();

	public DiceListModel() {
		this.addElement(FairDice.class);
		this.addElement(FakeDice.class);
	}
	@SuppressWarnings("unchecked")//We check in isDiceClass if it s a Dice
	private <T> void addElementCaptured(Class<T> diceClass){
		try{
			if(DiceClassLoader.isDiceClass(diceClass)){
				//diceList contains Class load from differant classLoader so we have to
				//make our own check
				boolean contains=false;
				for(Class<? extends Dice> clazz: diceList){
					if(clazz.getCanonicalName().equals(diceClass.getCanonicalName()))
						contains=true;			
				}
				if(!contains)
					diceList.add((Class<Dice>)diceClass);
			}
		}catch (IllegalArgumentException e) {
			//Don't add this class cause it's not a Dice
		}
	}
	public void addElement(Class<?> diceClass) {
		addElementCaptured(diceClass);
		System.out.println("bla :"+diceClass.getSimpleName());
		fireIntervalAdded(this,diceList.size() ,diceList.size());
	}
	@Override
	public Object getElementAt(int index) {
		return diceList.get(index);
	}
	@Override
	public int getSize() {
		return diceList.size();
	}
	public void addJar(String jarAbsolutePath){
		File file = new File(jarAbsolutePath);
		if(!file.isAbsolute() || !file.isFile()){
			throw new IllegalArgumentException("This should be an absolute path to a file.");
		}
		JarFile jf;
		try {
			jf = new JarFile("/home/nex/workspace/dice-o-matic/dice.jar");
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
					Class<?> toAdd = loader.loadClass("fr.umlv.ig.misc."+clazz);
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
