package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;

import robotx.libraries.XModule;

public class ContinuousServoTest extends XModule {

    public ContinuousServoTest(OpMode op){super(op);}

    CRServo testCRServo;

    public void init(){
        testCRServo = opMode.hardwareMap.crservo.get("testCRServo");
    }

    public void loop(){
        if (xGamepad1().dpad_up.isDown()){
            testCRServo.setPower(0.7);
        }
        else if (xGamepad1().dpad_down.isDown()){
            testCRServo.setPower(-0.7);
        }
        else {
            testCRServo.setPower(0.0);
        }
    }
}
