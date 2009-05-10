package fr.umlv.ig.misc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import fr.umlv.ig.cheatIr.model.DiceSelectorModel;
import fr.umlv.ig.cheatIr.model.DiceThrower;
import fr.umlv.ig.cheatIr.model.PrintListModel;
import fr.umlv.ig.cheatIr.model.StatDiceModel;
import fr.umlv.ig.cheatIr.model.TotalThrowModel;
import fr.umlv.ig.graph.JGraph;
import fr.umlv.ig.graph.JScrollablePanel;
import fr.umlv.ig.graph.JGraph.GraphType;

public class PanelFactory {
	public static JPanel newWorkspacePanel(final JFrame parent ,final DiceModel model) {
		JPanel newWorkspace = new JPanel();
        newWorkspace.setLayout(new BorderLayout());
        JButton validate = new JButton("Validate");
		validate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(model.getTotalDiceNumber()<=0)
					return;
				LinkedList<Dice> ll = launchDiceConfiguration(parent,model);
				if(ll == null)
					return;
				
				DiceThrower dt = new DiceThrower(ll);
				parent.setContentPane(createSessionPanel(dt));
				parent.validate();
				parent.repaint();
			}
		});
		JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
        southPanel.add(validate);
        validate.setAlignmentX(1);
        newWorkspace.add(southPanel, BorderLayout.SOUTH);

        JPanel dicePanel = PanelFactory.getDiceSelectorPanel(model);
        final JScrollPane scrollPane = new JScrollPane(dicePanel);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Select your dices"));
        newWorkspace.add(scrollPane, BorderLayout.CENTER);
        model.addJarDiceListener(new DiceListener() {
                @Override
                public void diceAdded() {
                        scrollPane.setViewportView(PanelFactory.getDiceSelectorPanel(model));
                }
                @Override
                public void diceValueChanged() {
                        /* */
                }
        });
        return newWorkspace;
	}

	 private static JPanel getDiceSelectorPanel(final DiceModel model) {
         JPanel dicePanel = new JPanel();
         dicePanel.setLayout(new GridBagLayout());
         GridBagConstraints constraints = new GridBagConstraints();
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
                 dicePanel.add(new JLabel(clazz.getSimpleName()), constraints);
                 SpinnerNumberModel snm = new DiceSpinnerNumberModel(0, Integer.MAX_VALUE, 1, clazz, model);
                 JSpinner spin = new JSpinner(snm);
                 constraints.gridwidth = GridBagConstraints.REMAINDER;
                 dicePanel.add(spin, constraints);
                 constraints.insets = bottomSpace;
                 constraints.gridwidth = GridBagConstraints.REMAINDER;
                 JLabel descriptionLabel = new JLabel(description.value()[0]);
                 descriptionLabel.setFont(new Font("Arial", Font.ITALIC, 12));
                 dicePanel.add(descriptionLabel, constraints);
                 constraints.insets = noSpace;
         }
         constraints.weighty = 1;
         constraints.weightx = 1;
         dicePanel.add(new JPanel(), constraints);
         return dicePanel;
 }
	
	private static LinkedList<Dice> launchDiceConfiguration(Component parent, DiceModel model){
		Iterator<Class<? extends Dice>> it =  model.getIterator();
		final LinkedList<Dice> diceList = new LinkedList<Dice>();
		while (it.hasNext()) {
			Class<? extends Dice> diceClass = (Class<? extends Dice>) it.next();
			Integer nb = model.getDiceNumber(diceClass);
			for(int i=0;i<nb;i++){
				if(diceClass.equals(FairDice.class)){
					diceList.add(new FairDice());
					continue;
				}
				JDialog dialog = new JDialog((JFrame)parent);
				final JPanel p = createConfigurationDicePanel(dialog,diceClass,diceList);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						p.setEnabled(false);
					}
				});
				dialog.setTitle("Configure the dice");
				dialog.setContentPane(p);
				dialog.pack();
				dialog.setLocationRelativeTo(parent);
				dialog.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
				dialog.setVisible(true);
				if(!p.isEnabled())
					return null;
			}
		}
		return diceList;

	}

	private static JPanel createConfigurationDicePanel(final JDialog window, Class<? extends Dice> diceClass,final LinkedList<Dice> diceList){
		JPanel mainPanel = new JPanel(new BorderLayout());
		final Constructor<?> constructor = diceClass.getConstructors()[0];
		DiceDescription descriptionFields = constructor.getAnnotation(DiceDescription.class);
		final Class<?> typesArray[] = constructor.getParameterTypes();
		String text[] = descriptionFields.value();
		final JTextField fields[] = new JTextField[typesArray.length];
		//panel.setBorder(BorderFactory.createTitledBorder(text[0]));
		JPanel panel = new JPanel(new GridLayout((text.length-1),2));
		JLabel desc = new JLabel(text[0]);
		panel.add(desc);
		for(int i=1;i<text.length;i++){
			panel.add(new JLabel(text[i]));
			fields[i-1] = new JTextField();
			panel.add(fields[i-1]);
		}
		JButton valid = new JButton("Validate");
		mainPanel.add(desc,BorderLayout.NORTH);
		mainPanel.add(panel,BorderLayout.CENTER);
		JPanel p = new JPanel(new BorderLayout());
		p.add(valid,BorderLayout.EAST);
		mainPanel.add(p,BorderLayout.SOUTH);
		valid.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					Object args[] = new Object[typesArray.length];
					for(int i=0;i<typesArray.length;i++){
						if(typesArray[i].getCanonicalName().equals("int")){
							args[i] = Integer.parseInt(fields[i].getText());
						}
						if(typesArray[i].getCanonicalName().equals("float")){
							args[i] = Float.parseFloat(fields[i].getText());
						}
						if(typesArray[i].getCanonicalName().equals("String")){
							args[i] = fields[i].getText();
						}
					}
					Dice d = (Dice) constructor.newInstance(args);
					diceList.add(d);

				}catch (NumberFormatException exception) {
					showFieldErrorDialog(window);
					return;
					//There is nothing to do, we have to wait that the user put correct value
				} catch (IllegalArgumentException exception) {
					showFieldErrorDialog(window);
					return;
					//There is nothing to do, we have to wait that the user put correct value
				} catch (InstantiationException exception) {
					showFieldErrorDialog(window);
					return;
					//There is nothing to do, we have to wait that the user put correct value
				} catch (IllegalAccessException exception) {
					showFieldErrorDialog(window);
					return;
					//There is nothing to do, we have to wait that the user put correct value
				} catch (InvocationTargetException exception) {
					showFieldErrorDialog(window);
					return;
					//There is nothing to do, we have to wait that the user put correct value
				}
				window.dispose();
			}
		});
		return mainPanel;
	}
	private static void showFieldErrorDialog(Component parent){
		JOptionPane.showMessageDialog(parent,"Incorrect field value! Please fill field with valid parameter.","Error",JOptionPane.ERROR_MESSAGE);	
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
		JPanel east = new JPanel(new GridBagLayout());
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
	public static JPanel createSessionPanel(final DiceThrower thrower){
		final TotalThrowModel total = new TotalThrowModel();
		final int diceCount = thrower.getDices().size();
		/* **************************************/
		//Formatter Start
		AbstractFormatter integerFormatter = new AbstractFormatter(){
			@Override
			public Integer stringToValue(String text) throws ParseException {
				try{
					Integer i =  new Integer(text);
					if(i>0)
						return i;
					throw new ParseException("Bad number",0);
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
		};


		AbstractFormatter selectorFormatter = new AbstractFormatter(){
			@Override
			public Object stringToValue(String text) throws ParseException {
				try{
					HashSet<Integer> set = new HashSet<Integer>();
					String parts[] = text.split(",");
					for(String s : parts){
						String parts2[] = s.split("-");
						if(parts2.length==2){
							int start = Integer.parseInt(parts2[0]);
							int fin = Integer.parseInt(parts2[1]);
							for(int i = start; i<=fin ;i++){
								if(i>0 && i<=diceCount){
									set.add(i-1);
								}else{
									throw new ParseException("Parse error",0);
								}
							}
						}else if(parts2.length==1){
							int i = Integer.parseInt(parts2[0]);
							if(i>0 && i<=diceCount){
								set.add(i-1);
							}else{
								throw new ParseException("Parse error",0);
							}
						}else{
							throw new ParseException("Parse error",0);
						}
					}
					return set;
				}catch (Exception e) {
					throw new ParseException("Parse error",0);
				}
			}
			@SuppressWarnings({ "unchecked", "null" })
			@Override
			public String valueToString(Object value) throws ParseException {
				/* This cast is sure because I know that stringToValue return a Set<Integer>*/
				Set<Integer> set = (Set<Integer>) value;
				if(set==null)
					throw new ParseException("Parse error",0);
				TreeSet<Integer> sorted = new TreeSet<Integer>();
				sorted.addAll(set);
				StringBuilder sb = new StringBuilder();
				Integer last = null;
				while(!sorted.isEmpty()){
					Integer i = sorted.pollFirst();
					if(last!=null){
						if(last==(i-1)){
							while(i!=null && last==(i-1)){
								last = i;
								i = sorted.pollFirst();
							}
							sb.append("-");
							sb.append(last+1);
						}
						if(i==null)
							continue;
						sb.append(",");
					}
					//i cannot be null!  
					sb.append(i+1);
					last = i;
				}
				return sb.toString();
			}
		};
		//Formatter End
		/* *********************************** */
		//left panel Start
		final JPanel leftPanel = new JPanel(new GridBagLayout());
		GridBagConstraints leftgbc = new GridBagConstraints();
		leftgbc.gridwidth = GridBagConstraints.REMAINDER;
		leftgbc.fill = GridBagConstraints.HORIZONTAL;
		leftgbc.weighty = 0;
		leftgbc.weightx = 1;
		leftgbc.insets=new Insets(0,0,0,0);
		JPanel rethrow = new JPanel(new GridLayout(3,1));
		rethrow.setBorder(BorderFactory.createTitledBorder("Throw dice!"));
		rethrow.add(new JLabel("Throw count :"));
		final JFormattedTextField throwCountField = new JFormattedTextField(integerFormatter);
		rethrow.add(throwCountField);
		final JButton throwButton = new JButton("Throw !");
		rethrow.add(throwButton);
		final JPanel graphManager = new JPanel(new GridLayout(5,1));
		graphManager.setBorder(BorderFactory.createTitledBorder("View Creator"));
		graphManager.add(new JLabel("Selected dice for next view :"));
		final JFormattedTextField selecetedDice = new JFormattedTextField(selectorFormatter);
		selecetedDice.setText("1-"+thrower.getDices().size());
		graphManager.add(selecetedDice);
		graphManager.add(new JLabel("Sampling :"));
		final JFormattedTextField sampling = new JFormattedTextField(integerFormatter);
		sampling.setText("1");
		graphManager.add(sampling);
		final JButton createView = new JButton("Create View");
		createView.setEnabled(false);
		graphManager.add(createView,leftgbc);
		final JPanel resultPanel = new JPanel(new BorderLayout());
		resultPanel.setBorder(BorderFactory.createTitledBorder("Results"));
		final PrintListModel printModel = new PrintListModel(total);
		final JList listResult = new JList(printModel);
		//listResult.setVisibleRowCount(0);
		JScrollPane resultJsp = new JScrollPane(listResult);
		//resultPanel.setVisible(false);
		resultPanel.add(resultJsp);
		JCheckBox sortButton = new JCheckBox("Shuffle result");
		resultPanel.add(sortButton,BorderLayout.SOUTH);
		leftPanel.add(rethrow,leftgbc);
		leftPanel.add(graphManager,leftgbc);
		leftPanel.add(sortButton,leftgbc);
		leftgbc.weighty=1;
		leftgbc.weightx=1;
		leftgbc.fill = GridBagConstraints.BOTH;
		leftgbc.gridheight = GridBagConstraints.REMAINDER;
		leftPanel.add(resultPanel,leftgbc);
		//Left panel End
		/* *********************************** */

		/* *********************************** */
		//Right Panel Start
		final JScrollablePanel rightPanel = new JScrollablePanel(new GridBagLayout());
		final GridBagConstraints rightgbc = new GridBagConstraints();
		rightgbc.fill = GridBagConstraints.HORIZONTAL;
		rightgbc.gridwidth = GridBagConstraints.REMAINDER;
		rightgbc.insets=new Insets(0,0,0,0);
		rightgbc.weighty=1;
		rightgbc.weightx=1;		
		final JPanel filler = new JPanel();
		rightPanel.add(filler,rightgbc);
		//Right panel End
		/* *********************************** */

		/* *********************************** */
		//Main panel Start
		final JScrollPane rightJsp = new JScrollPane(rightPanel);
		rightJsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		final JPanel panel = new JPanel(new BorderLayout());
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true,leftPanel,rightJsp);
		panel.add(split,BorderLayout.CENTER);
		//Main panel End
		/* *********************************** */

		/* *********************************** */
		//Action  Start
		sortButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(printModel.isShuffle())
					printModel.setShuffle(false);
				else
					printModel.setShuffle(true);
			}
		});

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

		sampling.addCaretListener(new CaretListener(){
			@Override
			public void caretUpdate(CaretEvent e) {
				try {
					sampling.commitEdit();
					sampling.setForeground(Color.black);
				} catch (ParseException e1) {
					sampling.setForeground(Color.red);
				}
			}
		});

		selecetedDice.addCaretListener(new CaretListener(){
			@Override
			public void caretUpdate(CaretEvent e) {
				try {
					selecetedDice.commitEdit();
					selecetedDice.setForeground(Color.black);
				} catch (ParseException e1) {
					selecetedDice.setForeground(Color.red);
				}
			}
		});

		createView.addActionListener(new ActionListener(){
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent e) {
				rightgbc.weighty=0;
				rightPanel.remove(filler);
				DiceSelectorModel selector = new DiceSelectorModel(total);
				AbstractFormatter setFormatter = selecetedDice.getFormatter();
				AbstractFormatter intFormatter = sampling.getFormatter();
				Integer sample = 1;
				try {
					/* This cast is sure because this JFormattedTextField use my own Formatter and it s return a Set<Integer>*/
					Set<Integer> set = (Set<Integer>) setFormatter.stringToValue(selecetedDice.getText());
					selector.setSelectedDices(set);
					sample = (Integer) intFormatter.stringToValue(sampling.getText());
				} catch (ParseException e1) {
					//Impossible because a filter is applied to the TextField so it contains valid data
				}
				StatDiceModel stat = new StatDiceModel(selector,sample);
				final JGraph g = new JGraph(stat);
				g.addMouseListener(new MouseAdapter(){
					@Override
					public void mouseClicked(MouseEvent e) {
						if(e.getButton()!=MouseEvent.BUTTON1)
							return;
						GraphType type = g.getGraphType();
						if(type!=GraphType.HISTOGRAM){
							g.setGraphType(GraphType.HISTOGRAM);
						}else{
							g.setGraphType(GraphType.PIE);
						}
					}
				});
				String title = selector.getRowCount()+" throws on "+selector.getColumnCount()+" dice"
				+" sampled by "+sample;
				rightPanel.add(createGraphicPanel(g,title),rightgbc);
				rightgbc.weighty=1;
				rightPanel.add(filler,rightgbc);
				rightgbc.weighty=0;
				rightJsp.revalidate();
			}	
		});
		throwButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String s = throwCountField.getText();
				if(s.equals(""))
					return;
				createView.setEnabled(true);
				throwButton.setText("Throw again");
				//resultPanel.setVisible(true);
				thrower.throwDices(Integer.parseInt(s), total);
			}
		});
		/* Action End */
		/* *********************************** */
		return panel;
	}
	public static JPanel createGraphicPanel(JGraph component,String graphTitle){
		final JPanel panel = new JPanel(new BorderLayout());
		ImageIcon icon = new ImageIcon("dialogCloseButton.png");
		ImageIcon overIcon = new ImageIcon("overCloseButton.png");
		ImageIcon pressedIcon = new ImageIcon("pressedCloseButton.png");
		JButton close = new JButton(icon);
		close.setRolloverIcon(overIcon);
		close.setPressedIcon(pressedIcon);
		close.setBorder(null);
		close.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel innerPanel = (JPanel)panel.getParent();
				innerPanel.remove(panel);
				innerPanel.revalidate();
				innerPanel.repaint();
			}
		});
		JPanel title = new JPanel(new BorderLayout());
		title.setBackground(Color.BLACK);
		title.add(close,BorderLayout.EAST);
		JLabel name = new JLabel("Graph : "+graphTitle);
		name.setForeground(Color.WHITE);
		name.setPreferredSize(new Dimension(0,0));
		title.add(name,BorderLayout.CENTER);
		panel.add(title,BorderLayout.NORTH);
		JScrollPane jsp = new JScrollPane(component);
		jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		panel.add(jsp,BorderLayout.CENTER);
		return panel;
	}
}