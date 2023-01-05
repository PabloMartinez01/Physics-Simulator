package simulator.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.PhysicsSimulator;
import simulator.model.SimulatorObserver;

public class Controller {
	
	private PhysicsSimulator simulator;
	private Factory<Body> bodiesFactory;
	private Factory<ForceLaws> forceLawsFactory;
	
	public Controller(PhysicsSimulator simulator, Factory<Body> bodiesFactory, Factory<ForceLaws> forceLawsFactory) {
		this.simulator = simulator;
		this.bodiesFactory = bodiesFactory;
		this.forceLawsFactory = forceLawsFactory;
	}
	
	public void reset() {
		simulator.reset();
	}
	
	public void setDeltaTime(double dt) {
		simulator.setDeltaTime(dt);
	}
	
	public void addObserver(SimulatorObserver o) {
		simulator.addObserver(o);
	}
	
	public List<JSONObject> getForceLawsInfo(){
		return forceLawsFactory.getInfo();
	}
	
	public void setForceLaws(JSONObject info) {
		ForceLaws force = forceLawsFactory.createInstance(info);
		simulator.setForceLaws(force);
	}
	
	
	public void loadBodies(InputStream in) {
		JSONObject jsonInupt = new JSONObject(new JSONTokener(in));
		JSONArray bodies = jsonInupt.getJSONArray("bodies");
		
		for (int i = 0; i < bodies.length(); i++) {
			simulator.addBody(bodiesFactory.createInstance(bodies.getJSONObject(i)));
		}
		
	}
	
	public void run(int n) {
		for (int i = 0; i < n; i++) {
			simulator.Advance();
		}
	}
	
	public void run(int n, OutputStream out, InputStream expOut, StateComparator cmp) throws NotEqualStatesException {
		JSONObject expOutJO = null;
		
		if (expOut != null) expOutJO = new JSONObject(new JSONTokener(expOut));
		
		if (out == null) {
			out = new OutputStream() {
				public void write(int b) throws IOException {}
			};
		}
		
		PrintStream p = new PrintStream(out);
		p.println("{");
		p.println("\"states\": [");	
		
		JSONObject currState = null;
		JSONObject expState = null;
		
		
		for (int i = 0; i <= n; i++) {
			currState = simulator.getState();
			p.println(currState);
			
			if (i != n) p.print(",");
			
			if (expOut != null) {
				expState = expOutJO.getJSONArray("states").getJSONObject(i);
				if (!cmp.equal(expState, currState)) throw new NotEqualStatesException(expState, currState, i);
			}
			
			simulator.Advance();
		}
		
		p.println("]");
		p.println("}");
		
	}

}
