package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import robotx.libraries.XModule;

public class SortingProtoArm extends XModule {

    Servo protoServo;

    public SortingProtoArm (OpMode op){super(op);}

    public void init(){
        protoServo = opMode.hardwareMap.servo.get("protoServo");
    }

    public void loop(){
        if (xGamepad1().b.isDown()){
            protoServo.setPosition(0.8);
        } else
        protoServo.setPosition(1.0);

    }
}
