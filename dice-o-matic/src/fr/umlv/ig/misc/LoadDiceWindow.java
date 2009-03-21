package fr.umlv.ig.misc;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class LoadDiceWindow {
	/*private JFrame frame = new JFrame("New session");
	private final DiceListModel diceModel = new DiceListModel();
	public LoadDiceWindow() {
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel main = (JPanel)frame.getContentPane();
		main.setLayout(new BorderLayout());
		final JList diceList = new JList(diceModel);
		diceList.setCellRenderer(new DefaultListCellRenderer(){
			private static final long serialVersionUID = 1L;

			@Override
			public Component getListCellRendererComponent(JList list,
					Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				Class<?> clazz = (Class<?>) value;
				super.getListCellRendererComponent(diceList, value, index, isSelected, cellHasFocus);
				this.setText(clazz.getSimpleName());
				return this;
			}
		});
		JScrollPane jsp = new JScrollPane(diceList);
		main.add(jsp,BorderLayout.CENTER);
		main.add(new JLabel("Here is current loaded dice :"),BorderLayout.NORTH);
		JPanel east = new JPanel(null);
		east.setLayout(new GridBagLayout());
		JButton addJar = new JButton("Add jar");
		addJar.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.addChoosableFileFilter(new FileNameExtensionFilter("Jar File","jar"));
				//chooser.setFileFilter(ff);
				int retValue = chooser.showDialog(frame,"Validate");
				if ( retValue == JFileChooser.APPROVE_OPTION){ 
					File file = chooser.getSelectedFile();
					diceModel.addJar(file.getAbsolutePath());
				}
			}
		});
		JButton close = new JButton("Close");
		close.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
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
		main.add(east,BorderLayout.EAST);
		frame.setSize(400,200);
		frame.setVisible(true);
	}*/
}