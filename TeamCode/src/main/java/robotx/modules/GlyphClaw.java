package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import robotx.libraries.XModule;
import robotx.opmodes.autonomous.AutonTestingOp;

/**
 * Created by Ben Sabo on 11/6/2017.
 */

public class GlyphClaw extends XModule {

    double clawServoPosition = 0.0;
    Servo clawServo;
    DcMotor rackMotor;
    CRServo rotateServo;
    boolean clawIsOpen;
    boolean armIsUp;
    public final void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    public GlyphClaw(OpMode op) {super(op);}

    public void init(){
        clawServo = opMode.hardwareMap.servo.get("clawServo");
        rackMotor = opMode.hardwareMap.dcMotor.get("rackMotor");
        rackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rotateServo = opMode.hardwareMap.crservo.get("rotateServo");

        closeClaw();
        clawIsOpen = false;
        rotateClawUp();
        armIsUp = true;
    }
    public void toggleClaw(){
        if (clawIsOpen){
            closeClaw();
            clawIsOpen = false;
        } else {
            openClaw();
            clawIsOpen = true;
        }
    }
    public void closeClaw() {
        clawServoPosition = 1.0;
        updateClawServo();

    }
    public void openClaw() {
        clawServoPosition = 0.0;
        updateClawServo();

    }
    public void rotateClawUp(){
        rotateServo.setPower(1);
        sleep(1000);
        rotateServo.setPower(0);
    }
    public void rotateClawDown(){
        rotateServo.setPower(-1);
        sleep(1000);
        rotateServo.setPower(0);
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

    private void updateClawServo() {
        clawServo.setPosition(clawServoPosition);
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
        opMode.telemetry.addData("Servo Position:", clawServo.getPosition());

        double updateServoPosition = (xGamepad2().right_trigger-xGamepad2().left_trigger)/20.0;
        clawServoPosition += updateServoPosition;
        updateClawServo();

        if(xGamepad2().b.wasPressed()){
            clawServoPosition = 0.70;
        }

        if (clawServoPosition > 0.95) {
            clawIsOpen = false;
        }
        if (clawServoPosition < 0.05) {
            clawIsOpen = true;
        }

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
        //Debug code:
        opMode.telemetry.addData("ClawIsOpen", clawIsOpen);
    }
    public void stop(){
        closeClaw();
        stopClaw();
    }
}