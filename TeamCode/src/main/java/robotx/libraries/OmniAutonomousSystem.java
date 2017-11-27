package robotx.libraries;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by Robot-X Team Member on 11/27/2017.
 */

public abstract class OmniAutonomousSystem extends XModule {

    private int previousHeadingAngle = 0;
    private double previousXCentimeters = 0.0;
    private double previousYCentimeters = 0.0;

    public OmniAutonomousSystem(OpMode op) {
        super(op);
    }

    // To be implemented by subclasses

    // Return the current heading angle of the robot.
    // This should not loop around at 360, and values should increase past 360.
    // Use the rawZ() method of GyroSensor for this.
    public abstract int getHeadingAngle();

    // Return the current distance the robot has traveled along the x-axis, in encoder ticks.
    public abstract int getXTicks();
    // Return the current distance the robot has traveled along the y-axis, in encoder ticks.
    public abstract int getYTicks();

    // Input encoder ticks and return centimeters.
    public abstract double ticksToCentimeters(int encoderTicks);

    // Public facing hardware methods

    // The total xDistance value, unadjusted.
    public double getXCentimeters() {
        return ticksToCentimeters(getXTicks());
    }
    // The total yDistance value, unadjusted.
    public double getYCentimeters() {
        return ticksToCentimeters(getYTicks());
    }

    // Return the change in heading angle since the last reset.
    public int changeInHeadingAngle() {
        return getHeadingAngle() - previousHeadingAngle;
    }

    // Return the change in x centimeters since the last reset.
    public double changeInXCentimeters() {
        return getXCentimeters() - previousXCentimeters;
    }
    // Return the change in y centimeters since the last reset.
    public double changeInYCentimeters() {
        return getYCentimeters() - previousYCentimeters;
    }

    // Reset the stored previous positions for distance and heading,
    // so that the change in them is measured from this point on.
    public void resetNavigationSensors() {
        previousHeadingAngle = getHeadingAngle();
        previousXCentimeters = getXCentimeters();
        previousYCentimeters = getYCentimeters();
    }

}
