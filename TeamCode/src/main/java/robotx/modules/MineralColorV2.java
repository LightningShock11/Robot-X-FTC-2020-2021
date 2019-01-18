package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;

import robotx.libraries.AutonomousMovement;
import robotx.libraries.XModule;

/**
 * Created by Ben Sabo on 10/20/2017.
 */

public class MineralColorV2 extends XModule {

    public AutonomousMovement movement;
    public LiftSystemXY liftSystemXY;

    ColorSensor backSensor;
    ColorSensor frontSensor;

    public boolean isBackWhite;
    public boolean isBackGold;
    public boolean isFrontGold;
    public boolean isFrontWhite;

    public int position;



    public MineralColorV2(OpMode Op) {super (Op);}

    public void init(){
        //----------initialize color sensors----------\\
        backSensor = opMode.hardwareMap.colorSensor.get("backSensor");
        backSensor.setI2cAddress(I2cAddr.create7bit(0x39)); // All REV color sensors use this address
        backSensor.enableLed(false);

        frontSensor = opMode.hardwareMap.colorSensor.get("frontSensor");
        frontSensor.setI2cAddress(I2cAddr.create7bit(0x39)); // All REV color sensors use this address
        frontSensor.enableLed(false);
        //---------------------------------------------\\


    }

    private void sleep(long milliseconds) { //Sleep during auton method
        if (opMode instanceof LinearOpMode) {
            ((LinearOpMode) opMode).sleep(milliseconds);
        }
    }

    public void DetectGold() //Method to detect the side gold is on
    {
        //---------Sensors measuring what is on the left and right side---------\\
        if(backSensor.red() > 240 && backSensor.green() > 240 && backSensor.blue() > 240) //check if the color is close enough to white
        {
            isBackWhite = true;
        }else{
            isBackGold = true;
        }
        sleep(1000);
        liftSystemXY.extendX(400);
        sleep(250);
        if(frontSensor.red() > 240 && frontSensor.green() > 240 && frontSensor.blue() > 240 )
        {
            isFrontWhite = true;
        }
        else {
            isFrontGold = true;
        }
        sleep(250);
        opMode.telemetry.update();

        //---------------------------------------------------------------------\\

        //---------------Defining the position of the gold cube----------------\\
        if(isBackWhite && isFrontWhite)
        {
            position = 1; //this is read left to right from the lander's POV
        }else if (isBackGold && isFrontWhite)
        {
            position = 2;
        }else if (isBackGold && isFrontWhite)
        {
            position = 3;
        }
        //---------------------------------------------------------------------\\
        knockMineral();

    }

    public  void knockMineral() //Run motions for knocking off minerals
    {
        //---------------if the gold is in pos 1----------------\\
        if(position == 1){
            opMode.telemetry.addLine();
            opMode.telemetry.addData("Gold is in pos: ", position);
            opMode.telemetry.update();
            liftSystemXY.retractX();
            movement.pointTurnLeft(40);
            liftSystemXY.extendX(1325);
            sleep(150);
            movement.pointTurnLeft(30);
        }
        //---------------if the gold is in pos 2----------------\\
        else if (position == 2){
            opMode.telemetry.addLine();
            opMode.telemetry.addData("Gold is in pos: ", position);
            opMode.telemetry.update();
            movement.pointTurnLeft(70);
        }
        //---------------if the gold is in pos 3----------------\\
        else if (position == 3){
            opMode.telemetry.addLine();
            opMode.telemetry.addData("Gold is in pos: ", position);
            opMode.telemetry.update();
            movement.pointTurnRight(30);
            sleep(150);
            liftSystemXY.retractX();
            movement.pointTurnLeft(100);
        }
        //---------------If colorsensor fails----------------\\
        else if(position == 0){
            opMode.telemetry.addLine();
            opMode.telemetry.addData("Gold Not found ", position);
            opMode.telemetry.update();
            liftSystemXY.retractX();
            sleep(5000);
            movement.pointTurnLeft(70);

        }
    }




}
