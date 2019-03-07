package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import robotx.libraries.XModule;

public class GondalaTest extends XModule {
    DcMotor gMotorLow;
    DcMotor gMotorHigh;

    public GondalaTest(OpMode op){super(op);}

    public void init(){
        gMotorHigh = opMode.hardwareMap.dcMotor.get("gMotorHigh");
        gMotorLow = opMode.hardwareMap.dcMotor.get("gMotorLow");
    }
    public void loop(){
        if (xGamepad2().left_bumper.isDown()){
            gMotorLow.setPower(-1.0);
            gMotorHigh.setPower(1.0);
        }
        else if (xGamepad2().right_bumper.isDown()){
            gMotorLow.setPower(1.0);
            gMotorHigh.setPower(-1.0);
        }
        else {
            gMotorLow.setPower(0.0);
            gMotorHigh.setPower(0.0);
        }
    }
}
