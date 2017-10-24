package robotx.modules;

import com.google.gson.interceptors.InterceptorFactory;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import robotx.libraries.*;


/**
 * Created by Kush Dalal on 10/13/2017.
 */


public class DriftBotTest extends XModule {

    DcMotor frontRight;
    DcMotor frontLeft;
    DcMotor backRight;
    DcMotor backLeft;

    public DriftBotTest(OpMode Op) {
        super(Op);
    }

    public void init() {
        frontRight = opMode.hardwareMap.dcMotor.get("frontRight");
        frontLeft = opMode.hardwareMap.dcMotor.get("frontLeft");
        backRight = opMode.hardwareMap.dcMotor.get("backRight");
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft = opMode.hardwareMap.dcMotor.get("backLeft");
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void loop() {
        float forwardBackAxis = xGamepad1().left_stick_y; //Forward and backwards axis
        float leftRightAxis = xGamepad1().left_stick_x; //Turning axis

        //Front side
        frontRight.setPower(-forwardBackAxis); //Move front right wheel forward or back
        frontLeft.setPower(-forwardBackAxis); //Move Front Left wheel forward or back

        //Back side
        backLeft.setPower(leftRightAxis); //rotate back left wheel right or left to turn
        backRight.setPower(leftRightAxis); //rotate back right wheel right or left to turn

    }
    public void stop() {
        //stop dat robot
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }
}