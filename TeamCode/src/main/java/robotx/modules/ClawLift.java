package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.security.PublicKey;

import robotx.libraries.XModule;

public class ClawLift extends XModule {
    DcMotor liftMotor;

    public ClawLift(OpMode op){super(op);}

    public void init(){
        liftMotor = opMode.hardwareMap.dcMotor.get("liftMotor");
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void loop(){
        liftMotor.setPower((xGamepad2().right_trigger) - (xGamepad2().left_trigger));
    }
}