package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class PhysicsSimulator {
	
	private double dtime; 
	private ForceLaws forceLaw;
	private List<Body> bodiesList;
	private double time; 
	
	private List<SimulatorObserver> observers;
	
	public PhysicsSimulator(double dtime, ForceLaws forceLaw){
		if (dtime < 0 || forceLaw == null) throw new IllegalArgumentException("Invalid");
		this.dtime = dtime;
		this.forceLaw = forceLaw;
		this.time = 0.0;
		bodiesList = new ArrayList<Body>();
		observers = new ArrayList<SimulatorObserver>();
	}
	
	public void reset() {
		bodiesList.clear();
		time = 0.0;
		for (SimulatorObserver o : observers) {
			o.onReset(bodiesList, time, dtime, forceLaw.toString());
		}
	}
	
	public void setDeltaTime(double dt) {
		if (dt < 0) throw new IllegalArgumentException("Invalid dt");
		this.dtime = dt;
		for (SimulatorObserver o : observers) {
			o.onDeltaTimeChanged(dt);
		}
	}
	
	public void setForceLaws(ForceLaws forceLaws) {
		if (forceLaws == null ) throw new IllegalArgumentException("Invalid forcelaw");
		this.forceLaw = forceLaws;
		for (SimulatorObserver o : observers) {
			o.onForceLawsChanged(forceLaws.toString());
		}
	}
	
	public void Advance() {
		for (Body bodyObject : bodiesList) {
			bodyObject.resetForce();
		}
		
		forceLaw.apply(bodiesList);
		
		for (Body bodyObject : bodiesList) {
			bodyObject.move(dtime);
		}
		
		time += dtime;
		
		for (SimulatorObserver o : observers) {
			o.onAdvance(bodiesList, time);
		}
	}
	
	public void addBody(Body b) throws IllegalArgumentException {
		if (bodiesList.contains(b)) throw new IllegalArgumentException(); //añadir mensaje
		bodiesList.add(b);
		for (SimulatorObserver o : observers) {
			o.onBodyAdded(bodiesList, b);
		}
	}
	

	
	public void addObserver(SimulatorObserver o) {
		if (observers.contains(o)) throw new IllegalArgumentException("Observer is already in the list");
		observers.add(o);
		o.onRegister(bodiesList, time, dtime, forceLaw.toString());
	}
	
	
	public JSONObject getState() {
		JSONObject object = new JSONObject();
		object.put("time", time);
		
		JSONArray array = new JSONArray();
		for (Body body : bodiesList) {
			array.put(body.getState());
		}
		
		object.put("bodies", array);
		return object;
	}
	

}
