package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import robotx.libraries.XModule;

/**
 * Created by KD on 11/25/2017.
 */

public class MechanumDriveNoLag extends XModule {

    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;

    public MechanumDriveNoLag(OpMode op) {
        super(op);
    }

    public void init() {
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

        // Combine the 3 powers to get a power for each motor with Mechanum wheels.
        float FRpower = forwardBackAxis - leftRightAxis + spinAxis;
        float FLpower = forwardBackAxis + leftRightAxis - spinAxis;
        float BRpower = forwardBackAxis + leftRightAxis + spinAxis;
        float BLpower = forwardBackAxis - leftRightAxis - spinAxis;

        // Slow down the robot if bumpers are held down.
        if (xGamepad1().left_bumper.isDown() && xGamepad1().right_bumper.isDown()){
            FLpower /= 5;
            FRpower /= 5;
            BRpower /= 5;
            BLpower /= 5;
        } else if (xGamepad1().left_bumper.isDown() || xGamepad1().right_bumper.isDown()){
            FLpower /= 2;
            FRpower /= 2;
            BRpower /= 2;
            BLpower /= 2;
        }

        // Assign the finalized powers to the motors.
        frontRight.setPower(FRpower);
        frontLeft.setPower(FLpower);
        backRight.setPower(BRpower);
        backLeft.setPower(BLpower);

        // Output the encoder positions for each motor for debug.
        opMode.telemetry.addData("frontRight", frontRight.getCurrentPosition());
        opMode.telemetry.addData("frontLeft", frontLeft.getCurrentPosition());
        opMode.telemetry.addData("backRight", backRight.getCurrentPosition());
        opMode.telemetry.addData("backLeft", backLeft.getCurrentPosition());

    }



}



