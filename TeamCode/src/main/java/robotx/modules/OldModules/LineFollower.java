package robotx.modules.OldModules;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.*;

import robotx.libraries.BasicDriveSystem;
import robotx.libraries.XModule;
import robotx.libraries.XLinearOpMode;
import robotx.modules.XOpticalDistanceSensor;

/**
 * Created by Nicholas on 12/14/16.
 */
public class LineFollower extends XModule {

	BasicDriveSystem driveSystem;
    OpticalDistanceSensor floorSensor;
    XOpticalDistanceSensor opticalDistanceSensor;
    //This line caused a huge issue with Android Studio, possibly fixed

	public LineFollower(OpMode op) {
		super(op);
	}
	public LineFollower(OpMode op, BasicDriveSystem driveSystem) {
		super(op);
		this.driveSystem = driveSystem;
	}

	public void init() {
		floorSensor = opMode.hardwareMap.opticalDistanceSensor.get("BP_floorSensor");
        floorSensor.enableLed(true);

		opticalDistanceSensor = new XOpticalDistanceSensor(opMode, "BP_distanceSensor");
	}

	// Public facing hardware methods

	// Only call these in a LinearOpMode.
	public void followFromRightAuton() {
		while(opticalDistanceSensor.distanceInCentimeters() >= 5.9 && !((XLinearOpMode) opMode).stopping()) {
			opMode.telemetry.addData("Floor Sensor Light", floorSensor.getLightDetected());
			opMode.telemetry.update();
			continueFollowingFromRight();
		}
		driveSystem.setBothPower(0.0);
	}
	public void followFromLeftAuton() {
		while(opticalDistanceSensor.distanceInCentimeters() >= 5.9 && !((XLinearOpMode) opMode).stopping()) {
			opMode.telemetry.addData("Floor Sensor Light", floorSensor.getLightDetected());
			opMode.telemetry.update();
			continueFollowingFromLeft();
		}
		driveSystem.setBothPower(0.0);
	}

	// May be called in TeleOp.
	public void continueFollowingFromRight() {
		driveSystem.setRightPower(getSameSidePower());
		driveSystem.setLeftPower(getOppositeSidePower());
	}
	public void continueFollowingFromLeft() {
		driveSystem.setRightPower(getOppositeSidePower());
		driveSystem.setLeftPower(getSameSidePower());
	}
	private double getSameSidePower() { // The power for the right side when following from the right, or left from left.
		double lightValue = floorSensor.getLightDetected();

		// Cap greenValue.
		if (lightValue < 0.0) {
            lightValue = 0.0;
		}
		if (lightValue > 0.60) {
            lightValue = 0.60;
		}

		// Proportional line follower
		double minPowerValue = 0.0; // Minimum power value for either side.
		double maxPowerValueDifference = 0.5; // Maximum difference in power between right and left sides.
		if (opMode instanceof LinearOpMode) { // Allow for a different value for autonomous and semi-auton.
			maxPowerValueDifference = 0.5;
		} else {
			maxPowerValueDifference = 0.5;
		}
		double targetValue = 0.23; // Target value for color sensor.

		return -( -((lightValue*maxPowerValueDifference) / (2*targetValue)) + maxPowerValueDifference + minPowerValue );
	}
	private double getOppositeSidePower() {
		double lightValue = floorSensor.getLightDetected();

		// Cap greenValue.
		if (lightValue < 0.0) {
            lightValue = 0.0;
		}
		if (lightValue > 0.60) {
            lightValue = 0.60;
		}

		// Proportional line follower
		double minPowerValue = 0.0; // Minimum power value for either side.
		double maxPowerValueDifference = 0.5; // Maximum difference in power between right and left sides.
		if (opMode instanceof LinearOpMode) { // Allow for a different value for autonomous and semi-auton.
			maxPowerValueDifference = 0.5;
		} else {
			maxPowerValueDifference = 0.5;
		}
		double targetValue = 0.23; // Target value for color sensor.

		return -( ((lightValue*maxPowerValueDifference) / (2*targetValue)) + minPowerValue );
	}


	// TeleOp code

	// Called only for active XModules.
	public void start() {

	}

	// Called only for active XModules.
	public void loop() {
		if (xGamepad1().right_trigger > 0.8) {
			driveSystem.controlsEnabled = false;
			if (opticalDistanceSensor.distanceInCentimeters() >= 5.9) {
				opMode.telemetry.addData("LineFollower", "Following from right");
				continueFollowingFromRight();
			} else {
				opMode.telemetry.addData("LineFollower", "Following from right, stopped");
				driveSystem.brakeAllMotors();
			}
		} else if (xGamepad1().left_trigger > 0.8) {
			driveSystem.controlsEnabled = false;
			if (opticalDistanceSensor.distanceInCentimeters() >= 5.9) {
				opMode.telemetry.addData("LineFollower", "Following from left");
				continueFollowingFromLeft();
			} else {
				opMode.telemetry.addData("LineFollower", "Following from left, stopped");
				driveSystem.brakeAllMotors();
			}
		} else {
			driveSystem.controlsEnabled = true;
		}
		opMode.telemetry.addData("Floor Sensor Light", floorSensor.getLightDetected());
	}

	// Called only for active XModules.
	public void stop() {

	}
}
