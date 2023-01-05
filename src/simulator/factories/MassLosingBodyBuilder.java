package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.MassLossingBody;

public class MassLosingBodyBuilder extends Builder<Body> {
	
	public MassLosingBodyBuilder() {
		super.typeTag = "mlb";
		super.desc = "Mass Losing Body";
	}

	
	protected MassLossingBody createTheInstance(JSONObject object) {
		String id = object.getString("id");
		
		JSONArray p = object.getJSONArray("p");
		Vector2D position = new Vector2D(p.getDouble(0), p.getDouble(1));
		
		JSONArray v = object.getJSONArray("v");
		Vector2D velocity = new Vector2D(v.getDouble(0), v.getDouble(1));
		
		double mass = object.getDouble("m");
		double lossFrequency = object.getDouble("freq");
		double lossFactor = object.getDouble("factor");
		
		return new MassLossingBody(id, velocity, position, mass, lossFactor, lossFrequency);
	}
	
	protected JSONObject createData() {
		JSONObject data = new JSONObject();
		data.put("id", "the identifier");
		data.put("m", "the mass");
		data.put("p", "the position");
		data.put("v", "the velocity");
		data.put("f", "the force");
		data.put("freq", "the frequency");
		data.put("factor", "the factor");
		return data;
	}

}
