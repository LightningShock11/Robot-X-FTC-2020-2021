package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import robotx.libraries.XModule;

public class FourMotorTest extends XModule {
    public FourMotorTest(OpMode op){super(op);}

    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backRight;
    DcMotor backLeft;


    public void init(){
        frontLeft = opMode.hardwareMap.dcMotor.get("frontLeft");
        frontRight = opMode.hardwareMap.dcMotor.get("frontRight");
        backRight = opMode.hardwareMap.dcMotor.get("backRight");
        backLeft = opMode.hardwareMap.dcMotor.get("backLeft");
    }

    public void loop(){
        opMode.telemetry.addData("FL Power:", frontLeft.getPower());
        opMode.telemetry.addData("FR Power:", frontRight.getPower());
        opMode.telemetry.addData("BR Power:", backRight.getPower());
        opMode.telemetry.addData("BL Power", backLeft.getPower());
        opMode.telemetry.update();

        if (xGamepad1().dpad_up.isDown()){
            frontLeft.setPower(1.0);
        }
        else if (xGamepad1().dpad_right.isDown()){
            frontRight.setPower(1.0);
        }
        else if (xGamepad1().dpad_down.isDown()){
            backRight.setPower(1.0);
        }
        else if (xGamepad1().dpad_left.isDown()){
            backLeft.setPower(1.0);
        }
        else {
            frontLeft.setPower(0.0);
            frontRight.setPower(0.0);
            backRight.setPower(0.0);
            backLeft.setPower(0.0);
        }
    }
}
