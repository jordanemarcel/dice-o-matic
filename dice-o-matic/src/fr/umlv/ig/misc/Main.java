 package fr.umlv.ig.misc;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import sun.misc.ClassLoaderUtil;

public class Main {
	
	public static final ArrayList<Class<? extends Dice>> dices = new ArrayList<Class<? extends Dice>>();
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		
		dices.add(FairDice.class);
		dices.add(FakeDice.class);

		
		
		String filePath = "file://home/jordane/workspace/Dice-o-matic/MyDices.jar";
		URLClassLoader clazzLoader;
		try {
			clazzLoader = new URLClassLoader(new URL[]{new URL(filePath)});
			Class<?> dice;
			dice = clazzLoader.loadClass("fr.umlv.ig.misc.OnlySixDice.class");
			Dice myDice = (Dice)dice.newInstance();
			System.out.println(myDice.getValue());
		} catch (MalformedURLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}

		final JFrame f = new JFrame("Dice'o'matic");
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.setSize(640, 480);
		JPanel p = new JPanel(new BorderLayout());
		f.setContentPane(p);
		JLabel title = new JLabel("Dice-o-matic");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Arial",Font.BOLD,30));
		p.add(title, BorderLayout.NORTH);
		ImageIcon ii = new ImageIcon(Main.class.getResource("splash.jpg"));
		p.add(new JLabel(ii), BorderLayout.CENTER);
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem newWorkspace = new JMenuItem("New workspace");
		newWorkspace.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel newWorkspace = new JPanel();
				f.setContentPane(newWorkspace);
				newWorkspace.setLayout(new GridLayout(4,1));
				
				final JLabel configureLabel = new JLabel("How many dices do you want?");
				configureLabel.setHorizontalAlignment(SwingConstants.LEFT);
				configureLabel.setFont(new Font("Arial",Font.BOLD,12));
				newWorkspace.add(configureLabel, BorderLayout.NORTH);
				
				for(Class<? extends Dice> clazz: dices) {
					newWorkspace.add(new JLabel(clazz.getSimpleName()));
				}
				
				/*for(Class<? extends Dice> clazz: dices) {
					
					Constructor<?>[] constructor = clazz.getDeclaredConstructors();
					if(constructor.length!=1)
						throw new ClassFormatError("Class "+clazz.getName()+" has not exactly one constructor.");
					
					DiceDescription description = constructor[0].getAnnotation(DiceDescription.class);
					
					if(description!=null) {
						String[] stringDescription = description.value();
						if(stringDescription.length>1) {
							newWorkspace.add(new JLabel(clazz.getSimpleName()+": "+stringDescription[0]));
						}
					}
				}*/
				
				f.validate();
				
				
				/*
				JDialog di = new JDialog(f, "Test");
				JPanel newpane = new JPanel();
				di.setContentPane(newpane);
				JButton button = new JButton("TEST");
				newpane.add(button);
				f.setEnabled(false);
				f.setFocusableWindowState(false);
				f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				di.setSize(200,100);
				di.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				di.addWindowListener(new WindowListener() {
					@Override
					public void windowClosed(WindowEvent e) {
						f.setEnabled(true);
						f.setFocusableWindowState(true);
						f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						f.requestFocus();
					}

					@Override
					public void windowActivated(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void windowClosing(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void windowDeactivated(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void windowDeiconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void windowIconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void windowOpened(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
				di.setVisible(true);
				*/
			}
		});
		
		JMenuItem importItem = new JMenuItem("Import Dices");
		
		JMenuItem quitItem = new JMenuItem("Quit");
		quitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				f.dispose();
		
			}
		});
		fileMenu.add(newWorkspace);
		fileMenu.add(importItem);
		fileMenu.add(quitItem);
		menuBar.add(fileMenu);
		f.setJMenuBar(menuBar);
		f.setVisible(true);
	}
	
}
