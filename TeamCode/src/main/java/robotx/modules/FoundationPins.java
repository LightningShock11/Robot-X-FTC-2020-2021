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
    double rightPos;
    double leftPos;

    public void init(){
        rightPin = opMode.hardwareMap.servo.get("rightPin");
        leftPin = opMode.hardwareMap.servo.get("leftPin");
        rightPin.setPosition(0);
        leftPin.setPosition(0);
    }
    public void deployPins(){
        if (pinsOut){
            rightPin.setPosition(0);
            leftPin.setPosition(0);
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
