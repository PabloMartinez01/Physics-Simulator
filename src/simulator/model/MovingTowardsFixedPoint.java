package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class MovingTowardsFixedPoint implements ForceLaws{

		private Vector2D origin;
		private double gravity;
	
		public MovingTowardsFixedPoint(Vector2D origin, double gravity) {
			this.origin = origin;
			this.gravity = gravity;
		}

		public void apply(List<Body> bs) {
			for (Body body : bs) {
				body.addForce(origin.minus(body.getPosition()).direction().scale(gravity*body.getMass()));
			}	
		}
		
		public String toString() {
			return "Moving towards " + origin + " with constant acceleration " + gravity;
		}
		
		
}
