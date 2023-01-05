package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import simulator.control.Controller;

public class MainWindow extends JFrame{
	//...
	Controller _ctrl;
	
	public MainWindow(Controller ctrl) {
		super("Physics Simulator");
		_ctrl = ctrl;
		initGUI();
	}
	
	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);
		this.setSize(800,600);
		mainPanel.add(new ControlPanel(_ctrl), BorderLayout.NORTH);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.add(new BodiesTable(_ctrl));
		centerPanel.add(new DistancesTable(_ctrl));
		
		centerPanel.add(new Viewer(_ctrl));
		
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		
		mainPanel.add(new StatusBar(_ctrl), BorderLayout.SOUTH);
		
		
		this.setVisible(true);
	
		
		
	}
	// other private/protected methods
}
