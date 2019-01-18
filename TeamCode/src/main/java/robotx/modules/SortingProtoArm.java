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
      //  protoServo = opMode.hardwareMap.servo.get("protoServo");

        //initialize color sensor
        sortingColor = opMode.hardwareMap.colorSensor.get("rightSensor");
        sortingColor.setI2cAddress(I2cAddr.create7bit(0x39)); // All REV color sensors use this address
        sortingColor.enableLed(false);

     //   protoServo.setPosition(0.85);
        //Open servo during init

    }

    public void loop(){


        if (sortingColor.red() > 240 && sortingColor.green() > 240 && sortingColor.blue() > 240) {
            //check if the color is close enough to white
            opMode.telemetry.addLine();
            opMode.telemetry.addData("is measured: True", sortingColor.red() + " " + sortingColor.green() + " " + sortingColor.blue());
            //protoServo.setPosition(0.6); //set the servo the drop in white

        } else if (xGamepad1().b.isDown()) {
            //manual override for testing purposes

           // protoServo.setPosition(0.6);
        } else {
            opMode.telemetry.addData("is measured: False", sortingColor.red() + " " + sortingColor.green() + " " + sortingColor.blue());
            //keep the servo open
          //  protoServo.setPosition(0.85); //set the servo to open for gold cubes
        }
    }
}
