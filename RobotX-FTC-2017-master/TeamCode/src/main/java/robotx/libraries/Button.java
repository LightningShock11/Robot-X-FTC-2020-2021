package robotx.libraries;

/**
 * Created by Nicholas on 11/21/16.
 */
public class Button {

	boolean lastValue = false;
	boolean currentValue = false;

	public void update(boolean newValue) {
		lastValue = currentValue;
		currentValue = newValue;
	}

	public boolean isDown() {
		return currentValue;
	}
	public boolean isUp() {
		return !currentValue;
	}

	public boolean wasPressed() {
		if (lastValue == false && currentValue == true) {
			return true;
		} else {
			return false;
		}
	}
	public boolean wasReleased() {
		if (lastValue == true && currentValue == false) {
			return true;
		} else {
			return false;
		}
	}

}
