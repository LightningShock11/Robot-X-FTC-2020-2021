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

    public GlyphClaw(OpMode op) {super(op);}

    public void init(){
        clawServo = opMode.hardwareMap.servo.get("clawServo");
        rackMotor = opMode.hardwareMap.dcMotor.get("rackMotor");
        rackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slideServo = opMode.hardwareMap.servo.get("slideServo");
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
    public void raiseClaw() {
        rackMotor.setPower(0.5);
    }
    public void lowerClaw() {
        rackMotor.setPower(-0.5);
    }
    public void stopClaw() {
        rackMotor.setPower(0.0);
    }
    public void slideLeft(){
        slideServo.setPosition(1.0);
    }
    public void slideRight(){
        slideServo.setPosition(0.0);
    }
    public void noSlide(){
        slideServo.setPosition(.50);
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
        if (xGamepad1().dpad_left.isDown()){
            slideLeft();
        }
        else if (xGamepad1().dpad_right.isDown()){
            slideRight();
        }
        else{
            noSlide();
        }
    }
    public void stop(){
        closeClaw();
        stopClaw();
        noSlide();
    }
}
