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

    CRServo rotationServo;

    public ContinuousRotationServoTest(OpMode op) {
        super(op);
    }

    public void init(){
        rotationServo = opMode.hardwareMap.crservo.get("rotationServo");
    }

    public void loop(){

        float rotationPower = xGamepad1().left_stick_y;
        rotationServo.setPower(rotationPower);
        double currentSpeed = xGamepad1().left_stick_y;
        opMode.telemetry.addData("Servo Speed:", currentSpeed);
    }

    }
