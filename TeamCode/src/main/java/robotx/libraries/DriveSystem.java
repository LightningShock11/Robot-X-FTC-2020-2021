package robotx.libraries;

import com.qualcomm.robotcore.eventloop.opmode.*;

import robotx.controls.NullControls;

/**
 * Created by Nicholas on 11/12/16.
 * Represents any drive system with two separate sides.
 * Can be used for 2,4,6 wheel drive systems, but not Mechanum.
 * Handles different control schemes for 2-sided drive systems.
 */
public abstract class DriveSystem extends XModule {

	private double leftPower = 0.0;
	private double rightPower = 0.0;

	public DriveSystemControls controls;

	public boolean controlsEnabled = true;

	public DriveSystem(OpMode op) {
		super(op);
		controls = new NullControls();
	}

	// To be implemented by subclasses.
	// Takes leftPower and rightPower and assigns them to the motors.
	public abstract void updateMotors();

	// Public facing hardware methods

	public double getLeftPower() {
		return leftPower;
	}
	public void setLeftPower(double leftPower) {
		this.leftPower = leftPower;
		updateMotors();
	}
	public double getRightPower() {
		return rightPower;
	}
	public void setRightPower(double rightPower) {
		this.rightPower = rightPower;
		updateMotors();
	}

	public void setBothPower(double power) {
		setLeftPower(power);
		setRightPower(power);
	}

	public void brakeAllMotors() {
		setBothPower(0.0);
	}

	// TeleOp code

	public void start() {
		brakeAllMotors();
	}

	public void loop() {
		if (controlsEnabled) {
			setLeftPower(controls.getLeftPower(xGamepad1(), xGamepad2()));
			setRightPower(controls.getRightPower(xGamepad1(), xGamepad2()));
		}
		opMode.telemetry.addData("Left Power", leftPower);
		opMode.telemetry.addData("Right Power", rightPower);
	}

	public void stop() {
		brakeAllMotors();
	}

}
