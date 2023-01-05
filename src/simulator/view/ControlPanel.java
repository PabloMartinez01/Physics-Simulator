package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class ControlPanel extends JPanel implements SimulatorObserver {
/**
	 * 
	 */
	private static final long serialVersionUID = 3272954499506331538L;
	// ...
	private Controller _ctrl;
	private boolean _stopped;
	
	private JToolBar toolBar;
	
	private JFileChooser fileChooser;
	private JButton openButton;
	private JButton physicsButton;
	private JButton runButton;
	private JButton stopButton;
	private JButton exitButton;
	
	private JSpinner stepsSpinner;
	private JTextField textField;
	
	private ForceLawsDialog forceLawsDialog;
	
	ControlPanel(Controller ctrl) {
		_ctrl = ctrl;
		_stopped = true;
		initGUI();
		_ctrl.addObserver(this);
	}
	
	private void initGUI() {
		this.setLayout(new BorderLayout());
		toolBar = new JToolBar();
		this.add(toolBar, BorderLayout.PAGE_START);
		
		fileChooser = new JFileChooser();	
		
		openButton = createButton("resources/icons/open.png");
		openButton.setToolTipText("open a file");
		openButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				openButtonAction();
			}
			
		});
		
		
		
		physicsButton = createButton("resources/icons/physics.png");
		physicsButton.setToolTipText("change the law force");
		physicsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				physicsButtonAction();	
			}
			
		});
		
		runButton = createButton("resources/icons/run.png");
		runButton.setToolTipText("run the simulator");
		runButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				runButtonAction();	
			}
			
		});
		
		stopButton = createButton("resources/icons/stop.png");
		stopButton.setToolTipText("stop the simulator");
		stopButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				stopButtonAction();		
			}
			
		});
		
		exitButton = createButton("resources/icons/exit.png");
		exitButton.setToolTipText("exit");
		exitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				exitButtonAction();
			}
			
		});
		
		stepsSpinner = new JSpinner(new SpinnerNumberModel(10000, 1, 20000, 100));
		Dimension d = new Dimension(80, 40);
		stepsSpinner.setMaximumSize(d);
		stepsSpinner.setMinimumSize(d);
		stepsSpinner.setPreferredSize(d);
		
		textField = new JTextField(5);
		textField.setText("2500");
		
		textField.setMaximumSize(d);
		textField.setMinimumSize(d);
		textField.setPreferredSize(d);
		
		
		
		
		toolBar.add(openButton);
		toolBar.addSeparator();
		toolBar.add(physicsButton);
		toolBar.addSeparator();
		toolBar.add(runButton);
		toolBar.addSeparator();
		toolBar.add(stopButton);
		toolBar.addSeparator();
		toolBar.add(new JLabel("steps: "));
		toolBar.add(stepsSpinner);
		toolBar.addSeparator();
		toolBar.add(new JLabel("delta-time: "));
		toolBar.add(textField);
		toolBar.add(Box.createGlue());
		toolBar.addSeparator();
		toolBar.add(exitButton);
		
	}
	
	
	// other private/protected methods
	// ...
	
	private JButton createButton(String icon) {
		JButton button = new JButton();
		button.setIcon(new ImageIcon(icon));
		return button;
	}
	
	private void openButtonAction() {
		int selection = fileChooser.showOpenDialog(null);
		if (selection == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			try {
				InputStream inputFile = new FileInputStream(file);
				_ctrl.reset();
				_ctrl.loadBodies(inputFile);
			}
			catch(IOException e) {
				e.printStackTrace();
			}	
			
		}
	}
	
	private void physicsButtonAction() {
		if (forceLawsDialog == null) {
			forceLawsDialog = new ForceLawsDialog((Frame) SwingUtilities.getWindowAncestor(this), _ctrl);
		}
		int status = forceLawsDialog.open();
		if (status == 1) {
			try {
				JSONObject object = forceLawsDialog.getSelectedLaws();
				_ctrl.setForceLaws(object);
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(this.getParent(), "Something went wrong: " + e.getLocalizedMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}
		

	}
	
	private void runButtonAction() {
		changeStateButtons(false);
		_stopped = false; 
		
		try {
			_ctrl.setDeltaTime(Integer.parseInt(textField.getText()));
			run_sim((int) stepsSpinner.getValue());
			
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(this.getParent(), "Something went wrong : "+ e.getLocalizedMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			_stopped = true;
			changeStateButtons(true);
		}
				
	}
	
	private void stopButtonAction() {
		_stopped = true;
		changeStateButtons(true);
	}
	
	private void exitButtonAction() {
		int option = JOptionPane.showOptionDialog(new JFrame(), "Are sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null );
		if (option == 0) System.exit(0);
	}
	
	private void changeStateButtons(boolean state) {
		openButton.setEnabled(state);
		physicsButton.setEnabled(state);
		runButton.setEnabled(state);
		exitButton.setEnabled(state);
	}
	
	
	private void run_sim(int n) {
		if ( n>0 && !_stopped ) {
			try {
				_ctrl.run(1);
			} catch (Exception e) {
				// TODO show the error in a dialog box
				// TODO enable all buttons
				changeStateButtons(true);
				_stopped = true;
				return;
			}
			SwingUtilities.invokeLater( new Runnable() {
				@Override
				public void run() {
					run_sim(n-1);
				}
			});
		} 
		else {
			_stopped = true;
			// TODO enable all buttons
			changeStateButtons(true);
		}
	}

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onForceLawsChanged(String fLawsDesc) {
		// TODO Auto-generated method stub
		
	}
	

}

