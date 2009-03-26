package test;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;



public class TestTable {
	public static void main(String[] args) {
		JFrame f = new JFrame("ja");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(300,400);
		JPanel main = new JPanel();
		f.setContentPane(main);
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("c1");
		model.addColumn("c2");
		model.addRow(new Object[]{1,"popi2"});
		model.addRow(new String[]{"hum","hum2"});
		JTable t = new JTable(model);
		main.add(t);
		f.setVisible(true);
	}
}
