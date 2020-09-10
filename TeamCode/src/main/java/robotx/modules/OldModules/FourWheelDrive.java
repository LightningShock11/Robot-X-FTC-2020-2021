package robotx.modules.OldModules;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.DcMotor;

import robotx.libraries.BasicDriveSystem;

/**
 * Created by Nicholas on 11/12/16.
 */
public class FourWheelDrive extends BasicDriveSystem {

	DcMotor leftFrontMotor;
	DcMotor leftBackMotor;
	DcMotor rightFrontMotor;
	DcMotor rightBackMotor;

	public FourWheelDrive(OpMode op) {
		super(op);
	}

	public void init() {
		leftFrontMotor = opMode.hardwareMap.dcMotor.get("4WD_leftFrontMotor");
		leftFrontMotor.setDirection(DcMotor.Direction.REVERSE);
		leftBackMotor = opMode.hardwareMap.dcMotor.get("4WD_leftBackMotor");
		leftBackMotor.setDirection(DcMotor.Direction.REVERSE);
		rightFrontMotor = opMode.hardwareMap.dcMotor.get("4WD_rightFrontMotor");
		rightBackMotor = opMode.hardwareMap.dcMotor.get("4WD_rightBackMotor");
	}

	public void updateMotors() {
		leftFrontMotor.setPower(getLeftPower());
		leftBackMotor.setPower(getLeftPower());
		rightFrontMotor.setPower(getRightPower());
		rightBackMotor.setPower(getRightPower());
	}

}
