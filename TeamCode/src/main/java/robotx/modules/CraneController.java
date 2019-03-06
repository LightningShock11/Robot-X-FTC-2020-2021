package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import robotx.libraries.XModule;

public class CraneController extends XModule {
    DcMotor baseRightMotor;
    DcMotor baseLeftMotor;

    public CraneController(OpMode op){super(op);}

    public void init(){
        baseRightMotor = opMode.hardwareMap.dcMotor.get("baseRightMotor");
        baseLeftMotor = opMode.hardwareMap.dcMotor.get("baseLeftMotor");
        baseRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        baseLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        baseLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void loop(){

        baseRightMotor.setPower(xGamepad2().right_trigger); //move the crane arm back and forth
        baseLeftMotor.setPower(xGamepad2().right_trigger);

        baseRightMotor.setPower(-xGamepad2().left_trigger);
        baseLeftMotor.setPower(-xGamepad2().left_trigger);
    }
}
