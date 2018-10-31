package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;

import robotx.libraries.XModule;

/**
 * Created by Ben Sabo & Kush Dalal on 9/28/2018.
 */
public class LiftSystemY extends XModule {

    public DcMotor YMotor;
    boolean up = false;


    public LiftSystemY(OpMode op){super(op);}

    public void init(){
        //initialize servo
        YMotor = opMode.hardwareMap.dcMotor.get("YMotor");
        YMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        YMotor.getCurrentPosition();
        }
    public void autoLift(){
        if (up){
            YMotor.setTargetPosition(0);
            up = false;
        }
        else if (up == false){
            YMotor.setTargetPosition(1);
            up = true;
        }
    }
    public void loop(){
<<<<<<< HEAD
        if(xGamepad2().a.wasPressed()){
            YMotor.setPower(1);


=======
        while (xGamepad2().dpad_up.isDown()){
            YMotor.setPower(1.0);
        }
        while (xGamepad2().dpad_down.isDown()){
            YMotor.setPower(-1.0);
        }
        if (xGamepad2().a.wasPressed()){
            autoLift();
            opMode.telemetry.addData("Motor Position:", YMotor.getCurrentPosition());
>>>>>>> 594af3a94b814a15b6e5cfa57fe74023ba4391d8
        }
    }

}
