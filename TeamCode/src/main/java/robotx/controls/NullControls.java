package robotx.controls;

import com.qualcomm.robotcore.hardware.Gamepad;

import robotx.libraries.DriveSystemControls;

/**
 * Created by Nicholas on 11/21/16.
 * An empty control class to be used by default so null pointer errors aren't thrown when there is no control scheme.
 */
public class NullControls extends DriveSystemControls {

	public double getLeftPower(Gamepad gamepad1, Gamepad gamepad2) {
		return 0.0;
	}
	public double getRightPower(Gamepad gamepad1, Gamepad gamepad2) {
		return 0.0;
	}

}
