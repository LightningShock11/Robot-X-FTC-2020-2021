package robotx.modules;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import robotx.libraries.AutonomousSystem;

/**
 * Created by Kush Dalal on 10/31/2018
 */
public class TwoWheelAutonIMU extends AutonomousSystem {
	int gyr;
	BNO055IMU gyroSensor;
	Orientation lastAngles = new Orientation();
	double globalAngle;


	// Be sure to assign these before use.
	public DcMotor leftMotor;
	public DcMotor rightMotor;


	public TwoWheelAutonIMU(OpMode op) {
		super(op);
	}

	public void init() {
		gyroSensor = (BNO055IMU) opMode.hardwareMap.gyroSensor.get("gyroSensor");
		resetAngle();
	}

	// Return the current heading angle of the robot.
	// This should not loop around at 360, and values should increase past 360.

	public void resetAngle() {
		lastAngles = gyroSensor.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

		globalAngle = 0;
	}

	public double getHeadingAngle() {

		Orientation angles = gyroSensor.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
		double deltaAngle = angles.firstAngle - lastAngles.firstAngle;

		if (deltaAngle < -180)
			deltaAngle += 360;
		else if (deltaAngle > 180)
			deltaAngle -= 360;

		globalAngle += deltaAngle;

		lastAngles = angles;

		return globalAngle;

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
		return encoderTicks * ((42.25 / 3600.0) * 2.54);
	}


}
