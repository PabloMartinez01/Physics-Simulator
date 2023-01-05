package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import simulator.control.Controller;

public class DistancesTable extends JPanel {
	
	public DistancesTable(Controller ctrl) {
		this.setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.black, 2),
				"Distances",
				TitledBorder.LEFT, TitledBorder.TOP));
		
		this.setPreferredSize(new Dimension(1000, 200));
		DistancesTableModel model = new DistancesTableModel(ctrl);
		JTable table = new JTable(model);
		table.setFillsViewportHeight(true);
		
		this.add(new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
	}
}
