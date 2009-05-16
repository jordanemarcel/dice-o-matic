package fr.umlv.ig.cheatir;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import fr.umlv.ig.cheatir.model.config.DiceModel;

public class Main {
	static boolean currentSession = false;
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		try {
			UIManager.setLookAndFeel(new MyMetalLnF());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		final DiceModel model = new DiceModel();
		final JFrame f = new JFrame("Dice'o'matic");
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.setSize(640, 480);
		JPanel p = new JPanel(new BorderLayout());
		p.setBackground(Color.white);
		f.setContentPane(p);
		JLabel logo = new JLabel(new ImageIcon(Main.class.getResource("diceomatic.jpg")));
		p.add(logo, BorderLayout.CENTER);
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem newWorkspace = new JMenuItem("New workspace");
		newWorkspace.setIcon(new ImageIcon(Main.class.getResource("star.gif")));
		newWorkspace.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Main.currentSession) {
					int value = JOptionPane.showConfirmDialog(f,"Do you really want to start over again?","Question",JOptionPane.YES_NO_OPTION);
					switch(value) {
					case JOptionPane.YES_OPTION:
						model.newSession();
						f.setContentPane(PanelFactory.createWorkspacePanel(f,model));
						f.validate();
						break;
					case JOptionPane.NO_OPTION:
						break;
					}
				} else {
					f.setContentPane(PanelFactory.createWorkspacePanel(f,model));
					Main.currentSession = true;
					f.validate();
				}
			}
		});
		JMenuItem importItem = new JMenuItem("Import Dices");
		importItem.setIcon(new ImageIcon(Main.class.getResource("plus.gif")));
		importItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new JDialog();
				dialog.setContentPane(PanelFactory.createJarImportPanel(dialog, model));
				dialog.setModal(true);
				dialog.setSize(300, 200);
				dialog.setLocationRelativeTo(f);
				dialog.setVisible(true);
			}
		});
		JMenuItem quitItem = new JMenuItem("Quit");
		quitItem.setIcon(new ImageIcon(Main.class.getResource("quit.gif")));
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
