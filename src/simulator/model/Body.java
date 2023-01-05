package simulator.model;	

import org.json.JSONObject;
import simulator.misc.Vector2D;

public class Body {
	
	protected String id;
	protected Vector2D velocity;
	protected Vector2D force;
	protected Vector2D position;
	protected double mass;
	
	
	public Body(String id, Vector2D velocity, Vector2D position, double mass) {
		this.id = id;
		this.velocity = velocity;
		this.position = position;
		this.mass = mass;
		resetForce();
	}
	
	public String getId() {
		return id;
	}
	
	public double getMass() {
		return mass;
	}
	
	public Vector2D getPosition() {
		return position;
	}
	
	public Vector2D getVelocity() {
		return velocity;
	}
	
	public Vector2D getForce() {
		return force;
	}
	
	
	public void addForce(Vector2D f) {
		force = force.plus(f);
	}
	
	public void resetForce() {
		force = new Vector2D();
	}
	
	public void move(double t) {	
		Vector2D acceleration = (mass == 0.0) ? new Vector2D() : force.scale((1.0/mass));
		position = position.plus(velocity.scale(t).plus(acceleration.scale(0.5*t*t)));
		velocity = velocity.plus(acceleration.scale(t));
	}
	
	public JSONObject getState() {
		JSONObject jObject = new JSONObject();
		jObject.put("id", id);
		jObject.put("m", mass);
		jObject.put("p", position.asJSONArray());
		jObject.put("v", velocity.asJSONArray());
		jObject.put("f", force.asJSONArray());
		return jObject;
	}
	
	public String toString() {
		return getState().toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Body other = (Body) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	

	
	
	
}
