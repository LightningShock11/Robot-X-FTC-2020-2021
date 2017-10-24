package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import robotx.libraries.*;

/**
 * Created by Nicholas on 11/21/16.
 */
public class TwoWheelLauncher extends XModule {

	public enum LauncherState {
		LAUNCHING,
		CLOSED,
		REVERSING
	}

	DcMotor wheelMotor;
	DcMotor upperSweeper;
	Servo plateServo;

	LauncherState launcherState;

	int numberLaunched = 0;

	public TwoWheelLauncher(OpMode op) {
		super(op);
	}

	public void init() {
		wheelMotor = opMode.hardwareMap.dcMotor.get("2WL_wheelMotor");
		wheelMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

		upperSweeper = opMode.hardwareMap.dcMotor.get("2WL_upperSweeper");

		plateServo = opMode.hardwareMap.servo.get("2WL_plateServo");
		plateServo.scaleRange(0.02, 0.98);

		launcherState = LauncherState.CLOSED;
	}

	// Public facing hardware methods

	public void startLaunching() {
		wheelMotor.setPower(-1.0);
		upperSweeper.setPower(1.0);
		plateServo.setPosition(0.71);
		
		launcherState = LauncherState.LAUNCHING;
	}
	public void startReversing() {
		wheelMotor.setPower(0.3);
		upperSweeper.setPower(-1.0);
		plateServo.setPosition(0.71);

		launcherState = LauncherState.REVERSING;

		slowedDown = true;
	}
	public void close() {
		wheelMotor.setPower(0.0);
		upperSweeper.setPower(0.0);
		plateServo.setPosition(1.0);

		launcherState = LauncherState.CLOSED;

		slowedDown = true;
	}

	public void toggleLaunching() {
		if (launcherState == LauncherState.CLOSED || launcherState == LauncherState.REVERSING) {
			startLaunching();
		} else if (launcherState == LauncherState.LAUNCHING) {
			close();
		}
	}
	public void toggleReversing() {
		if (launcherState == LauncherState.CLOSED || launcherState == LauncherState.LAUNCHING) {
			startReversing();
		} else if (launcherState == LauncherState.LAUNCHING) {
			close();
		}
	}

	public int getNumberLaunched() {
		return numberLaunched;
	}
	public void resetNumberLaunched() {
		numberLaunched = 0;
	}

	// TeleOp code

	int lastEncoderValue;
	long lastMillis;

	double encoderTicksPerSecond;

	boolean slowedDown = true; // True if the launcher is currently slowed down from launching one ball, false otherwise.

	// Called only for active XModules.
	public void start() {
		close();
		lastEncoderValue = wheelMotor.getCurrentPosition();
		lastMillis = System.currentTimeMillis();
	}

	// Called only for active XModules.
	public void loop() {
		if (xGamepad2().y.wasPressed()) {
			toggleLaunching();
		}
		if (xGamepad2().a.wasPressed()) {
			toggleReversing();
		}

		// Only recalculate encoderTicksPerSecond every 100ms.
		if ( (System.currentTimeMillis() - lastMillis)>100 ) {
			long changeInMillis = System.currentTimeMillis() - lastMillis;
			int changeInEncoder = wheelMotor.getCurrentPosition() - lastEncoderValue;

			encoderTicksPerSecond = changeInEncoder / (changeInMillis / 1000.0);

			lastEncoderValue = wheelMotor.getCurrentPosition();
			lastMillis = System.currentTimeMillis();
		}

		// Check if a ball was launched.
		if (encoderTicksPerSecond<800 && launcherState == LauncherState.LAUNCHING && !slowedDown) {
			slowedDown = true;
			numberLaunched++;
		}
		if (encoderTicksPerSecond>850) {
			slowedDown = false;
		}

		opMode.telemetry.addData("encoderTicksPerSecond", encoderTicksPerSecond);
		opMode.telemetry.addData("numberLaunched", numberLaunched);
	}

	// Called only for active XModules.
	public void stop() {
		close();
	}

}
