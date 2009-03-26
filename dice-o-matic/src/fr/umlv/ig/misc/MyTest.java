package fr.umlv.ig.misc;

import javax.swing.JFrame;

public class MyTest {
	public static void main(String[] args) {
		
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setContentPane(PanelFactory.createSessionPanel());
		
		f.setSize(600, 400);
		f.setVisible(true);
	}
}
