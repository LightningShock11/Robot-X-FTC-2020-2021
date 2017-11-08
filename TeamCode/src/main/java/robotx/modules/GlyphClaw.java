package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import robotx.libraries.XModule;

/**
 * Created by Ben Sabo on 11/6/2017.
 */

public class GlyphClaw extends XModule {

    Servo clawServo;
    boolean clawIsOpen;

    public GlyphClaw(OpMode op) {super(op);}

    public void init(){
        clawServo = opMode.hardwareMap.servo.get("clawServo");
    }
    public void toggleClaw(){
        if (clawIsOpen){
            clawServo.setPosition(.3);
            clawIsOpen = false;
        }
        else{
            clawServo.setPosition(.9);
            clawIsOpen = true;
        }
    }
    public void loop(){
        while(xGamepad2().x.wasPressed()){
            clawServo.setPosition(.3);
        }

        clawServo.setPosition(.9);

    }
    public void stop(){
        clawServo.setPosition(.9);
    }
}
