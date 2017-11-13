package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import robotx.libraries.XModule;

/**
 * Created by Ben Sabo on 11/6/2017.
 */

public class GlyphClaw extends XModule {

    Servo clawServo;
    DcMotor rackMotor;
    boolean clawIsOpen = false;

    public GlyphClaw(OpMode op) {super(op);}

    public void init(){
        clawServo = opMode.hardwareMap.servo.get("clawServo");
        rackMotor = opMode.hardwareMap.dcMotor.get("rackMotor");
        opMode.telemetry.addLine("Initialization successful");
    }
    public void toggleClaw(){
        if (clawIsOpen){
            clawServo.setPosition(.3);
            clawIsOpen = false;
            opMode.telemetry.addLine("Closed claw");
        }
        else{
            clawServo.setPosition(.9);
            clawIsOpen = true;
            opMode.telemetry.addLine("Opened claw");
        }
    }
    public void loop(){
        if(xGamepad1().x.wasPressed()) {
            toggleClaw();
        }
        if (xGamepad1().dpad_up.wasPressed()){
            rackMotor.setPower(1);
        }
        else if (xGamepad1().dpad_up.wasReleased()){
            rackMotor.setPower(0);
        }
        if (xGamepad1().dpad_down.wasPressed()){
            rackMotor.setPower(-1);
        }
        else if (xGamepad1().dpad_down.wasReleased()){
            rackMotor.setPower(0.0);
        }
    }
    public void stop(){
        clawServo.setPosition(.9);
        rackMotor.setPower(0);
    }
}
