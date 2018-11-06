/*package robotx.modules;


import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.*;

import robotx.libraries.*;

/**
 * Created by Nicholas on 12/6/2016.
 */
/*
@Disabled
public class TileRunnerAuton extends AutonomousSystem {

	ModernRoboticsI2cGyro gyroSensor;
	// Be sure to assign these before use.
	public DcMotor leftMotor;
	public DcMotor rightMotor;

	public TileRunnerAuton(OpMode op) {
		super(op);
	}

	public void init() {
		gyroSensor = (ModernRoboticsI2cGyro)opMode.hardwareMap.gyroSensor.get("gyroSensor");
	}

	// Return the current heading angle of the robot.
	// This should not loop around at 360, and values should increase past 360.
	public int getHeadingAngle() {
		return gyroSensor.getIntegratedZValue();
	}

	// Return the current distance the left side has traveled, in encoder ticks.
	public int getLeftTicks() {
		return leftMotor.getCurrentPosition();
	}
	// Return the current distance the right side has traveled, in encoder ticks.
	public int getRightTicks() {
		return rightMotor.getCurrentPosition();
	}

	// Input encoder ticks and return centimeters.
	public double ticksToCentimeters(int encoderTicks) {
		return encoderTicks * ((42.25/3600.0)*2.54);
	}

	public void calibrateGyro() {
		gyroSensor.calibrate();
	}

}
*/