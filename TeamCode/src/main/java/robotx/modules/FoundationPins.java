package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import robotx.libraries.XModule;

public class FoundationPins extends XModule {
    public FoundationPins(OpMode op){super(op);}

    Servo rightPin;
    Servo leftPin;
    //Relative to the front of the robot
    boolean pinsOut = false;
    double rightPos = .34;
    double leftPos = .65;

    public void init(){
        rightPin = opMode.hardwareMap.servo.get("rightPin");
        leftPin = opMode.hardwareMap.servo.get("leftPin");
        rightPin.setPosition(0.15);
        leftPin.setPosition(0.85);
    }
    public void deployPins(){
        if (pinsOut){
            rightPin.setPosition(0.15);
            leftPin.setPosition(0.85);
            pinsOut = false;
        }
        else{
            rightPin.setPosition(rightPos);
            leftPin.setPosition(leftPos);
            pinsOut = true;
        }
    }
    public void loop(){
        if (xGamepad2().y.wasPressed()){
            deployPins();
        }
    }
}
