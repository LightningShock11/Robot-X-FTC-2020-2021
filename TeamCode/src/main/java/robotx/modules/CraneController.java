package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import robotx.libraries.XModule;

public class CraneController extends XModule {
    DcMotor baseMotor;
    DcMotor liftMotor;

    public CraneController(OpMode op){super(op);}
    public int targetHeight = 0;

    public void init(){
        baseMotor = opMode.hardwareMap.dcMotor.get("baseMotor");
        liftMotor = opMode.hardwareMap.dcMotor.get("liftMotor");
        baseMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public void loop(){

        baseMotor.setPower(xGamepad2().left_stick_y); //move the crane arm back and forth

        if (xGamepad2().left_bumper.isDown()){
            liftMotor.setPower(1);
        } else if (xGamepad2().left_bumper.isUp()){
            liftMotor.setPower(0);
        }

        if (xGamepad2().right_bumper.isDown()){
            liftMotor.setPower(-1);
        } else if (xGamepad2().right_bumper.isUp()){
            liftMotor.setPower(0);
        }


    }
    public void ExtendLift (){
        liftMotor.setTargetPosition(targetHeight);
        liftMotor.setPower(1);
        if(liftMotor.getCurrentPosition() == liftMotor.getTargetPosition()){
            liftMotor.setPower(0);
        }
    }
}
