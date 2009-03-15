package fr.umlv.ig.misc;

import java.awt.Menu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;

public class Main {
	
	public static void main(String[] args) {
		final JFrame f = new JFrame("Dice'o'matic");
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.setSize(640, 480);
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem loadJarItem = new JMenuItem("Load a Dice file");
		JMenuItem quitItem = new JMenuItem("Quit");
		quitItem.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				f.dispose();
		
			}
		});
		fileMenu.add(loadJarItem);
		fileMenu.add(quitItem);
		menuBar.add(fileMenu);
		f.setJMenuBar(menuBar);
		f.setVisible(true);
	}
	
}
