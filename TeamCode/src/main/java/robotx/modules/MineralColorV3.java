package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;

import robotx.libraries.AutonMoveSimple;
import robotx.libraries.AutonomousMovement;
import robotx.libraries.XModule;

/**
 * Created by Ben Sabo on 10/20/2017.
 */

public class MineralColorV3 extends XModule {

    public AutonomousMovement movement;
    public AutonMoveSimple drive;
    public LiftSystemXY liftSystemXY;

    ColorSensor backSensor;


    public boolean isThirdWhite;
    public boolean isThirdGold;
    public boolean isSecondGold;
    public boolean isSecondWhite;

    public int position;



    public MineralColorV3(OpMode Op) {super (Op);}

    public void init(){
        //----------initialize color sensors----------\\
        backSensor = opMode.hardwareMap.colorSensor.get("backSensor");
        backSensor.setI2cAddress(I2cAddr.create7bit(0x39)); // All REV color sensors use this address
        backSensor.enableLed(false);


        //---------------------------------------------\\


    }

    private void sleep(long milliseconds) { //Sleep during auton method
        if (opMode instanceof LinearOpMode) {
            ((LinearOpMode) opMode).sleep(milliseconds);
        }
    }



    public void DetectGold3() //Method to detect the side gold is on
    {
        if(backSensor.red() > 200 && backSensor.green() > 200 && backSensor.blue() > 200) //check if the color is close enough to white
        {
            isThirdWhite = true;
        }else{
            isThirdGold = true;
        }
    }
    public void DetectGold2() //Method to detect the side gold is on
    {
        if(backSensor.red() > 200 && backSensor.green() > 200 && backSensor.blue() > 200) //check if the color is close enough to white
        {
            isSecondWhite = true;
        }else{
            isSecondGold = true;
        }
    }


    public  void knockMineral() //Run motions for knocking off minerals
    {

    }




}
