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
    DcMotor xMotor;
    DcMotor beltMotor;
    boolean up = false;
    boolean beltOn = false;


    public LiftSystemXY(OpMode op) {
        super(op);
    }

    public void init(){
        //initialize motor
        yMotor = opMode.hardwareMap.dcMotor.get("yMotor");
        yMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        yMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        yMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        yMotor.getCurrentPosition();
        yMotor.setTargetPosition(0);
        xMotor = opMode.hardwareMap.dcMotor.get("xMotor");
        xMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        xMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        xMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        xMotor.getCurrentPosition();
        xMotor.setTargetPosition(0);

        beltMotor = opMode.hardwareMap.dcMotor.get("beltMotor");
        beltMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        }
    public void yMotor(double power){
        yMotor.setPower(power);
        }
    public void xMotor(double power){
        xMotor.setPower(power);
    }
    public void autoLift(){
        //Toggle method that allows the motor to move back and forth between two positions
        if (up){
            yMotor.setTargetPosition(0);
            yMotor.setPower(-1.0);
            xMotor.setTargetPosition(0);
            xMotor.setPower(-1.0);
            up = false;
        }
        else{
            yMotor.setTargetPosition(1325);
            yMotor.setPower(1.0);
            xMotor.setTargetPosition(1325);
            xMotor.setPower(1.0);
            up = true;
        }
    }
    public void toggleBelt(){
        if (beltOn){
            beltMotor.setPower(0.0);
            beltOn = false;
        }
        else{
            beltMotor.setPower(1.0);
            beltOn = true;
        }
    }
    public void loop(){
        opMode.telemetry.addData("Current Motor Position:", yMotor.getCurrentPosition());
        opMode.telemetry.addData("Target Motor Position:", yMotor.getTargetPosition());

        //If the motor has reached its target position, stop the motor
        if (yMotor.getTargetPosition() == 1345 && yMotor.getCurrentPosition() >= yMotor.getTargetPosition()){
            yMotor.setPower(0.0);
        }
        else if(yMotor.getTargetPosition() == 0 && yMotor.getCurrentPosition() <= yMotor.getTargetPosition()){
            yMotor.setPower(0.0);
        }
        if (xMotor.getTargetPosition() == 1345 && xMotor.getCurrentPosition() >= xMotor.getTargetPosition()){
            xMotor.setPower(0.0);
        }
        else if (xMotor.getTargetPosition() == 0 && xMotor.getCurrentPosition() <= xMotor.getTargetPosition()){
            xMotor.setPower(0.0);
        }

        if (xGamepad2().y.wasPressed()){
            autoLift();
        }

        //Allows for motor to be manually controlled with the dpad
        if (xGamepad2().dpad_up.isDown()){
            yMotor.setPower(1.0);
        }
        else if(xGamepad2().dpad_down.isDown()){
            yMotor.setPower(-1.0);
        }
        if (xGamepad2().right_bumper.isDown()){
            xMotor.setPower(1.0);
        }
        else if (xGamepad2().left_bumper.isDown()){
            xMotor.setPower(-1.0);
        }
        if (xGamepad2().left_bumper.wasReleased() || xGamepad2().right_bumper.wasReleased()){
            xMotor.setPower(0.0);
        }
        if(xGamepad2().dpad_down.wasReleased() || xGamepad2().dpad_up.wasReleased()){
            yMotor.setPower(0.0);
        }

        //Toggles the conveyor belt
        if (xGamepad2().a.wasPressed()){
            toggleBelt();
        }
    }
    public void stop(){
        yMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        xMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
}
