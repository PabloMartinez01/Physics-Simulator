package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;

public class MassEqualStates implements StateComparator{

	
	public boolean equal(JSONObject s1, JSONObject s2) {
		
		if (s1.getDouble("time") != s2.getDouble("time")) return false;
		
		JSONArray bodies1 = s1.getJSONArray("bodies"); 
		JSONArray bodies2 = s2.getJSONArray("bodies"); 
		
		if (bodies1.length() != bodies2.length()) return false;
		
		int i = 0;
		while (i < bodies1.length()) {
			JSONObject body1 = bodies1.getJSONObject(i);
			JSONObject body2 = bodies2.getJSONObject(i);
			if (!body1.getString("id").equals(body2.getString("id")) || body1.getDouble("m") != body2.getDouble("m")) return false;	
		}
		
		return true;
	}

}
