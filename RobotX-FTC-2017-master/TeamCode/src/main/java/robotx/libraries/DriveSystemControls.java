package robotx.libraries;

import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Created by Nicholas on 11/12/16.
 */
public abstract class DriveSystemControls {

	public abstract double getLeftPower(Gamepad gamepad1, Gamepad gamepad2);
	public abstract double getRightPower(Gamepad gamepad1, Gamepad gamepad2);

}
