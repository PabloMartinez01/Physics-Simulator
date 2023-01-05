package simulator.model;

import org.json.JSONObject;

import simulator.misc.Vector2D;

public class MassLossingBody extends Body{
	
	private double lossFactor;
	private double lossFrequency;
	private double counter;
	
	public MassLossingBody(String id, Vector2D velocity, Vector2D position, double mass, double lossFactor, double lossFrequency) {
		super(id, velocity, position, mass);
		this.lossFactor = lossFactor;
		this.lossFrequency = lossFrequency;
		counter = 0.0;
	}
	
	
	public void move(double t) {
		super.move(t);
		counter += t;
		if (counter >= lossFrequency) {
			mass = mass * (1.0 - lossFactor);
			counter = 0.0;
		}
	}
	
	public JSONObject getState() {
		JSONObject object = super.getState();
		object.put("freq", lossFrequency);
		object.put("factor", lossFactor);
		return object;
	}

}
