package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 899031886423975580L;

	
	private List<Body> bodiesList;
	private String[] columnNames = {"Id", "Mass", "Position", "Velocity", "Force"};
	
	
	public BodiesTableModel(Controller ctrl) {
		bodiesList = new ArrayList<Body>();
		ctrl.addObserver(this);
	}
	

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return bodiesList.size();
	}
	
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		Body body = bodiesList.get(arg0);
		String s = "";
		
		switch(arg1) {
		case 0:
			s = body.getId(); 
			break;
		case 1:
			s = "" + body.getMass(); 
			break;
		case 2:
			s = body.getPosition().toString();
			break;
		case 3:
			s = body.getVelocity().toString();
			break;
		case 4:
			s = body.getForce().toString();
			break;
		}
		
		return s;
		
	}
	
	private void update(List<Body> bodies) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				bodiesList = bodies;
				fireTableStructureChanged();
				
			}
			
		});
	}
	
	

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		update(bodies);
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		update(bodies);
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		update(bodies);
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		update(bodies);
	}

	@Override
	public void onDeltaTimeChanged(double dt) {}

	@Override
	public void onForceLawsChanged(String fLawsDesc) {}

}
