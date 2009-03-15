package fr.umlv.ig.misc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Menu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Main {
	
	public static void main(String[] args) {
		final JFrame f = new JFrame("Dice'o'matic");
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.setSize(640, 480);
		JPanel p = new JPanel(new BorderLayout());
		f.setContentPane(p);
		JLabel title = new JLabel("Dice-o-matic");
		title.setBackground(Color.YELLOW);
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setFont(new Font("Arial",Font.BOLD,30));
		p.add(title, BorderLayout.NORTH);
		ImageIcon ii = new ImageIcon(Main.class.getResource("splash.jpg"));
		p.add(new JLabel(ii), BorderLayout.CENTER);
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem newWorkspace = new JMenuItem("Start a new workspace");
		newWorkspace.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
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
						f.requestFocus;
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
				/*
				JFrame hello = new JFrame("Test");
				f.setEnabled(false);
				f.setFocusableWindowState(false);
				hello.setSize(100, 100);
				hello.setLocation(50, 50);
				hello.setVisible(true);*/
			}
		});
		JMenuItem quitItem = new JMenuItem("Quit");
		quitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				f.dispose();
		
			}
		});
		fileMenu.add(newWorkspace);
		fileMenu.add(quitItem);
		menuBar.add(fileMenu);
		f.setJMenuBar(menuBar);
		f.setVisible(true);
	}
	
}
