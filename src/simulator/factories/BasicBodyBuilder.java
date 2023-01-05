package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;

public class BasicBodyBuilder extends Builder<Body>{
	
	public BasicBodyBuilder() {
		super.typeTag = "basic";
		super.desc = "Default body";
	}

	
	protected Body createTheInstance(JSONObject object) {
		
		String id = object.getString("id");
		
		JSONArray p = object.getJSONArray("p");
		Vector2D position = new Vector2D(p.getDouble(0), p.getDouble(1));
		
		JSONArray v = object.getJSONArray("v");
		Vector2D velocity = new Vector2D(v.getDouble(0), v.getDouble(1));
		
		double mass = object.getDouble("m");
		
		return new Body(id, velocity, position, mass);
		
	}
	
	protected JSONObject createData() {
		JSONObject data = new JSONObject();
		data.put("id", "the identifier");
		data.put("m", "the mass");
		data.put("p", "the position");
		data.put("v", "the velocity");
		data.put("f", "the force");
		return data;
	}

}
