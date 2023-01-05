package simulator.factories;

import org.json.JSONObject;

import simulator.control.EpsilonEqualStates;
import simulator.control.StateComparator;

public class EpsilonEqualStatesBuilder extends Builder<StateComparator> {
	
	public EpsilonEqualStatesBuilder() {
		super.typeTag = "epseq";
		super.desc = "Epsilon Equal States";
	}

	@Override
	protected StateComparator createTheInstance(JSONObject object) {
		double eps = object.has("eps") ? object.getDouble("eps") : 0.0;
		return new EpsilonEqualStates(eps);
	}
	
	protected JSONObject createData() {
		JSONObject data = new JSONObject();
		data.put("eps", "epsilon error");
		return data;
	}

}
