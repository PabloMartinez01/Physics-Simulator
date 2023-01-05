package simulator.view;

import simulator.model.Body;

public class StepDistances {
	private int step;
	private Body min;
	private Body max;
	
	
	
	public StepDistances(int step, Body min, Body max) {
		super();
		this.step = step;
		this.min = min;
		this.max = max;
	}
	
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	public Body getMin() {
		return min;
	}
	public void setMin(Body min) {
		this.min = min;
	}
	public Body getMax() {
		return max;
	}
	public void setMax(Body max) {
		this.max = max;
	}
	
	

}
