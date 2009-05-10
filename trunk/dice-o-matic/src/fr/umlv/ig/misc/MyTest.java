package fr.umlv.ig.misc;

import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import fr.umlv.ig.cheatIr.model.DiceThrower;
import fr.umlv.ig.graph.MyMetalLnF;

public class MyTest {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new MyMetalLnF());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		LinkedList<Dice> list = new LinkedList<Dice>();
		list.add(new FairDice());
		for(int i=0;i<5;i++)
		list.add(new FairDice());
		DiceThrower dt = new DiceThrower(list);
		f.setContentPane(PanelFactory.createSessionPanel(dt));
		f.setSize(600, 400);
		f.setVisible(true);
	}
}
