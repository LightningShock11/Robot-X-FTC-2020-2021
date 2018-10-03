package robotx.OldModules;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import robotx.libraries.*;

/**
 * Created by Robot-X Team 4969 on 2/8/2017.
 */
public class LiftSystem extends XModule  {

    DcMotor liftRightMotor;
    DcMotor liftLeftMotor;

    Servo liftLeftServo;
    Servo liftRightServo;

    boolean claspsAreOpen;

    public LiftSystem (OpMode op){
        super(op);
    }

    public void init () {
        liftLeftMotor = opMode.hardwareMap.dcMotor.get("liftLeftMotor");
        liftRightMotor = opMode.hardwareMap.dcMotor.get("liftRightMotor");
        liftRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        liftRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        liftLeftServo = opMode.hardwareMap.servo.get("liftLeftServo");
        liftRightServo = opMode.hardwareMap.servo.get("liftRightServo");
    }

    public void start(){
        closeClasps();
    }

    public void openClasps(){
        liftRightServo.setPosition(0.85);
        liftLeftServo.setPosition(.1);

        claspsAreOpen = true;
    }

    public void closeClasps(){
        liftLeftServo.setPosition(1);
        liftRightServo.setPosition(0);

        claspsAreOpen = false;
    }

    public void toggleClasps(){
        if (claspsAreOpen){
            closeClasps();
        } else {
            openClasps();
        }
    }

    public void raiseLift() {
        liftRightMotor.setPower(0.25);
        liftLeftMotor.setPower(0.25);
    }
    public void lowerLift() {
        liftLeftMotor.setPower(-0.1);
        liftRightMotor.setPower(-0.1);
    }
    public void freezeLift() {
        liftLeftMotor.setPower(0.0);
        liftRightMotor.setPower(0.0);
    }

    public void loop(){
        if (xGamepad2().dpad_up.wasPressed()){
            raiseLift();
        } else if (xGamepad2().dpad_up.wasReleased()){
            freezeLift();
        }

        if (xGamepad2().dpad_down.wasPressed()){
            lowerLift();
        } else if (xGamepad2().dpad_down.wasReleased()){
            freezeLift();
        }

        if (xGamepad2().x.wasPressed()) {
            toggleClasps();
        }
    }

    public void stop(){
        freezeLift();
    }

}


