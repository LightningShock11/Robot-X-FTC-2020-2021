package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import robotx.libraries.OmniDriveSystem;

/**
 * Created by KD on 11/25/2017.
 */

public class MechanumDrive extends OmniDriveSystem {
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;

    public MechanumDrive(OpMode op) {
        super(op);
    }

    public void init(){
        frontLeft = opMode.hardwareMap.dcMotor.get("frontLeft");
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight = opMode.hardwareMap.dcMotor.get("frontRight");
        backLeft = opMode.hardwareMap.dcMotor.get("backLeft");
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight = opMode.hardwareMap.dcMotor.get("backRight");
    }

    public void updateMotors(){
        opMode.telemetry.addData("frontRight", getYPower() - getXPower() + getRotationPower());
        frontRight.setPower(getYPower() - getXPower() + getRotationPower());
        opMode.telemetry.addData("frontLeft", getYPower() + getXPower() - getRotationPower());
        frontLeft.setPower(getYPower() + getXPower() - getRotationPower());
        opMode.telemetry.addData("backRight", getYPower() + getXPower() + getRotationPower());
        backRight.setPower(getYPower() + getXPower() + getRotationPower());
        opMode.telemetry.addData("backLeft", getYPower() - getXPower() - getRotationPower());
        backLeft.setPower(getYPower() - getXPower() - getRotationPower());
    }
}
