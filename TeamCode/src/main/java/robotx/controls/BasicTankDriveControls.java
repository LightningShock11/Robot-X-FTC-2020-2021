package robotx.controls;

import com.qualcomm.robotcore.hardware.Gamepad;

import robotx.libraries.DriveSystemControls;

/**
 * Created by Nicholas on 11/12/16.
 */
public class BasicTankDriveControls extends DriveSystemControls {

	public double getLeftPower(Gamepad gamepad1, Gamepad gamepad2) {
		double leftPower = -1.0 * gamepad1.left_stick_y; // Invert it because -1.0 is up on the gamepads.

		return leftPower;
	}
	public double getRightPower(Gamepad gamepad1, Gamepad gamepad2) {
		double rightPower = -1.0 * gamepad1.right_stick_y; // Invert it because -1.0 is up on the gamepads.

		return rightPower;
	}

}
