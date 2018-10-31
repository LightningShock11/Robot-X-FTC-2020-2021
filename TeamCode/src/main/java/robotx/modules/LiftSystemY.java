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


    public LiftSystemY(OpMode op){super(op);}

    public void init(){
        //initialize servo
        YMotor = opMode.hardwareMap.dcMotor.get("YMotor");
        }

    public void loop(){
        if(xGamepad2().a.wasPressed()){
            YMotor.setPower(1);
            

        }

    }


     }
