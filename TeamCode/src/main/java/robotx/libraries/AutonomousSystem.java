package robotx.libraries;

import com.qualcomm.robotcore.eventloop.opmode.*;

import robotx.controls.NullControls;

/**
 * Created by Nicholas on 12/4/16.
 */
public abstract class AutonomousSystem extends XModule {

	private double previousHeadingAngle = 0;
	private double previousLeftCentimeters = 0.0;
	private double previousRightCentimeters = 0.0;

	public AutonomousSystem(OpMode op) {
		super(op);
	}

	// To be implemented by subclasses

	// Return the current heading angle of the robot.
	// This should not loop around at 360, and values should increase past 360.
	// Use the rawZ() method of GyroSensor for this.
	public abstract int getHeadingAngle();

	// Return the current distance the left side has traveled, in encoder ticks.
	public abstract int getLeftTicks();
	// Return the current distance the right side has traveled, in encoder ticks.
	public abstract int getRightTicks();

	// Input encoder ticks and return centimeters.
	public abstract double ticksToCentimeters(int encoderTicks);

	// Public facing hardware methods

	// The total leftDistance value, unadjusted.
	public double getLeftCentimeters() {
		return ticksToCentimeters(getLeftTicks());
	}
	// The total rightDistance value, unadjusted.
	public double getRightCentimeters() {
		return ticksToCentimeters(getRightTicks());
	}

	// Return the change in heading angle since the last reset.
	public double changeInHeadingAngle() {
		return getHeadingAngle() - previousHeadingAngle;
	}

	// Return the change in left centimeters since the last reset.
	public double changeInLeftCentimeters() {
		return getLeftCentimeters() - previousLeftCentimeters;
	}
	// Return the change in right centimeters since the last reset.
	public double changeInRightCentimeters() {
		return getRightCentimeters() - previousRightCentimeters;
	}

	// Reset the stored previous positions for distance and heading,
	// so that the change in them is measured from this point on.
	public void resetNavigationSensors() {
		previousHeadingAngle = getHeadingAngle();
		previousLeftCentimeters = getLeftCentimeters();
		previousRightCentimeters = getRightCentimeters();
	}



}
