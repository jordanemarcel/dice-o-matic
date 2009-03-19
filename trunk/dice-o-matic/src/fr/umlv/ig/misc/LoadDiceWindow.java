package fr.umlv.ig.misc;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class LoadDiceWindow {
	private JFrame frame = new JFrame("New session");
	private final DiceListModel diceModel = new DiceListModel();
	private DiceClassLoader diceClassLoader;
	private ArrayList<URL> urls;
	public LoadDiceWindow() {
		diceClassLoader = new DiceClassLoader(new URL[0]);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel main = (JPanel)frame.getContentPane();
		main.setLayout(new BorderLayout());
		JList diceList = new JList(diceModel);
		JScrollPane jsp = new JScrollPane(diceList);
		main.add(jsp,BorderLayout.CENTER);
		JPanel east = new JPanel(new FlowLayout());
		JButton addJar = new JButton("Add jar");
		addJar.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				int retValue = chooser.showDialog(frame,"Validate");
				if ( retValue == JFileChooser.APPROVE_OPTION){ 
					File file = chooser.getSelectedFile();
					try {
						urls.add(new URL("file://"+file.getAbsolutePath()));
					} catch (MalformedURLException e1) {
						e1.printStackTrace();
					}
					diceClassLoader = new DiceClassLoader(urls.toArray(new URL[0]));
					//diceClassLoader.get
				}
			}
		});
		east.add(addJar);
		main.add(east,BorderLayout.EAST);
		frame.pack();
		frame.setVisible(true);
	}

}
