package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;

public class EpsilonEqualStates implements StateComparator{

	protected double eps;
	
	public EpsilonEqualStates(double eps) {
		this.eps = eps;
	}
	
	public boolean compare(String key, JSONObject body1, JSONObject body2) {
		Vector2D v1 = new Vector2D(body1.getJSONArray(key).getDouble(0), body1.getJSONArray(key).getDouble(1));
		Vector2D v2 = new Vector2D(body2.getJSONArray(key).getDouble(0), body2.getJSONArray(key).getDouble(1));
		return v1.distanceTo(v2) <= eps;
	}
	
	public boolean equal(JSONObject s1, JSONObject s2) {
		
		
		if (s1.getDouble("time") != s2.getDouble("time")) return false;
		
		JSONArray bodies1 = s1.getJSONArray("bodies"); 
		JSONArray bodies2 = s2.getJSONArray("bodies"); 
		
		if (bodies1.length() != bodies2.length()) return false;
		
		int i = 0;	
		while (i < bodies1.length()) {
			
			JSONObject body1 = bodies1.getJSONObject(i);
			JSONObject body2 = bodies2.getJSONObject(i);
			
			//comprobar id
			if(!body1.getString("id").equals(body2.getString("id"))) return false;
			
			if (Math.abs(body1.getDouble("m") - body2.getDouble("m")) > eps) return false;
			
			if (!compare("p", body1, body2) || !compare("v", body1, body2) || !compare("f", body1, body2)) return false;
				
			i++;
		}
		
		return true;
	}

}
