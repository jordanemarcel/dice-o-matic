package fr.umlv.ig.misc;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Iterator;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.ListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PanelFactory {
	
	public static JPanel newWorkspacePanel(final DiceModel model) {
		JPanel newWorkspace = new JPanel();
		newWorkspace.setLayout(new BorderLayout());
		
		JButton next = new JButton("Validate");
		next.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new JDialog();
				dialog.setTitle("Configure the dice");
				dialog.setSize(200, 100);
				dialog.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
				dialog.setVisible(true);
			}
			
		});
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
		
		Iterator<Class <? extends Dice>> it = model.getIterator();
		while(it.hasNext()) {
			final Class<? extends Dice> clazz = it.next();
			constraints.gridwidth = 1;
			constraints.anchor = GridBagConstraints.BASELINE_LEADING;
			constraints.insets = rightSpace;
			DiceDescription description = clazz.getConstructors()[0].getAnnotation(DiceDescription.class);
			scrollPanel.add(new JLabel(clazz.getSimpleName()), constraints);
			JSpinner spin = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1) {
				private static final long serialVersionUID = 1L;
				
				@Override
				protected void fireStateChanged() {
					model.changeElement(clazz, (Integer)this.getValue());
					for(ChangeListener cl: this.getChangeListeners()) {
						cl.stateChanged(new ChangeEvent(this));
					}
				}
				
			});
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
	
	public static JPanel newJarImportPanel(final Window window, final DiceModel model) {
		final JPanel newJarPanel = new JPanel();
		newJarPanel.setLayout(new BorderLayout());
		ListModel listModel = new DiceListModel(model);
		
		final JList diceList = new JList(listModel);
		diceList.setCellRenderer(new DefaultListCellRenderer(){
			private static final long serialVersionUID = 1L;

			@Override
			public Component getListCellRendererComponent(JList list,
					Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				Class<?> clazz = (Class<?>) value;
				this.setText(clazz.getSimpleName());
				return this;
			}
		});
		JScrollPane jsp = new JScrollPane(diceList);
		newJarPanel.add(jsp,BorderLayout.CENTER);
		newJarPanel.add(new JLabel("Dice loaded:"),BorderLayout.NORTH);
		JPanel east = new JPanel(null);
		east.setLayout(new GridBagLayout());
		JButton addJar = new JButton("Import a JarFile");
		addJar.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.addChoosableFileFilter(new FileNameExtensionFilter("Jar File","jar"));
				int retValue = chooser.showDialog(newJarPanel,"Validate");
				if ( retValue == JFileChooser.APPROVE_OPTION){ 
					File file = chooser.getSelectedFile();
					model.addJar(file.getAbsolutePath());
				}
			}
		});
		
		JButton close = new JButton("Close");
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.dispose();
			}
		});
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill=GridBagConstraints.HORIZONTAL;
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		east.add(addJar,gbc);
		gbc.insets=new Insets(5,0,0,0);
		east.add(close,gbc);
		gbc.weighty=1;
		east.add(new JPanel(),gbc);
		newJarPanel.add(east,BorderLayout.EAST);
		return newJarPanel;
	}
}
