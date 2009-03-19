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
				f.setContentPane(PanelFactory.newWorkspacePanel());
				f.validate();
			}
		});
		
		JMenuItem importItem = new JMenuItem("Import Dices");
		
		JMenuItem quitItem = new JMenuItem("Quit");
		quitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
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
