package robotx.libraries;

import com.qualcomm.robotcore.eventloop.opmode.*;

/**
 * Created by Robot-X Team Member on 11/27/2017.
 */

public class OmniAutonomousMovement extends XModule {

    // All distances are in centimeters.

    OmniAutonomousSystem sensors;
    OmniDriveSystem drive;

    public OmniAutonomousMovement(OpMode op) {
        super(op);
    }
    public OmniAutonomousMovement(LinearOpMode op, OmniAutonomousSystem s, OmniDriveSystem d) {
        super(op);
        sensors = s;
        drive = d;
    }

    public void driveForward(double power, double distance) {
        sensors.resetNavigationSensors();
        drive.setAllPower(0.0);
        while( (sensors.changeInYCentimeters() < distance) && !((XLinearOpMode) opMode).stopping() ) {
            //drive.setYPower( rampAdjustment(power, distance, sensors.changeInYCentimeters()) );
            drive.setYPower( power );
            opMode.telemetry.addData("Progress", sensors.changeInYCentimeters() + "cm / " + distance + "cm");
            opMode.telemetry.update();
        }
        drive.setAllPower(0.0);
    }
    public void driveBackward(double power, double distance) {
        sensors.resetNavigationSensors();
        drive.setAllPower(0.0);
        while( (-sensors.changeInYCentimeters() < distance) && !((XLinearOpMode) opMode).stopping() ) {
            //drive.setYPower( -rampAdjustment(power, distance, -sensors.changeInYCentimeters()) );
            drive.setYPower( -power );
            opMode.telemetry.addData("Progress", -sensors.changeInYCentimeters() + "cm / " + distance + "cm");
            opMode.telemetry.update();
        }
        drive.setAllPower(0.0);
    }
    public void driveRight(double power, double distance) {
        sensors.resetNavigationSensors();
        drive.setAllPower(0.0);
        while( (sensors.changeInXCentimeters() < distance) && !((XLinearOpMode) opMode).stopping() ) {
            drive.setXPower( rampAdjustment(power, distance, sensors.changeInXCentimeters()) );
            opMode.telemetry.addData("Progress", sensors.changeInXCentimeters() + "cm / " + distance + "cm");
            opMode.telemetry.update();
        }
        drive.setAllPower(0.0);
    }
    public void driveLeft(double power, double distance) {
        sensors.resetNavigationSensors();
        drive.setAllPower(0.0);
        while( (-sensors.changeInXCentimeters() < distance) && !((XLinearOpMode) opMode).stopping() ) {
            drive.setXPower( -rampAdjustment(power, distance, -sensors.changeInXCentimeters()) );
            opMode.telemetry.addData("Progress", -sensors.changeInXCentimeters() + "cm / " + distance + "cm");
            opMode.telemetry.update();
        }
        drive.setAllPower(0.0);
    }

    private double rampAdjustment(double maxPower, double distance, double currentDist) {
        double rampUpCoeff = 0.0;
        double normalCoeff = 1.0;
        double rampDownCoeff = 0.0;

        // If in the first 30cm of a movement, ramp up.
        rampUpCoeff = currentDist/20.0;

        // If in the last 30cm of a movement, ramp down.
        rampDownCoeff = (distance-currentDist)/40.0;

        double coeff = 0.0;
        // Choose minimum of 3 values.
        if (rampDownCoeff <= rampUpCoeff && rampDownCoeff <= normalCoeff) { // If rampDown is the smallest...
            coeff = rampDownCoeff;
        } else if (rampUpCoeff <= rampDownCoeff && rampUpCoeff <= normalCoeff) { // If rampUp is the smallest...
            coeff = rampUpCoeff;
        } else { // normalCoeff in the smallest.
            coeff = normalCoeff;
        }

        //Clamp the coeff to a normal range.
        if (coeff > 1.0) {
            coeff = 1.0;
        }
        if (coeff < 0.0) {
            coeff = 0.0;
        }

        double a = 0.1; // a is the minimum power
        double outputPower = maxPower * (coeff - a*coeff + a);
        return outputPower; // Range output power
    }

    public void pointTurnLeft(int degrees) {
        sensors.resetNavigationSensors();
        opMode.telemetry.addData("StartAngle", -sensors.getHeadingAngle() + "deg");
        opMode.telemetry.update();
        drive.setRotationPower(-0.3);
        while ( (sensors.changeInHeadingAngle() < degrees) && !((XLinearOpMode) opMode).stopping() ) {
            opMode.telemetry.addData("Progress", sensors.changeInHeadingAngle() + "deg / " + degrees + "deg");
            opMode.telemetry.update();
        }
        drive.brakeAllMotors();
    }
    public void pointTurnRight(int degrees) {
        sensors.resetNavigationSensors();
        opMode.telemetry.addData("StartAngle", sensors.getHeadingAngle() + "deg");
        drive.setRotationPower(0.3);
        while ( (-sensors.changeInHeadingAngle() < degrees) && !((XLinearOpMode) opMode).stopping() ) {
            opMode.telemetry.addData("Progress", -sensors.changeInHeadingAngle() + "deg / " + degrees + "deg");
            opMode.telemetry.update();
        }
        drive.brakeAllMotors();
    }

}
