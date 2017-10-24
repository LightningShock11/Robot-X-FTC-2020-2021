package robotx.libraries;

import com.qualcomm.robotcore.eventloop.opmode.*;

/**
 * Created by Nicholas on 11/3/16.
 */
public abstract class XModule {

	public OpMode opMode;

	public XModule(OpMode op) {
		opMode = op;
	}

	public XGamepad xGamepad1() {
		if (opMode instanceof XOpMode) {
			return ((XOpMode) opMode).xGamepad1;
		} else {
			return new XGamepad(opMode.gamepad1);
		}
	}
	public XGamepad xGamepad2() {
		if (opMode instanceof XOpMode) {
			return ((XOpMode) opMode).xGamepad2;
		} else {
			return new XGamepad(opMode.gamepad2);
		}
	}

	// Optional methods to override.  Called when parent opMode's methods are called.
	// Called for active and inactive XModules.
	public void init() {

	}

	// Called for active and inactive XModules.
	public void init_loop() {

	}

	// Called only for active XModules.
	public void start() {

	}

	// Called only for active XModules.
	public void loop() {

	}

	// Called only for active XModules.
	public void stop() {

	}

}
