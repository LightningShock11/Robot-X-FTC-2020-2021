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
        YMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        YMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        YMotor.getCurrentPosition();
        YMotor.setTargetPosition(0);
        YMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        YMotor.setPower(-1.0);
        }
    public void autoLift(){
        if (up){
            YMotor.setTargetPosition(0);
            YMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            up = false;
        }
        else{
            YMotor.setTargetPosition(500);
            YMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            up = true;
        }
    }
    public void loop(){
        opMode.telemetry.addData("Motor Position:", YMotor.getCurrentPosition());

        if (xGamepad2().a.wasPressed()){
            autoLift();
        }
        if (YMotor.getCurrentPosition() >= 500){
            YMotor.setPower(0);
        }
    }
    public void stop(){
        YMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

}
