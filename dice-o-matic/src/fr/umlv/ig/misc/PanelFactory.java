package fr.umlv.ig.misc;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PanelFactory {

	public static JPanel newWorkspacePanel() {
		JPanel newWorkspace = new JPanel();
		newWorkspace.setLayout(new BorderLayout());
		
		
		JLabel title = new JLabel("Select your dices:");
		title.setFont(new Font("Arial",Font.BOLD,16));
		newWorkspace.add(title, BorderLayout.NORTH);
		
		JButton next = new JButton("Next");
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
		southPanel.add(next);
		next.setAlignmentX(1);
		newWorkspace.add(southPanel, BorderLayout.SOUTH);
		
		
		JPanel scrollPanel = new JPanel();
		GridBagConstraints constraints = new GridBagConstraints();
		scrollPanel.setLayout(new GridBagLayout());
		JScrollPane scrollPane = new JScrollPane(scrollPanel);
		
		newWorkspace.add(scrollPane);
		
		for(Class<? extends Dice> clazz: Main.dices) {
			DiceDescription description = clazz.getConstructors()[0].getAnnotation(DiceDescription.class);
			scrollPanel.add(new JLabel(clazz.getSimpleName()+" ("+description.value()[0]+")"));
		}
		for(Class<? extends Dice> clazz: Main.dices) {
			DiceDescription description = clazz.getConstructors()[0].getAnnotation(DiceDescription.class);
			scrollPanel.add(new JLabel(clazz.getSimpleName()+" ("+description.value()[0]+")"));
		}
		return newWorkspace;
	}
	
}
