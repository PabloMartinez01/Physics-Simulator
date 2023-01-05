package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws> {
	
	public MovingTowardsFixedPointBuilder() {
		super.typeTag = "mtfp";
		super.desc = "Moving towards a fixed point";
	}

	
	protected ForceLaws createTheInstance(JSONObject object) {
		double g = object.has("g") ? Double.parseDouble(object.getString("g")): 9.81;
		
		Vector2D vector = new Vector2D();
		if (object.has("c")) {
			JSONArray c = new JSONArray(object.getString("c"));
			vector = new Vector2D(c.getDouble(0), c.getDouble(1));
		}
		
		return new MovingTowardsFixedPoint(vector, g);
	}
	
	protected JSONObject createData() {
		JSONObject data = new JSONObject();
		data.put("c", "the point towards which bodies move (a json list of 2 numbers, e.g., [100.0, 50.0]");
		data.put("g", "the length of the acceleration vector (a number)");
		return data;
	}

}
