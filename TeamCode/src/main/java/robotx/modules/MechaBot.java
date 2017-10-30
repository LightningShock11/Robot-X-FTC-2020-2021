package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import robotx.libraries.XModule;


/**
 * Created by Ben Sabo on 10/25/2017.
 */

public class MechaBot extends XModule {

    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;

    public MechaBot(OpMode op) {super(op);}

    public void init(){
        frontLeft = opMode.hardwareMap.dcMotor.get("frontLeft");
        frontRight = opMode.hardwareMap.dcMotor.get("frontRight");
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft = opMode.hardwareMap.dcMotor.get("backLeft");
        backRight = opMode.hardwareMap.dcMotor.get("backRight");
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void loop(){
        float forwardBackAxis = xGamepad1().left_stick_y;
        float leftRightAxis = xGamepad1().left_stick_x;
        float spinRight = xGamepad1().right_trigger;
        float spinLeft = xGamepad1().left_trigger;

        frontRight.setPower(forwardBackAxis);
        frontLeft.setPower(forwardBackAxis);
        backLeft.setPower(forwardBackAxis);
        backRight.setPower(forwardBackAxis);

        frontLeft.setPower(leftRightAxis);
        backLeft.setPower(-leftRightAxis);
        frontRight.setPower(-leftRightAxis);
        backRight.setPower(leftRightAxis);

        frontLeft.setPower(-spinLeft);
        backLeft.setPower(-spinLeft);
        frontRight.setPower(spinLeft);
        frontLeft.setPower(spinLeft);

        frontLeft.setPower(spinRight);
        backLeft.setPower(spinRight);
        frontRight.setPower(-spinRight);
        backRight.setPower(-spinRight);
    }
    public void stop(){
        frontLeft.setPower(0.0);
        frontRight.setPower(0.0);
        frontRight.setPower(0.0);
        frontLeft.setPower(0.0);
    }
}
