package robotx.modules;

import com.qualcomm.robotcore.eventloop.*;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import robotx.modules.*;
import robotx.libraries.*;
import robotx.controls.*;
/**
 * Created by Ben Sabo on 9/18/2017.
 */

public class ContinuousRotationServoTest extends XModule {

    Servo rotationServo;

    public ContinuousRotationServoTest(OpMode op) {
        super(op);
    }

    public void init(){
        rotationServo = opMode.hardwareMap.servo.get("rotationServo");
    }

    public void loop(){

        float rotationPower = xGamepad1().left_stick_y;
        rotationPower /= 0.5;
        rotationPower += 0.5;
        rotationServo.setPosition(rotationPower);
        double currentSpeed = xGamepad1().left_stick_y;
        opMode.telemetry.addData("Servo Speed:", currentSpeed);
    }

    }
