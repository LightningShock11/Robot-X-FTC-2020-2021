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
public class LiftSystemY extends XModule {

    public DcMotor YMotor;
    boolean up = false;


    public LiftSystemY(OpMode op){super(op);}

    public void init(){
        //initialize servo
        YMotor = opMode.hardwareMap.dcMotor.get("YMotor");
        YMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        YMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        YMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        YMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        YMotor.getCurrentPosition();
        YMotor.setTargetPosition(0);
        }
    public void autoLift(){
        //Toggle method that allows the motor to move back and forth between two positions
        if (up){
            YMotor.setTargetPosition(0);
            YMotor.setPower(-1.0);
            up = false;
        }
        else{
            YMotor.setTargetPosition(-36000);
            YMotor.setPower(1.0);
            up = true;
        }
    }
    public void loop(){
<<<<<<< HEAD



        while (xGamepad2().dpad_up.isDown()){
=======
        opMode.telemetry.addData("Current Motor Position:", YMotor.getCurrentPosition());
        opMode.telemetry.addData("Target Motor Position:", YMotor.getTargetPosition());

        if (xGamepad2().a.wasPressed()){
            //Call the toggle method
            autoLift();
        }

        //If the motor has reached its target position, stop the motor
        if (YMotor.getTargetPosition() == -36000 && YMotor.getCurrentPosition() <= YMotor.getTargetPosition()){
            YMotor.setPower(0.0);
        }
        else if(YMotor.getTargetPosition() == 0 && YMotor.getCurrentPosition() >= YMotor.getTargetPosition()){
            YMotor.setPower(0.0);
        }


        //Allows for motor to be manually controlled with the dpad
        if (xGamepad2().dpad_up.isDown()){
>>>>>>> ef7a1b2e8f1bd5eb2913cd9f285d533095c01203
            YMotor.setPower(1.0);
        }
        else if(xGamepad2().dpad_down.isDown()){
            YMotor.setPower(-1.0);
        }
<<<<<<< HEAD
        if (xGamepad2().a.wasPressed()){
            autoLift();
            opMode.telemetry.addData("Motor Position:", YMotor.getCurrentPosition());
=======
        else if(xGamepad2().dpad_down.wasReleased() || xGamepad2().dpad_up.wasReleased()){
            YMotor.setPower(0.0);
>>>>>>> ef7a1b2e8f1bd5eb2913cd9f285d533095c01203
        }
    }
    public void stop(){
        YMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

}
