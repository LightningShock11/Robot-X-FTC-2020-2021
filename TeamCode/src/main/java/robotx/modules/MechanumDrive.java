package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import robotx.libraries.OmniDriveSystem;

/**
 * Created by KD on 11/25/2017.
 */

public class MechanumDrive extends OmniDriveSystem {
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;

    public MechanumDrive(OpMode op) {
        super(op);
    }

    public void init(){
        frontLeft = opMode.hardwareMap.dcMotor.get("frontLeft");
        frontRight = opMode.hardwareMap.dcMotor.get("frontRight");
        backLeft = opMode.hardwareMap.dcMotor.get("backLeft");
        backRight = opMode.hardwareMap.dcMotor.get("backRight");
    }

    public void updateMotors(){
        frontLeft.setPower(getYPower() + getXPower() + getRotationPower());
        frontRight.setPower(getYPower() - getXPower() - getRotationPower());
        backRight.setPower(getYPower() - getXPower() + getRotationPower());
        backLeft.setPower(getYPower() + getXPower() - getRotationPower());
    }
}
