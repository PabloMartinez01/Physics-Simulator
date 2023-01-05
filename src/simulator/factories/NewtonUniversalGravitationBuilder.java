package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws> {
	
	public NewtonUniversalGravitationBuilder() {
		super.typeTag = "nlug";
		super.desc = "Newton's law of universal gravitation";
	}

	
	protected ForceLaws createTheInstance(JSONObject object) {
		double g;
		try {
			g = object.has("G") ? Double.parseDouble(object.getString("G")): 6.67E-11;
		}
		catch(Exception e) {
			g = object.has("G") ? (object.getDouble("G")): 6.67E-11;
		}
		return new NewtonUniversalGravitation(g);
	}
	
	protected JSONObject createData() {
		JSONObject data = new JSONObject();
		data.put("G", "the gravitational constant (a number)");
		return data;
	}

}
