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

        float FRpower = forwardBackAxis - leftRightAxis + spinAxis;
        float FLpower = forwardBackAxis + leftRightAxis - spinAxis;
        float BRpower = forwardBackAxis + leftRightAxis + spinAxis;
        float BLpower = forwardBackAxis - leftRightAxis - spinAxis;

        opMode.telemetry.addData("frontRight", FRpower);
        frontRight.setPower(FRpower);

        opMode.telemetry.addData("frontLeft", FLpower);
        frontLeft.setPower(FLpower);

        opMode.telemetry.addData("backRight", BRpower);
        backRight.setPower(BRpower);

        if(xGamepad1().left_bumper.isDown() && xGamepad1().right_bumper.isDown()){
            FLpower /= 5;
            FRpower /= 5;
            BRpower /= 5;
            BLpower /= 5;
        }

        opMode.telemetry.addData("backLeft", BLpower);
        backLeft.setPower(BLpower);

        if(xGamepad1().left_bumper.isDown() || xGamepad1().right_bumper.isDown()){
            FLpower /= 2;
            FRpower /= 2;
            BRpower /= 2;
            BLpower /= 2;
        }
    }



}



