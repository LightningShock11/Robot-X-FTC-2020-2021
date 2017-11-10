package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import robotx.libraries.XModule;

/**
 * Created by Ben Sabo on 11/6/2017.
 */

public class GlyphClaw extends XModule {

    Servo clawServo;
    boolean clawIsOpen = false;

    public GlyphClaw(OpMode op) {super(op);}

    public void init(){
        clawServo = opMode.hardwareMap.servo.get("clawServo");
        opMode.telemetry.addLine("Servo initialized");
    }
    public void toggleClaw(){
        if (clawIsOpen){
            clawServo.setPosition(.3);
            clawIsOpen = false;
            opMode.telemetry.addLine("Closed claw");
        }
        else{
            clawServo.setPosition(.9);
            clawIsOpen = true;
            opMode.telemetry.addLine("Opened claw");
        }
    }
    public void loop(){
        if(xGamepad1().x.wasPressed()) {
            toggleClaw();
        }
    }
    public void stop(){
        clawServo.setPosition(.9);
    }
}
