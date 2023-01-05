package simulator.control;

import org.json.JSONObject;

public class NotEqualStatesException extends Exception {
	private static final long serialVersionUID = -1221803898228865349L;

	public NotEqualStatesException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NotEqualStatesException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public NotEqualStatesException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public NotEqualStatesException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public NotEqualStatesException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public NotEqualStatesException(JSONObject expState, JSONObject currState, int i) {
		super("Error in step: " + i + "\n \n" + "Expected State: " + expState + "\n" + "Current State: " + currState);
	}

}
