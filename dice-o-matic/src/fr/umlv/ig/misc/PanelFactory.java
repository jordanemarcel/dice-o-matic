package fr.umlv.ig.misc;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PanelFactory {

	public static JPanel newWorkspacePanel() {
		JPanel newWorkspace = new JPanel();
		newWorkspace.setLayout(new BorderLayout());
		
		
		//JLabel title = new JLabel("Select your dices:");
		//title.setFont(new Font("Arial",Font.BOLD,16));
		//newWorkspace.add(title, BorderLayout.NORTH);
		
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
		newWorkspace.add(scrollPane, BorderLayout.CENTER);
		
		Insets bottomSpace = new Insets(0,10,10,0);
		Insets noSpace = new Insets(10,10,0,0);
		Insets rightSpace = new Insets(10,10,0,10);
		
		
		for(Class<? extends Dice> clazz: Main.dices) {
			constraints.gridwidth = 1;
			constraints.anchor = GridBagConstraints.BASELINE_LEADING;
			constraints.insets = rightSpace;
			DiceDescription description = clazz.getConstructors()[0].getAnnotation(DiceDescription.class);
			scrollPanel.add(new JLabel(clazz.getSimpleName()), constraints);
			JSpinner spin = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
			constraints.gridwidth = GridBagConstraints.REMAINDER;
			scrollPanel.add(spin, constraints);
			constraints.insets = bottomSpace;
			constraints.gridwidth = GridBagConstraints.REMAINDER;
			JLabel descriptionLabel = new JLabel(description.value()[0]);
			descriptionLabel.setFont(new Font("Arial", Font.ITALIC, 12));
			scrollPanel.add(descriptionLabel, constraints);
			constraints.insets = noSpace;
		}
		constraints.weighty = 1;
		constraints.weightx = 1;
		scrollPanel.add(new JPanel(), constraints);
		scrollPane.setBorder(BorderFactory.createTitledBorder("Select your dices"));
		return newWorkspace;
	}
	
}
