package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import robotx.libraries.XModule;


/**
 * Created by Kush Dalal on 10/25/2017.
 */


public class XOmniTest extends XModule {

    DcMotor frontRight;
    DcMotor frontLeft;
    DcMotor backRight;
    DcMotor backLeft;

    public XOmniTest(OpMode Op) {
        super(Op);
    }

    public void init() {
        frontRight = opMode.hardwareMap.dcMotor.get("frontRight");
        frontLeft = opMode.hardwareMap.dcMotor.get("frontLeft");
        backRight = opMode.hardwareMap.dcMotor.get("backRight");
        backLeft = opMode.hardwareMap.dcMotor.get("backLeft");

    }
    public void loop() {
        float forwardBackAxis = xGamepad1().left_stick_y;
        float leftRightAxis = xGamepad1().left_stick_x;
        float spinAxis = xGamepad1().right_stick_x;

        //drive forward or backwards
        backRight.setPower(forwardBackAxis);
        backLeft.setPower(forwardBackAxis);
        frontRight.setPower(forwardBackAxis);
        frontLeft.setPower(-forwardBackAxis);

        //drive left or right
        frontRight.setPower(-leftRightAxis);
        frontLeft.setPower(-leftRightAxis);
        backRight.setPower(-leftRightAxis);
        backLeft.setPower(leftRightAxis);

        //spin to left or right
        frontLeft.setPower(spinAxis);
        frontRight.setPower(-spinAxis);
        backLeft.setPower(spinAxis);
        backRight.setPower(spinAxis);
        //Code for using joysticks
    }
    public void stop() {
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }
}
