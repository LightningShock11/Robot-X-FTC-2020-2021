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
    boolean up;
    boolean out;
    int xMax = 1345;
    int yMax = 3100;

    public LiftSystemXY(OpMode op) {
        super(op);
    }

    public void init(){
        //initialize motor
        yMotor = opMode.hardwareMap.dcMotor.get("yMotor");
        yMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        yMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        yMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        yMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        yMotor.getCurrentPosition();
        yMotor.setTargetPosition(0);
        xMotor = opMode.hardwareMap.dcMotor.get("xMotor");
        xMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        xMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        xMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        xMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        xMotor.getCurrentPosition();
        xMotor.setTargetPosition(0);
        }
    public void yMotor(double power){
        yMotor.setPower(power);
        }
    public void xMotor(double power){
        xMotor.setPower(power);
    }
    public void autoLiftX(){
        //Toggle method that allows the motor to move back and forth between two positions
        if (out){
            xMotor.setTargetPosition(0);
            xMotor.setPower(-1.0);
            out = false;
        }
        else{
            xMotor.setTargetPosition(xMax);
            xMotor.setPower(1.0);
            out = true;
        }
    }
    public void autoLiftY(){
        if (up) {
            yMotor.setTargetPosition(0);
            yMotor.setPower(-1.0);
            up = false;
        }
        else {
            yMotor.setTargetPosition(yMax);
            yMotor.setPower(1.0);
            up = true;
        }
    }
    public void extendX(int xPosition){
        xMotor.setTargetPosition(xPosition);
        xMotor.setPower(1.0);
        if (xMotor.getTargetPosition() == xPosition && xMotor.getCurrentPosition() >= xMotor.getTargetPosition()) {
            xMotor.setPower(0.0);
        }
    }
    public void retractX(){
        xMotor.setTargetPosition(0);
        xMotor.setPower(-1.0);
        if (xMotor.getTargetPosition() == 0 && xMotor.getCurrentPosition() <= xMotor.getTargetPosition()){
            xMotor.setPower(0.0);
        }
    }
    public void extendY(int yPosition){
        yMotor.setTargetPosition(yPosition);
        yMotor.setPower(1.0);
        if (yMotor.getTargetPosition() == yPosition && yMotor.getCurrentPosition() >= yMotor.getTargetPosition()) {
            yMotor.setPower(0.0);
        }
    }
    public void retractYPos(int yPositionDown){
        yMotor.setTargetPosition(yPositionDown);
        yMotor.setPower(-1.0);
        if (yMotor.getTargetPosition() == yPositionDown && yMotor.getCurrentPosition() <= yMotor.getTargetPosition()){
            yMotor.setPower(0.0);
        }
    }
    public void retractY(){
        yMotor.setTargetPosition(0);
        yMotor.setPower(-1.0);
        if (yMotor.getTargetPosition() == 0 && yMotor.getCurrentPosition() <= yMotor.getTargetPosition()){
            yMotor.setPower(0.0);
        }
    }
    public void loop(){
        opMode.telemetry.addData("X Motor Position:", xMotor.getCurrentPosition());
        opMode.telemetry.addData("X Target Position:", xMotor.getTargetPosition());
        opMode.telemetry.addData("Y Motor Position:", yMotor.getCurrentPosition());
        opMode.telemetry.addData("Y Target Position:", yMotor.getTargetPosition());
        opMode.telemetry.addData("Y power:", yMotor.getPower());

        //If the motor has reached its target position, stop the motor
        if (yMotor.getTargetPosition() == yMax && yMotor.getCurrentPosition() >= yMotor.getTargetPosition()){
            yMotor.setPower(0.0);
        }
        else if(yMotor.getTargetPosition() == 0 && yMotor.getCurrentPosition() <= yMotor.getTargetPosition()){
            yMotor.setPower(0.0);
        }
        if (xMotor.getTargetPosition() == xMax && xMotor.getCurrentPosition() >= xMotor.getTargetPosition()){
            xMotor.setPower(0.0);
        }
        else if (xMotor.getTargetPosition() == 0 && xMotor.getCurrentPosition() <= xMotor.getTargetPosition()){
            xMotor.setPower(0.0);
        }

        if (xGamepad2().y.wasPressed()){
            autoLiftY();
        }
        if (xGamepad2().a.wasPressed()){
            autoLiftX();
        }
        //Allows for motor to be manually controlled with the dpad
        if (xGamepad2().dpad_up.isDown()){
            yMotor.setPower(2.0);
        }
        else if(xGamepad2().dpad_down.isDown()){
            yMotor.setPower(-2.0);
        }
        if (xGamepad2().right_bumper.isDown()){
            xMotor.setPower(2.0);
        }
        else if (xGamepad2().left_bumper.isDown()){
            xMotor.setPower(-2.0);
        }
        if (xGamepad2().left_bumper.wasReleased() || xGamepad2().right_bumper.wasReleased()){
            xMotor.setPower(0.0);
        }
        if(xGamepad2().dpad_down.wasReleased() || xGamepad2().dpad_up.wasReleased()){
            yMotor.setPower(0.0);
        }
    }
    public void stop(){
        yMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        xMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
}