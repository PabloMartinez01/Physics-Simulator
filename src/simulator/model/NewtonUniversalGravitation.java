package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws{

	protected double gravity;
	
	public NewtonUniversalGravitation(double gravity) {
		this.gravity = gravity;
	}
	
	public Vector2D force(Body a, Body b) {
		Vector2D delta = b.getPosition().minus(a.getPosition());
		double dist = delta.magnitude();
		double magnitude = (dist > 0) ? (gravity * a.getMass() * b.getMass()) / (dist * dist) : 0.0;
		return delta.direction().scale(magnitude);
	}

	public void apply(List<Body> bs) {
		for (Body a : bs) {
			for (Body b : bs) {
				if (!a.equals(b)) {
					Vector2D force = force(a, b);
					a.addForce(force);
				}
			}
		}	
	}

	
	public String toString() {
		return "Newton's Universal Gravitation with G =  " + gravity;
	}
	
	
}
