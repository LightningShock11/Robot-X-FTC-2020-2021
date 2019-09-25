package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import robotx.libraries.XModule;

public class StoneClaw extends XModule {

    Servo clawServo;
    boolean closed = true;

    public StoneClaw(OpMode op){super(op);}

    public void init(){
        clawServo = opMode.hardwareMap.servo.get("clawServo");
        clawServo.setPosition(0.0);
    }
    public void toggleClaw(){
        if (closed){
            clawServo.setPosition(1.0);
            closed = false;
        }
        else{
            clawServo.setPosition(0.0);
            closed = true;
        }
    }
    public void loop(){
        opMode.telemetry.addData("Servo position", clawServo.getPosition());
        opMode.telemetry.update();

        if (xGamepad2().a.wasPressed()){
            toggleClaw();
        }
        if (xGamepad2().dpad_right.isDown()) {
            clawServo.setPosition(clawServo.getPosition() + .05);
        }
        else if (xGamepad2().dpad_left.isDown()){
            clawServo.setPosition(clawServo.getPosition() - .05);
        }
    }
}
