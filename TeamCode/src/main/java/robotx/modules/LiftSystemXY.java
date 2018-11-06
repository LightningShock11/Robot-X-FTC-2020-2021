package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;

import robotx.libraries.XModule;

/**
 * Created by Ben Sabo & Kush Dalal on 9/28/2018.
 */
public class LiftSystemXY extends XModule {

    DcMotor yMotor;
    boolean up = false;


<<<<<<< HEAD:TeamCode/src/main/java/robotx/modules/LiftSystemY.java
    public LiftSystemY(OpMode op) {
        super(op);
    }

    public void init() {
        //initialize servo
        YMotor = opMode.hardwareMap.dcMotor.get("YMotor");
        YMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        YMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        YMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        YMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        YMotor.getCurrentPosition();
        YMotor.setTargetPosition(0);
    }

    public void autoLift() {
        //Toggle method that allows the motor to move back and forth between two positions
        if (up) {
            YMotor.setTargetPosition(0);
            YMotor.setPower(-1.0);
            up = false;
        } else {
            YMotor.setTargetPosition(-36000);
            YMotor.setPower(1.0);
            up = true;
        }
    }
=======
    public LiftSystemXY(OpMode op){super(op);}

    public void init(){
        //initialize motor
        yMotor = opMode.hardwareMap.dcMotor.get("YMotor");
        yMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        yMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        yMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        yMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        yMotor.getCurrentPosition();
        yMotor.setTargetPosition(0);
        }
    public void autoLift(){
        //Toggle method that allows the motor to move back and forth between two positions
        if (up){
            yMotor.setTargetPosition(0);
            yMotor.setPower(-1.0);
            up = false;
        }
        else{
            yMotor.setTargetPosition(-36000);
            yMotor.setPower(1.0);
            up = true;
        }
    }
    public void loop(){
        opMode.telemetry.addData("Current Motor Position:", yMotor.getCurrentPosition());
        opMode.telemetry.addData("Target Motor Position:", yMotor.getTargetPosition());
>>>>>>> 9722fb0df17744ed2428fd3cd5c8aaf1e8f31558:TeamCode/src/main/java/robotx/modules/LiftSystemXY.java

    public void loop() {


        while (xGamepad2().dpad_up.isDown()) {
            opMode.telemetry.addData("Current Motor Position:", YMotor.getCurrentPosition());
            opMode.telemetry.addData("Target Motor Position:", YMotor.getTargetPosition());

            if (xGamepad2().a.wasPressed()) {
                //Call the toggle method
                autoLift();
            }

            //If the motor has reached its target position, stop the motor
            if (YMotor.getTargetPosition() == -36000 && YMotor.getCurrentPosition() <= YMotor.getTargetPosition()) {
                YMotor.setPower(0.0);
            } else if (YMotor.getTargetPosition() == 0 && YMotor.getCurrentPosition() >= YMotor.getTargetPosition()) {
                YMotor.setPower(0.0);
            }


            //Allows for motor to be manually controlled with the dpad
            if (xGamepad2().dpad_up.isDown()) {
                YMotor.setPower(1.0);
            } else if (xGamepad2().dpad_down.isDown()) {
                YMotor.setPower(-1.0);
            }
            if (xGamepad2().a.wasPressed()) {
                autoLift();
                opMode.telemetry.addData("Motor Position:", YMotor.getCurrentPosition());
            } else if (xGamepad2().dpad_down.wasReleased() || xGamepad2().dpad_up.wasReleased()) {
                YMotor.setPower(0.0);
            }

<<<<<<< HEAD:TeamCode/src/main/java/robotx/modules/LiftSystemY.java


        }
    }
    public void stop() {
        YMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
=======
        //If the motor has reached its target position, stop the motor
        if (yMotor.getTargetPosition() == -36000 && yMotor.getCurrentPosition() <= yMotor.getTargetPosition()){
            yMotor.setPower(0.0);
        }
        else if(yMotor.getTargetPosition() == 0 && yMotor.getCurrentPosition() >= yMotor.getTargetPosition()){
            yMotor.setPower(0.0);
        }


        //Allows for motor to be manually controlled with the dpad
        if (xGamepad2().dpad_up.isDown()){
            yMotor.setPower(1.0);
        }
        else if(xGamepad2().dpad_down.isDown()){
            yMotor.setPower(-1.0);
        }
        else if(xGamepad2().dpad_down.wasReleased() || xGamepad2().dpad_up.wasReleased()){
            yMotor.setPower(0.0);
        }
    }
    public void stop(){
        yMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
>>>>>>> 9722fb0df17744ed2428fd3cd5c8aaf1e8f31558:TeamCode/src/main/java/robotx/modules/LiftSystemXY.java
    }
}