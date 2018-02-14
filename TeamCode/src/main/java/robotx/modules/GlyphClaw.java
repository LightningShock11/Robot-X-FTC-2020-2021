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
    Servo rotateServo;
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
        rackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rotateServo = opMode.hardwareMap.servo.get("rotateServo");

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
        clawServoPosition = 0.0;
        updateClawServo();
        clawIsOpen = false;
    }
    public void openClaw() {
        clawServoPosition = 1.0;
        updateClawServo();
        clawIsOpen = true;
    }
    public void rotateClawUp(){
        rotateServo.setPosition(0.05);
        armIsUp = true;
    }
    public void rotateClawDown(){
        rotateServo.setPosition(0.5);
        armIsUp = false;
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
    long timeStartedRaising = 0L;
    boolean clawIsRaising = false;
    public void startRaisingClaw(){
        timeStartedRaising = System.currentTimeMillis();
        clawIsRaising = true;
    }
    public void stopRaisingClaw(){
        rackMotor.setPower(0.0);
        clawIsRaising = false;
    }
    public void raiseClaw() {
        /*double power = (double)(System.currentTimeMillis() - timeStartedRaising) / 700.0;
        power = 0.5*Math.pow(power, 2.0) + 0.5;

        // Clamp the power.
        if (power>1.0) {
            power = 1.0;
        } else if (power<0.0) {
            power = 0.0;
        }

        opMode.telemetry.addData("clawPower", power);*/

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

    public void loop(){
        opMode.telemetry.addData("Servo Position:", clawServo.getPosition());

        double updateServoPosition = (xGamepad2().right_trigger-xGamepad2().left_trigger)/20.0;
        clawServoPosition += updateServoPosition;
        updateClawServo();

        if(xGamepad2().b.wasPressed()){
            clawServoPosition = 0.70;
        }

        if (clawServoPosition > 0.95) {
            clawIsOpen = true;
        }
        if (clawServoPosition < 0.05) {
            clawIsOpen = false;
        }

        // New claw code for acceleration curve.
       /* if (xGamepad2().dpad_up.wasPressed()) {
            startRaisingClaw();
        } else if (xGamepad2().dpad_down.wasReleased()) {
            stopRaisingClaw();
        }*/

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
        opMode.telemetry.addData("clawIsOpen", clawIsOpen);
    }
    public void stop(){
        stopClaw();
    }
}