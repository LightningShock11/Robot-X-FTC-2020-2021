package robotx.libraries;

import com.qualcomm.robotcore.eventloop.opmode.*;

import robotx.modules.TwoMotorDrive;

/**
 * Created by Robot-X Team 4969 on 12/6/2016.
 */
public class AutonomousMovement extends XModule {

	// All distances are in centimeters.

	AutonomousSystem sensors;
	BasicDriveSystem drive;


	public AutonomousMovement(OpMode op) {
		super(op);
	}
	public AutonomousMovement(LinearOpMode op, AutonomousSystem s, BasicDriveSystem d) {
		super(op);
		sensors = s;
		drive = d;
	}

    private void sleep(long milliseconds) { //Sleep during auton method
        if (opMode instanceof LinearOpMode) {
            ((LinearOpMode) opMode).sleep(milliseconds);
        }
    }

    public void goForward(double power, int time){

        drive.setLeftPower(-power);
        drive.setRightPower(-power);
        sleep(time);
        drive.setLeftPower(0);
        drive.setRightPower(0);
    }
    public void goBackward(double power, int time){

        drive.setLeftPower(power);
        drive.setRightPower(power);
        sleep(time);
        drive.setLeftPower(0);
        drive.setRightPower(0);
    }

    public  void stopDriving (){
        drive.brakeAllMotors();
    }

	public void driveForward(double power, double distance) {
		sensors.resetNavigationSensors();
		drive.setBothPower(0.0);
		while( (sensors.changeInLeftCentimeters() < distance) && !((XLinearOpMode) opMode).stopping() ) {
			drive.setBothPower( rampAdjustment(power, distance, sensors.changeInLeftCentimeters()) );
			opMode.telemetry.addData("Progress", sensors.changeInLeftCentimeters() + "cm / " + distance + "cm");
			opMode.telemetry.update();
		}
		drive.setBothPower(0.0);
	}
	public void driveBackward(double power, double distance) {
		sensors.resetNavigationSensors();
		drive.setBothPower(0.0);
		while( (-sensors.changeInLeftCentimeters() < distance) && !((XLinearOpMode) opMode).stopping() ) {
			drive.setBothPower( -rampAdjustment(power, distance, -sensors.changeInLeftCentimeters()) );
			opMode.telemetry.addData("Progress", -sensors.changeInLeftCentimeters() + "cm / " + distance + "cm");
			opMode.telemetry.update();
		}
		drive.setBothPower(0.0);
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
		drive.setRightPower(0.4);
		drive.setLeftPower(-0.4);
		while ( (sensors.changeInHeadingAngle() < degrees) && !((XLinearOpMode) opMode).stopping() ) {
			opMode.telemetry.addData("Progress", sensors.changeInHeadingAngle() + "deg / " + degrees + "deg");
			opMode.telemetry.update();
		}
		drive.setBothPower(0.0);
	}
	public void pointTurnRight(int degrees) {
		sensors.resetNavigationSensors();
		opMode.telemetry.addData("StartAngle", sensors.getHeadingAngle() + "deg");
		drive.setRightPower(-0.4);
		drive.setLeftPower(0.4);
		while ( (-sensors.changeInHeadingAngle() < degrees) && !((XLinearOpMode) opMode).stopping() ) {
			opMode.telemetry.addData("Progress", -sensors.changeInHeadingAngle() + "deg / " + degrees + "deg");
			opMode.telemetry.update();
		}
		drive.setBothPower(0.0);
	}

}
