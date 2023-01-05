package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class DistancesTableModel extends AbstractTableModel implements SimulatorObserver{
	

	private static final long serialVersionUID = 3500399478468135031L;
	private String[] columNames = {"#", "Min Dist.", "Max Dist"};
	private List<StepDistances> steps;
	private int contador;
	
	public DistancesTableModel(Controller ctrl) {
		steps = new ArrayList<StepDistances>();
		contador = 0;
		ctrl.addObserver(this);
		
	}

	@Override
	public int getColumnCount() {
		return columNames.length;	
	}

	@Override
	public int getRowCount() {
		return steps.size();
	}
	
	public String getColumnName(int column) {
		return columNames[column];
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		StepDistances step = steps.get(arg0);
		String s = "";
		
		switch(arg1) {
		case 0:
			s = Integer.toString(step.getStep()); 
			break;
		case 1:
			s = "" + step.getMin().getId() + " : " + step.getMin().getPosition().distanceTo(new Vector2D()); 
			break;
		case 2:
			s = "" + step.getMax().getId() + " : " + step.getMax().getPosition().distanceTo(new Vector2D());
			break;
		
		}
		
		return s;
	}
	
	private Body buscarMin(List<Body> bodies) {
		if (bodies.size() == 0) return null;
		Body b = bodies.get(0);
		
		for(int i = 1; i < bodies.size(); i++) {
			if (bodies.get(i).getPosition().distanceTo(new Vector2D()) < b.getPosition().distanceTo(new Vector2D())) {
				b = bodies.get(i);
			}
		}
		
		return new Body(b.getId(), b.getVelocity(), b.getPosition(), b.getMass());
		
	}
	
	
	private Body buscarMax(List<Body> bodies) {
		if (bodies.size() == 0) return null;
		Body b = bodies.get(0);
		
		for(int i = 1; i < bodies.size(); i++) {
			if (bodies.get(i).getPosition().distanceTo(new Vector2D()) > b.getPosition().distanceTo(new Vector2D())) {
				b = bodies.get(i);
			}
		}
		
		return new Body(b.getId(), b.getVelocity(), b.getPosition(), b.getMass());
		
	}
	

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		contador = 0;
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		steps.clear();
		contador = 0;
		fireTableStructureChanged();
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		// TODO Auto-generated method stub
		if (buscarMin(bodies) != null && buscarMax(bodies) != null) {
			steps.add(new StepDistances(contador++, buscarMin(bodies), buscarMax(bodies)));
		}
		fireTableStructureChanged();
		
	}

	@Override
	public void onDeltaTimeChanged(double dt) {}
	@Override
	public void onForceLawsChanged(String fLawsDesc) {}

}
