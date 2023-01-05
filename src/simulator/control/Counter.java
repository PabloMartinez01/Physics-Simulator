package simulator.control;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;

import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class Counter implements SimulatorObserver{
	
	
	private Map<String, Integer> counter;
	private Map<String, Double> lastState;
	private int mode;
	
	public Counter(int mode, Controller ctrl) {
		this.counter = new HashMap<String, Integer>();
		this.lastState = new HashMap<String, Double>();
		this.mode = mode;
		ctrl.addObserver(this);
	}
	
	public void print() {
		for (Entry<String, Integer> e : counter.entrySet()) {
			System.out.println(e.getKey() + " " + e.getValue());
		}
	}
	

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		for (Body b : bodies) {
			counter.put(b.getId(), 0);
			lastState.put(b.getId(), b.getPosition().asJSONArray().getDouble(mode));
		}
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		counter.clear();
		lastState.clear();
		for (Body b : bodies) {
			counter.put(b.getId(), 0);
			lastState.put(b.getId(), b.getPosition().asJSONArray().getDouble(mode));
		}
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		counter.put(b.getId(), 0);
		lastState.put(b.getId(), b.getPosition().asJSONArray().getDouble(mode));
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		
		for (Body b : bodies) {
			
			if (b.getPosition().asJSONArray().getDouble(mode) >= 0 && lastState.get((String) b.getId()) < 0) {
				counter.put(b.getId(), counter.get((String) b.getId()) + 1);
			}
			else if (b.getPosition().asJSONArray().getDouble(mode) < 0 && lastState.get((String) b.getId()) >= 0) {
				counter.put(b.getId(), counter.get((String) b.getId()) + 1);
			}
			lastState.put(b.getId(), b.getPosition().asJSONArray().getDouble(mode));
			
			
			
		}
		
		
	}

	@Override
	public void onDeltaTimeChanged(double dt) {}

	@Override
	public void onForceLawsChanged(String fLawsDesc) {}

}
