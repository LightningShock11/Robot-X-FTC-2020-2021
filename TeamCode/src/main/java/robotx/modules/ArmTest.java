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

public class ArmTest extends XModule {

    DcMotor armMotor;
    Servo armServo;

    boolean isClawOpen;

    public ArmTest(OpMode op) {
        super(op);
    }

    public void init() {
        armMotor = opMode.hardwareMap.dcMotor.get("armMotor");
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        armServo = opMode.hardwareMap.servo.get("armServo");
    }

    public void goForward() {
        armMotor.setPower(1.0);
    }

    public void goBackward() {
        armMotor.setPower(-1.0);
    }

    public void noMove(){
        armMotor.setPower(0.0);
    }

    public void openClaw(){
        armServo.setPosition(20.0);

        isClawOpen = true;
    }
    public void closeClaw(){
        armServo.setPosition(60.0);

        isClawOpen = false;
    }
    public void toggleClaw(){
        if (isClawOpen) {
            closeClaw();
        }
        else {
            openClaw();
        }
    }

    public void loop() {
        while (xGamepad1().a.wasPressed()){
            goForward();
        }
        while (xGamepad1().b.wasPressed()){
            goBackward();
        }
        if (xGamepad1().a.wasReleased()){
            noMove();
        }
        if (xGamepad1().b.wasReleased()){
            noMove();
        }
        if (xGamepad1().left_bumper.wasPressed()){
            toggleClaw();
            }
        }
    }
