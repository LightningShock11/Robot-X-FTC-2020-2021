package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;

import robotx.libraries.AutonomousMovement;
import robotx.libraries.OmniAutonomousMovement;
import robotx.libraries.XModule;

/**
 * Created by Ben Sabo on 10/20/2017.
 */

public class MineralColor extends XModule {

    public AutonomousMovement movement;
    public LiftSystemXY liftSystemXY;

    ColorSensor rightSensor;
    ColorSensor leftSensor;

    public boolean isRightWhite;
    public boolean isRightGold;
    public boolean isLeftGold;
    public boolean isLeftWhite;

    public int position;



    public MineralColor(OpMode Op) {super (Op);}

    public void init(){
        //initialize color sensor
        rightSensor = opMode.hardwareMap.colorSensor.get("rightSensor");
        rightSensor.setI2cAddress(I2cAddr.create7bit(0x39)); // All REV color sensors use this address
        rightSensor.enableLed(false);

        leftSensor = opMode.hardwareMap.colorSensor.get("leftSensor");
        leftSensor.setI2cAddress(I2cAddr.create7bit(0x39)); // All REV color sensors use this address
        leftSensor.enableLed(false);

    }

    private void sleep(long milliseconds) {
        if (opMode instanceof LinearOpMode) {
            ((LinearOpMode) opMode).sleep(milliseconds);
        }
    }

    public void DetectGold()
    {
        movement.pointTurnRight(20);
        if(rightSensor.red() > 240 && rightSensor.green() > 240 && rightSensor.blue() > 240) //check if the color is close enough to white
        {
            isRightWhite = true;
        }else{
            isRightGold = true;
        }
        movement.pointTurnLeft(20);
        if(leftSensor.red() > 240 && leftSensor.green() > 240 && leftSensor.blue() > 240 )
        {
            isLeftWhite = true;
        }
        else {
            isLeftGold = true;
        }


        if(isRightWhite && isLeftWhite)
        {
            position = 1; //this is read left to right from the lander's POV
        }else if (isRightGold && isLeftGold)
        {
            position = 2;
        }else if (isRightGold && isLeftWhite)
        {
            position = 3;
        }

    }

    public  void knockMineral(){
        if(position == 1){
            liftSystemXY.retractX();
            movement.pointTurnLeft(40);
            liftSystemXY.extendX(1325);
            sleep(150);
            movement.pointTurnLeft(30);

        }else if (position == 2){
            movement.pointTurnLeft(70);
        }else if (position == 3){
            movement.pointTurnRight(30);
            sleep(150);
            liftSystemXY.retractX();
            movement.pointTurnLeft(100);
        }else if(position == 0){
            opMode.telemetry.addData("Gold Not found ", position);
        }
    }




}
