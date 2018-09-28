package robotx.OldModules;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import robotx.libraries.*;

/**
 * Created by Robot-X Team 4969 on 2/11/2017.
 */
public class LiftOneMotor extends XModule {
    DcMotor liftRightMotor;
    DcMotor liftLeftMotor;

    public LiftOneMotor (OpMode op){
        super(op);
    }

    public void raiseLeftMotor(){
        liftLeftMotor.setPower(0.25);
    }

    public void raiseRightMotor(){
        liftRightMotor.setPower(0.25);
    }

    public void lowerLeftMotor(){
        //liftLeftMotor.setPower(x);
    }

    public void start(){

    }

    public void loop(){
        if (xGamepad2().left_stick_button.wasPressed()){
            raiseLeftMotor();
        }

        if (xGamepad2().right_stick_button.wasPressed()){
            raiseRightMotor();
        }

    }

}
