package robotx.libraries;

/**
 * Created by Nicholas on 11/6/16.
 */
public class PressHandler {

	boolean lastState;
	boolean currentState;

	public PressHandler() {
		lastState = false;
		currentState = false;
	}

	// When using this class, call update(boolean) every loop.
	public void update(boolean newState) {
		// Shift states back.
		lastState = currentState;

		// Update currentState;
		currentState = newState;
	}

	public boolean onPress() {
		if (lastState == false && currentState == true) {
			return true;
		} else {
			return false;
		}
	}

	public boolean onRelease() {
		if (lastState == true && currentState == false) {
			return true;
		} else {
			return false;
		}
	}

}
