package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import robotx.libraries.XModule;

public class StoneLift extends XModule {
    public StoneLift(OpMode op){super(op);}

    DcMotor liftMotor;

    public void init(){
        liftMotor = opMode.hardwareMap.dcMotor.get("liftMotor");
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void loop(){
        liftMotor.setPower(xGamepad2().left_stick_y);
    }
}
