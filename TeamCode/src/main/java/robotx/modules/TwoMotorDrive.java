package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.DcMotor;

import robotx.libraries.DriveSystem;

/**
 * Created by Nicholas on 12/5/2016.
 */
public class TwoMotorDrive extends DriveSystem {

	public DcMotor leftMotor;
	public DcMotor rightMotor;

	public TwoMotorDrive(OpMode op) {
		super(op);
	}

	public void init() {
		leftMotor = opMode.hardwareMap.dcMotor.get("2MD_leftMotor");
		leftMotor.setDirection(DcMotor.Direction.REVERSE);
		rightMotor = opMode.hardwareMap.dcMotor.get("2MD_rightMotor");
	}

	public void updateMotors() {
		leftMotor.setPower(getLeftPower());
		rightMotor.setPower(getRightPower());
	}

}
