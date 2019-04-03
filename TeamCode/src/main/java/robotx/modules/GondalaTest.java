package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import robotx.libraries.XModule;

public class GondalaTest extends XModule {
    DcMotor gMotorLow;
    DcMotor gMotorHigh;

    public GondalaTest(OpMode op){super(op);}

    public void init(){
        gMotorHigh = opMode.hardwareMap.dcMotor.get("gMotorHigh");
        gMotorLow = opMode.hardwareMap.dcMotor.get("gMotorLow");
        gMotorLow.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        gMotorHigh.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        gMotorHigh.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void loop(){
        /*if (xGamepad2().left_bumper.isDown()){
            gMotorLow.setPower(-1.0);
            gMotorHigh.setPower(-1.0);
        }
        else if (xGamepad2().right_bumper.isDown()){
            gMotorHigh.setPower(1.0);
            gMotorLow.setPower(1.0);
        }
        else {
            gMotorLow.setPower(0.0);
            gMotorHigh.setPower(0.0);
        }*/
        gMotorHigh.setPower(-xGamepad2().left_trigger);
        gMotorLow.setPower(xGamepad2().right_trigger);
    }
}
