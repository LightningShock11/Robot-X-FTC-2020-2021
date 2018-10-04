package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;

import robotx.libraries.XModule;
/**
 * Created by Ben Sabo & Kush Dalal on 9/28/2018.
 */
public class SortingProtoArm extends XModule {

    Servo protoServo;
    ColorSensor sortingColor;

    public SortingProtoArm (OpMode op){super(op);}

    public void init(){
        //initialize servo
        protoServo = opMode.hardwareMap.servo.get("protoServo");

        //initialize color sensor
        sortingColor = opMode.hardwareMap.colorSensor.get("sortingColor");
        sortingColor.setI2cAddress(I2cAddr.create7bit(0x39)); // All REV color sensors use this address
        sortingColor.enableLed(true);
        opMode.telemetry.addLine("Color sensor is online");

    }

    public void loop(){
        //constantly test the below

        if (sortingColor.red() > 220 && sortingColor.green() > 220 && sortingColor.blue() > 220) {
            //check if the color is close enough to white

            protoServo.setPosition(0.8); //set the servo the drop in white

        }else if (xGamepad1().b.isDown()) {
            //manual override for testing purposes

            protoServo.setPosition(0.8);

        }else{
            //keep the servo open
            protoServo.setPosition(1.0); //set the servo to open for gold cubes
        }

    }
}
