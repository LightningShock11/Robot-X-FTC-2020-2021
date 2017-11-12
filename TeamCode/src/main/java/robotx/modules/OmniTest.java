package robotx.modules;

import com.google.gson.interceptors.InterceptorFactory;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import robotx.libraries.*;


/**
 * Created by Ben Sabo on 9/22/2017.
 */


public class OmniTest extends XModule {

    static double ROOT2 = Math.sqrt(2.0);

    DcMotor frontRight;
    DcMotor frontLeft;
    DcMotor backRight;
    DcMotor backLeft;

    public OmniTest(OpMode Op) {
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
        double forwardBackAxis = -xGamepad1().left_stick_y;
        double leftRightAxis = xGamepad1().left_stick_x;
        double spinAxis = xGamepad1().right_stick_x;

        // Info on rotation: https://math.stackexchange.com/questions/383321/rotating-x-y-points-45-degrees
        double rotatedXAxis = (leftRightAxis-forwardBackAxis) / ROOT2;
        double rotatedYAxis = (forwardBackAxis+leftRightAxis) / ROOT2;

        backLeft.setPower(-rotatedYAxis-spinAxis);
        frontRight.setPower(rotatedYAxis-spinAxis);

        frontLeft.setPower(rotatedXAxis+spinAxis);
        backRight.setPower(-rotatedXAxis+spinAxis);

    }
    public void stop() {
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }
}
//System.out.println("William has been here")
