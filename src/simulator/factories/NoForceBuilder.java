package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NoForce;

public class NoForceBuilder extends Builder<ForceLaws> {
	
	public NoForceBuilder() {
		super.typeTag = "nf";
		super.desc = "No force";
	}

	protected ForceLaws createTheInstance(JSONObject object) {
		return new NoForce();
	}

}
