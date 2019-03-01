package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;

import robotx.libraries.XModule;

/**
 * Created by Kush Dalal on 11/21/2017.
 */

public class TwoWheelSucker extends XModule {
    CRServo rightServo;
    CRServo leftServo;
    public TwoWheelSucker(OpMode op) {super(op);}
    public void init(){
        rightServo = opMode.hardwareMap.crservo.get("rightServo");
        opMode.telemetry.addLine("Servo initialized");
        leftServo = opMode.hardwareMap.crservo.get("leftServo");
        opMode.telemetry.addLine("Servo initialized");
    }public void loop(){
        if(xGamepad2().a.isDown()) {
            rightServo.setPower(1.0);
            leftServo.setPower(-1.0);

        }else if(xGamepad2().y.isDown()) {
            rightServo.setPower(-1.0);
            leftServo.setPower(1.0);
        }else{
            rightServo.setPower(0.0);
            leftServo.setPower(0.0);

        }

    }

}
