package robotx.modules;

import robotx.libraries.XModule;
import com.qualcomm.robotcore.*;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;


import robotx.libraries.*;
import robotx.controls.*;

/**
 * Created by Ben Sabo on 10/20/2017.
 */

public class JewelColor extends XModule {
    boolean leftBallIsBlue;
    boolean leftBallIsRed;

    boolean armIsUp;

    ColorSensor armColor;
    Servo armServo;

    public JewelColor(OpMode Op) {super (Op);}

    public void init(){
        armColor = opMode.hardwareMap.colorSensor.get("armColor");
        armColor.setI2cAddress(I2cAddr.create7bit(0x39));
        armColor.enableLed(true);
        opMode.telemetry.addLine("Color sensor is online");
        armServo = opMode.hardwareMap.servo.get("armServo");
    }
    public void colorEval(){
        if (armColor.blue() > armColor.red()){
            opMode.telemetry.addLine("Left ball is blue");
            leftBallIsBlue = true;
            leftBallIsRed = false;
        }
        else if (armColor.blue() < armColor.red()){
            opMode.telemetry.addLine("Left ball is red");
            leftBallIsRed = true;
            leftBallIsBlue = false;
        }
    }
    public void toggleArm(){
        if (armIsUp) {
            lowerArm();
        } else {
            raiseArm();
        }
    }

    public void lowerArm() {
        armServo.setPosition(0.3);
        armIsUp = false;
    }
    public void raiseArm() {
        armServo.setPosition(1.0);
        armIsUp = true;
    }


    public void start(){
        raiseArm();
    }
    public void loop(){
        if (xGamepad1().a.wasPressed()){
            colorEval();
            }
        if (xGamepad1().b.wasPressed()){
            toggleArm();
        }
    }
    public void stop(){
        armColor.resetDeviceConfigurationForOpMode();
        lowerArm();
    }
}
