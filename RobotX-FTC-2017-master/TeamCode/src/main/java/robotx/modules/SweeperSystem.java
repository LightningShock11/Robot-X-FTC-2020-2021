package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.DcMotor;

import robotx.libraries.*;

/**
 * Created by Nicholas on 11/12/16.
 */
public class SweeperSystem extends XModule {

	public enum SweeperStatus {
		COLLECTING,
		EXPELLING,
		OFF
	}

	DcMotor sweeperMotor;

	SweeperStatus sweeperStatus;

	public SweeperSystem(OpMode op) {
		super(op);
	}

	public void init() {
		sweeperMotor = opMode.hardwareMap.dcMotor.get("SW_sweeperMotor");

		sweeperStatus = SweeperStatus.OFF;
	}

	// Public facing hardware methods
	public void startCollecting() {
		sweeperMotor.setPower(1.0);
		sweeperStatus = SweeperStatus.COLLECTING;
	}
	public void startExpelling() {
		sweeperMotor.setPower(-1.0);
		sweeperStatus = SweeperStatus.EXPELLING;
	}
	public void turnOff() {
		sweeperMotor.setPower(0.0);
		sweeperStatus = SweeperStatus.OFF;
	}

	public void toggleCollecting() {
		if (sweeperStatus == SweeperStatus.OFF || sweeperStatus == SweeperStatus.EXPELLING) {
			startCollecting();
		} else if (sweeperStatus == SweeperStatus.COLLECTING) {
			turnOff();
		}
	}
	public void toggleExpelling() {
		if (sweeperStatus == SweeperStatus.OFF || sweeperStatus == SweeperStatus.COLLECTING) {
			startExpelling();
		} else if (sweeperStatus == SweeperStatus.EXPELLING) {
			turnOff();
		}
	}

	// TeleOp code

	// Called only for active XModules.
	public void start() {
		turnOff();
	}

	// Called only for active XModules.
	public void loop() {
		if (xGamepad2().left_bumper.wasPressed()) {
			toggleExpelling();
		}
		if (xGamepad2().right_bumper.wasPressed()) {
			toggleCollecting();
		}

	}

	// Called only for active XModules.
	public void stop() {
		turnOff();
	}

}
