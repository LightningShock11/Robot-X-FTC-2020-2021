package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import robotx.libraries.XModule;

public class StoneClaw extends XModule {

    public Servo clawServo;

    public StoneClaw(OpMode op){super(op);}

    public void init () {
        clawServo = opMode.hardwareMap.servo.get("clawServo");
        clawServo.setPosition(0.8);
    }

    public void loop () {
        if(xGamepad2().dpad_left.wasPressed()){
            clawServo.setPosition(0);
        }
        if(xGamepad2().dpad_right.wasPressed()) {
            clawServo.setPosition(0.8);
        }
        opMode.telemetry.addData("Claw Position: ", clawServo.getPosition());
        opMode.telemetry.update();
    }
}
