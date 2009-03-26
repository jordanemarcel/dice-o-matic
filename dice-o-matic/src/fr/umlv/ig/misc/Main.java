package fr.umlv.ig.misc;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class Main {
	static boolean currentSession = false;
		
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		
		final DiceModel model = new DiceModel();
		
		final JFrame f = new JFrame("Dice'o'matic");
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.setSize(640, 480);
		JPanel p = new JPanel(new BorderLayout());
		f.setContentPane(p);
		JLabel title = new JLabel("Dice-o-matic");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Arial",Font.BOLD,30));
		p.add(title, BorderLayout.NORTH);
		//ImageIcon ii = new ImageIcon(Main.class.getResource("splash.jpg"));
		//p.add(new JLabel(ii), BorderLayout.CENTER);
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem newWorkspace = new JMenuItem("New workspace");
		
		newWorkspace.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Main.currentSession) {
					JOptionPane optionPane = new JOptionPane("Do you really want to start over again?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
					final JDialog dialog = new JDialog();
					dialog.setContentPane(optionPane);
					dialog.setModal(true);
					dialog.pack();
					dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					optionPane.addPropertyChangeListener(new PropertyChangeListener() {

						@Override
						public void propertyChange(PropertyChangeEvent evt) {
							Integer value = (Integer)evt.getNewValue();
							if(value==null)
								return;
							switch(value) {
							case JOptionPane.YES_OPTION:
								model.newSession();
								f.setContentPane(PanelFactory.newWorkspacePanel(model));
								dialog.dispose();
								f.validate();
								break;
							case JOptionPane.NO_OPTION:
								dialog.dispose();
								break;
							}
						}
						
					});
					dialog.setVisible(true);
				} else {
					f.setContentPane(PanelFactory.newWorkspacePanel(model));
					Main.currentSession = true;
					f.validate();
				}
			}
		});
		
		JMenuItem importItem = new JMenuItem("Import Dices");
		importItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new JDialog();
				dialog.setContentPane(PanelFactory.newJarImportPanel(dialog, model));
				dialog.setModal(true);
				dialog.setSize(300, 200);
				dialog.setVisible(true);
			}
			
		});
		
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
