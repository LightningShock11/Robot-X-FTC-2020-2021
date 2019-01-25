package robotx.libraries;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import robotx.modules.TwoMotorDrive;

/**
 * Created by Robot-X Team 4969 on 12/6/2016.
 */
public class AutonMoveSimple extends XModule {

	// All distances are in centimeters.


	TwoMotorDrive drive;


	public AutonMoveSimple(OpMode op) {
		super(op);
	}

	public AutonMoveSimple(LinearOpMode op, TwoMotorDrive d) {
		super(op);
		drive = d;

	}

    private void sleep(long milliseconds) { //Sleep during auton method
        if (opMode instanceof LinearOpMode) {
            ((LinearOpMode) opMode).sleep(milliseconds);
        }
    }

    public void goForward(double power, int time){

        drive.leftMotor.setPower(-power);
        drive.rightMotor.setPower(-power);
        sleep(time);
        drive.leftMotor.setPower(0);
        drive.rightMotor.setPower(0);
    }
    public void goBackward(double power, int time){

        drive.leftMotor.setPower(power);
        drive.rightMotor.setPower(power);
        sleep(time);
        drive.leftMotor.setPower(0);
        drive.rightMotor.setPower(0);
    }


}
