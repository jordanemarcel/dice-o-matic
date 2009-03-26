package fr.umlv.ig.misc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.ParseException;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.ListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PanelFactory {

	public static JPanel newWorkspacePanel(final DiceModel model) {
		JPanel newWorkspace = new JPanel();
		newWorkspace.setLayout(new BorderLayout());



		//JLabel title = new JLabel("Select your dices:");
		//title.setFont(new Font("Arial",Font.BOLD,16));
		//newWorkspace.add(title, BorderLayout.NORTH);

		JButton next = new JButton("Next");


		//JButton next = new JButton("Validate");
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



		//	for(Class<? extends Dice> clazz: Main.dices) {


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

	@SuppressWarnings("serial")
	public static JPanel createSessionPanel(){
		final JPanel panel = new JPanel(new BorderLayout());
		final JPanel leftPanel = new JPanel(new GridBagLayout());

		final JFormattedTextField throwCountField = new JFormattedTextField(new AbstractFormatter(){
			@Override
			public Integer stringToValue(String text) throws ParseException {
				try{
					return new Integer(text);
				}catch(NumberFormatException e){
					throw new ParseException("Bad number",0);
				}
			}
			@Override
			public String valueToString(Object value) throws ParseException {
				if(value==null)
					return null;
				try{
					return new Integer(value.toString()).toString();
				}catch(NumberFormatException e){
					throw new ParseException("Bad number",0);
				}
			}
		});
		//throwCountField.setColumns(5);
		throwCountField.addCaretListener(new CaretListener(){
			@Override
			public void caretUpdate(CaretEvent e) {
				try {
					throwCountField.commitEdit();
					throwCountField.setForeground(Color.black);
				} catch (ParseException e1) {
					throwCountField.setForeground(Color.red);
				}
			}
		});

		final GridBagConstraints rightgbc = new GridBagConstraints();
		rightgbc.fill = GridBagConstraints.HORIZONTAL;
		rightgbc.gridwidth = GridBagConstraints.REMAINDER;

		GridBagConstraints leftgbc = new GridBagConstraints();
		leftgbc.gridwidth = GridBagConstraints.REMAINDER;
		leftgbc.fill = GridBagConstraints.HORIZONTAL;
		leftgbc.insets=new Insets(5,0,0,0);
		JButton throwAgain = new JButton("Throw again");
		JPanel rethrow = new JPanel(new GridLayout(3,1));
		rethrow.setBorder(BorderFactory.createTitledBorder("Throw it again!"));
		rethrow.add(new JLabel("Throw count :"));
		rethrow.add(throwCountField);
		rethrow.add(throwAgain);
		JPanel graphManager = new JPanel(new GridLayout(3,1));
		graphManager.setBorder(BorderFactory.createTitledBorder("View Creator"));
		graphManager.add(new JLabel("Selected dice for next view :"));

		JFormattedTextField selecetedDice = new JFormattedTextField(new AbstractFormatter(){
			@Override
			public Object stringToValue(String text) throws ParseException {
				return null;
			}
			@Override
			public String valueToString(Object value) throws ParseException {
				return null;
			}
		});
		graphManager.add(selecetedDice);
		JButton createView = new JButton("Create View");
		graphManager.add(createView,leftgbc);
		leftPanel.add(graphManager,leftgbc);
		leftPanel.add(rethrow,leftgbc);
		leftgbc.weighty=1;
		leftPanel.add(new JPanel(),leftgbc);
		//right panel
		final JPanel rightPanel = new JPanel(new GridBagLayout());
		final JScrollPane rightJsp = new JScrollPane(rightPanel);
		createView.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				rightPanel.add(createGraphicPanel(new JButton("bla")),rightgbc );
				rightJsp.validate();
			}	
		});	

		//add all on the main panel
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true,leftPanel,rightJsp);
		panel.add(split,BorderLayout.CENTER);
		return panel;
	}
	private static JPanel createGraphicPanel(JComponent component){
		final JPanel panel = new JPanel(new BorderLayout());
		ImageIcon icon = new ImageIcon("dialogCloseButton.png");
		ImageIcon overIcon = new ImageIcon("overCloseButton.png");
		ImageIcon pressedIcon = new ImageIcon("pressedCloseButton.png");
		JButton close = new JButton(icon);
		close.setRolloverIcon(overIcon);
		close.setPressedIcon(pressedIcon);
		close.setBorder(null);
		close.setSize(close.getMinimumSize());
		close.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Container innerJspPanel = panel.getParent();
				innerJspPanel.remove(panel);
				innerJspPanel.getParent().validate();
				innerJspPanel.repaint();
			}
		});
		JPanel title = new JPanel(new BorderLayout());
		title.setBackground(Color.BLACK);

		title.add(close,BorderLayout.EAST);
		title.add(new JLabel(" "),BorderLayout.CENTER);

		panel.add(title,BorderLayout.NORTH);
		panel.add(component,BorderLayout.CENTER);
		return panel;
	}
}  
