package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import robotx.libraries.XModule;

import static android.os.Build.VERSION_CODES.P;

public class CapstonePositioning extends XModule {

    public Servo capstoneServo;

    public CapstonePositioning(OpMode op){super(op);}

    public void init () {
        capstoneServo = opMode.hardwareMap.servo.get("capstoneServo");
        capstoneServo.setPosition(0);
    }
    public void loop () {

        if(xGamepad2().x.wasPressed()) {
            if(capstoneServo.getPosition() == 0) {
                capstoneServo.setPosition(0.5);
            } else {
                capstoneServo.setPosition(0);
            }
        }
    }
}
