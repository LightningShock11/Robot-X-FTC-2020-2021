package robotx.libraries;

import robotx.libraries.XGamepad;

/**
 * Created by Nicholas on 11/12/16.
 */
public abstract class DriveSystemControls {

	public abstract double getLeftPower(XGamepad gamepad1, XGamepad gamepad2);
	public abstract double getRightPower(XGamepad gamepad1, XGamepad gamepad2);

}
