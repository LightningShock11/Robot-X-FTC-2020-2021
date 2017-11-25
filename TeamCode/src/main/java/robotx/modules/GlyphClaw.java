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
    Servo slideServo;
    boolean clawIsOpen = false;
    boolean armIsUp = true;

    public GlyphClaw(OpMode op) {super(op);}

    public void init(){
        clawServo = opMode.hardwareMap.servo.get("clawServo");
        rackMotor = opMode.hardwareMap.dcMotor.get("rackMotor");
        rackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slideServo = opMode.hardwareMap.servo.get("slideServo");
        slideServo.setPosition(.5);
        openClaw();
        rotateClawUp();
    }
    public void toggleClaw(){
        if (clawIsOpen){
            closeClaw();
        } else {
            openClaw();
        }
    }
    public void closeClaw() {
        clawServo.setPosition(.9);
        clawIsOpen = false;
    }
    public void openClaw() {
        clawServo.setPosition(.3);
        clawIsOpen = true;
    }
    public void rotateClawUp(){
        slideServo.setPosition(2);
    }
    public void rotateClawDown(){
        slideServo.setPosition(0);
    }
    public void toggleRotateClaw(){
        if (armIsUp){
            rotateClawDown();
        }
        else{
            rotateClawUp();
        }
    }
    public void raiseClaw() {
        rackMotor.setPower(0.5);
    }
    public void lowerClaw() {
        rackMotor.setPower(-0.25);
    }
    public void stopClaw() {
        rackMotor.setPower(0.0);
    }


    public void loop(){
        if(xGamepad1().x.wasPressed()) {
            toggleClaw();
        }
        if (xGamepad1().dpad_up.isDown()){
            raiseClaw();
        } else if (xGamepad1().dpad_down.isDown()) {
            lowerClaw();
        } else {
            stopClaw();
        }
        if (xGamepad1().b.wasPressed()){
            toggleRotateClaw();
        }
    }
    public void stop(){
        closeClaw();
        stopClaw();
    }
}
