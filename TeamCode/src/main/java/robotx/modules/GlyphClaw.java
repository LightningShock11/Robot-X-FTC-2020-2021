package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import robotx.libraries.XModule;

/**
 * Created by Ben Sabo on 11/6/2017.
 */

public class GlyphClaw extends XModule {

    Servo clawServo;
    DcMotor rackMotor;
    Servo rotateServoRight;
    Servo rotateServoLeft;
    //Servo pushServo;
    boolean clawIsOpen = false;
    boolean armIsUp = true;

    public GlyphClaw(OpMode op) {super(op);}

    public void init(){
        clawServo = opMode.hardwareMap.servo.get("clawServo");
        rackMotor = opMode.hardwareMap.dcMotor.get("rackMotor");
        rackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rotateServoRight = opMode.hardwareMap.servo.get("rotateServoRight");
        rotateServoLeft = opMode.hardwareMap.servo.get("rotateServoLeft");
        //pushServo = opMode.hardwareMap.servo.get("pushServo");
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
        clawServo.setPosition(0);
        clawIsOpen = true;
    }
    public void rotateClawUp(){
        rotateServoRight.setPosition(.5);
        rotateServoLeft.setPosition(.5);
    }
    public void rotateClawDown(){
        rotateServoRight.setPosition(0);
        rotateServoLeft.setPosition(1);
    }
    public void toggleRotateClaw(){
        if (armIsUp){
            rotateClawDown();
            armIsUp = false;
        }
        else{
            rotateClawUp();
            armIsUp = true;
        }
    }
    public void raiseClaw() {
        rackMotor.setPower(1);
    }
    public void lowerClaw() {
        rackMotor.setPower(-0.5);
    }
    public void stopClaw() {
        rackMotor.setPower(0.0);
    }

    /*public void pusherOut(){
        pushServo.setPosition(1);
    }
    public void pusherIn(){
        pushServo.setPosition(0);
    }
    */

    public void start(){
        closeClaw();
        rotateClawUp();
    }

    public void loop(){
        if(xGamepad2().x.wasPressed()) {
            toggleClaw();
        }
        if (xGamepad2().dpad_up.isDown()){
            raiseClaw();
        } else if (xGamepad2().dpad_down.isDown()) {
            lowerClaw();
        } else {
            stopClaw();
        }
        if (xGamepad2().y.wasPressed()){
            toggleRotateClaw();
        }
        /*if (xGamepad2().b.isDown()){
            pusherOut();
        }
        else{
            pusherIn();
        }
        */
    }
    public void stop(){
        closeClaw();
        stopClaw();
    }
}