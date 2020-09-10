package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;

import robotx.libraries.XModule;
/**
 * Created by Ben Sabo & Kush Dalal on 9/28/2018.
 */
public class StoneDetectionColor extends XModule {

    public ColorSensor stoneColor;
    public DistanceSensor stoneDistance;
    public boolean isLeft;
    public boolean isCenter;
    public boolean isRight;

    public StoneDetectionColor(OpMode op){super(op);}

    public void init(){
        //initialize servo
        //  protoServo = opMode.hardwareMap.servo.get("protoServo");

        //initialize color sensor
        stoneColor = opMode.hardwareMap.colorSensor.get("colorSensor");
        stoneColor.setI2cAddress(I2cAddr.create7bit(0x39)); // All REV color sensors use this address
        stoneColor.enableLed(false);

        //   protoServo.setPosition(0.85);
        //Open servo during init

    }

    public void loop(){


        if (stoneColor.red() < 10 && stoneColor.green() < 10 && stoneColor.blue() < 10) {
            //check if the color is close enough to white
            opMode.telemetry.addLine();
            opMode.telemetry.addData("is measured: True", stoneColor.red() + " " + stoneColor.green() + " " + stoneColor.blue());
            //protoServo.setPosition(0.6); //set the servo the drop in white

        } else if (xGamepad1().b.isDown()) {
            //manual override for testing purposes

            // protoServo.setPosition(0.6);
        } else {
            opMode.telemetry.addData("is measured: False", stoneColor.red() + " " + stoneColor.green() + " " + stoneColor.blue());
            //keep the servo open
            //  protoServo.setPosition(0.85); //set the servo to open for gold cubes
        }
    }
}
