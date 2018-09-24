package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import robotx.libraries.OmniDriveSystem;

/**
 * Created by WB on 9/24/2018.
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
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight = opMode.hardwareMap.dcMotor.get("frontRight");
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft = opMode.hardwareMap.dcMotor.get("backLeft");
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight = opMode.hardwareMap.dcMotor.get("backRight");
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void updateMotors(){
        opMode.telemetry.addData("frontRight", getYPower() - getXPower() - getRotationPower());
        frontRight.setPower(getYPower() - getXPower() + getRotationPower());
        opMode.telemetry.addData("frontLeft", getYPower() + getXPower() + getRotationPower());
        frontLeft.setPower(getYPower() + getXPower() - getRotationPower());
        opMode.telemetry.addData("backRight", getYPower() + getXPower() - getRotationPower());
        backRight.setPower(getYPower() + getXPower() + getRotationPower());
        opMode.telemetry.addData("backLeft", getYPower() - getXPower() + getRotationPower());
        backLeft.setPower(getYPower() - getXPower() - getRotationPower());
    }
}
