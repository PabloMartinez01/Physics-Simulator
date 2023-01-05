package simulator.factories;

import org.json.JSONObject;

import simulator.control.MassEqualStates;
import simulator.control.StateComparator;

public class MassEqualStatesBuilder extends Builder<StateComparator>{
	
	public MassEqualStatesBuilder() {
		super.typeTag = "masseq";
		super.desc = "Mass equal states";
	}

	
	protected StateComparator createTheInstance(JSONObject object) {
		return new MassEqualStates();
	}

}
