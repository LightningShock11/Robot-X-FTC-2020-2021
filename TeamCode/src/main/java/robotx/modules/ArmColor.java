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

public class ArmColor extends XModule {
    boolean leftBallIsBlue;
    boolean leftBallIsRed;

    ColorSensor armColor;

    public ArmColor(OpMode Op) {super (Op);}

    public void init(){
        armColor = opMode.hardwareMap.colorSensor.get("armColor");
        armColor.setI2cAddress(I2cAddr.create7bit(0x39));
        armColor.enableLed(true);
        opMode.telemetry.addLine("Color sensor is online");
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
    public void loop(){
        if (xGamepad1().a.wasPressed()){
            colorEval();
            }
    }
    public void stop(){
        armColor.resetDeviceConfigurationForOpMode();
    }
}
