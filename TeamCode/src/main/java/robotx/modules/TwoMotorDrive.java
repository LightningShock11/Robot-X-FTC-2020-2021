package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.DcMotor;

import robotx.libraries.BasicDriveSystem;

/**
 * Created by Nicholas on 12/5/2016.
 */
public class TwoMotorDrive extends BasicDriveSystem {

	public DcMotor leftMotor;
	public DcMotor rightMotor;

	public TwoMotorDrive(OpMode op) {
		super(op);
	}

	public void init() {
		leftMotor = opMode.hardwareMap.dcMotor.get("leftMotor");
		leftMotor.setDirection(DcMotor.Direction.REVERSE);
		rightMotor = opMode.hardwareMap.dcMotor.get("rightMotor");
	}

	public void updateMotors() {
		leftMotor.setPower(getRightPower());
		rightMotor.setPower(getLeftPower());

		leftMotor.setPower(getLeftPower());
		rightMotor.setPower(getLeftPower());

		leftMotor.setPower(getRightPower());
		rightMotor.setPower(-getRightPower());
	}
<<<<<<< HEAD
		}


=======

}
>>>>>>> ef7a1b2e8f1bd5eb2913cd9f285d533095c01203



