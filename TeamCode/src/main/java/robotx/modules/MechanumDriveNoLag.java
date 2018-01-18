package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import robotx.libraries.XModule;

/**
 * Created by KD on 11/25/2017.
 */

public class MechanumDriveNoLag extends XModule{
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;

    public MechanumDriveNoLag(OpMode op) {
        super(op);
    }

    public void init(){
        frontRight = opMode.hardwareMap.dcMotor.get("frontRight");
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft = opMode.hardwareMap.dcMotor.get("frontLeft");
        backRight = opMode.hardwareMap.dcMotor.get("backRight");
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft = opMode.hardwareMap.dcMotor.get("backLeft");

    }
    public void loop() {
        float forwardBackAxis = xGamepad1().left_stick_y; //Forward and backwards axis
        float leftRightAxis = xGamepad1().left_stick_x; //Strafing axis
        float spinAxis = -xGamepad1().right_stick_x; //spinning axis

        //will make these to methods later
        //drive forward/backward
       /* frontLeft.setPower(forwardBackAxis - leftRightAxis - spinAxis);
        frontRight.setPower(forwardBackAxis);
        backLeft.setPower(forwardBackAxis);
        backRight.setPower(forwardBackAxis);

        drive strafe
        frontRight.setPower(-leftRightAxis);
        backRight.setPower(leftRightAxis);
        backLeft.setPower(-leftRightAxis);
        frontLeft.setPower(leftRightAxis);

        //spin!
        frontLeft.setPower(spinAxis);
        frontRight.setPower(spinAxis);
        backRight.setPower(-spinAxis);
        backLeft.setPower(-spinAxis);*/
        opMode.telemetry.addData("frontRight", forwardBackAxis - leftRightAxis - spinAxis);
        frontRight.setPower(forwardBackAxis - leftRightAxis + spinAxis);

        opMode.telemetry.addData("frontLeft", forwardBackAxis + leftRightAxis + spinAxis);
        frontLeft.setPower(forwardBackAxis + leftRightAxis - spinAxis);

        opMode.telemetry.addData("backRight", forwardBackAxis + leftRightAxis - spinAxis);
        backRight.setPower(forwardBackAxis + leftRightAxis + spinAxis);

        opMode.telemetry.addData("backLeft", forwardBackAxis - leftRightAxis + spinAxis);
        backLeft.setPower(forwardBackAxis - leftRightAxis - spinAxis);

    }



}



