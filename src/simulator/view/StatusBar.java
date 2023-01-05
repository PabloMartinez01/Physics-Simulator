package simulator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class StatusBar extends JPanel implements SimulatorObserver {
	private static final long serialVersionUID = -2968013173547630455L;

	// ...
	private JLabel _currTime; // for current time
	private JLabel _currLaws; // for gravity laws
	private JLabel _numOfBodies; // for number of bodies
	

	StatusBar(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
	}

	private void initGUI() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(BorderFactory.createBevelBorder(1));
		
		// TODO complete the code to build the tool bar
		
		_currTime = new JLabel();
		_currTime.setPreferredSize(new Dimension(80, 20));
		_numOfBodies = new JLabel();
		_numOfBodies.setPreferredSize(new Dimension(80, 20));
		_currLaws = new JLabel();
		
		JSeparator separador = new JSeparator(JSeparator.VERTICAL);
		separador.setPreferredSize(new Dimension(10, 0));
		
		this.add(new JLabel("Time: "));
		
		this.add(_currTime);
		this.add(separador);
		this.add(new JLabel("Bodies: "));
		this.add(_numOfBodies);
		this.add(separador);
		this.add(new JLabel("Laws: "));
		this.add(_currLaws);
		
	}
	// other private/protected methods
	// ...
	
	
	
	// SimulatorObserver methods
	// ...

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		// TODO Auto-generated method stub
		_currTime.setText("" + time);
		_numOfBodies.setText("" + bodies.size());
		_currLaws.setText("" + fLawsDesc);

	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		// TODO Auto-generated method stub
		_currTime.setText("" + time);
		_numOfBodies.setText("" + bodies.size());
		_currLaws.setText("" + fLawsDesc);
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		// TODO Auto-generated method stub
		_numOfBodies.setText("" + bodies.size());
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		// TODO Auto-generated method stub
		_currTime.setText("" + time);
		_numOfBodies.setText("" + bodies.size());

	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onForceLawsChanged(String fLawsDesc) {
		// TODO Auto-generated method stub
		_currLaws.setText("" + fLawsDesc);
	}

}
