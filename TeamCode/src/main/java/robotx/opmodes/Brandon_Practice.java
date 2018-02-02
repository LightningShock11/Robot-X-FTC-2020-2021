package robotx.opmodes;

/**
 * Created by Anna Esketit on 1/31/2018.
 */

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


public class Brandon_Practice {

    private ElapsedTime runtime = new ElapsedTime();

    DcMotor frontleftmotor;
    DcMotor frontrightmotor;
    DcMotor backleftmotor;
    DcMotor backrightmotor;

    //rotateRobot

    //double power = 0.5;

    public void runOpMode(){
        moveForward(0.5);
        moveLeft(0.5);
        moveBackward(0.5);
        moveRight(0.5);
        stop(1);
    }

    public void moveForward(double seconds) {
        frontleftmotor.setPower(0.5);
        frontrightmotor.setPower(-0.5);
        backleftmotor.setPower(0.5);
        backrightmotor.setPower(0.5);
    }

    public void moveBackward(double seconds){
        frontleftmotor.setPower(-0.5);
        frontrightmotor.setPower(0.5);
        backleftmotor.setPower(-0.5);
        backrightmotor.setPower(-0.5);
    }

    public void moveLeft(double seconds){
        frontleftmotor.setPower(-0.5);
        frontrightmotor.setPower(-0.5);
        backleftmotor.setPower(0.5);
        backrightmotor.setPower(0.5);
    }

    public void moveRight(double seconds){
        frontleftmotor.setPower(0.5);
        frontrightmotor.setPower(0.5);
        backleftmotor.setPower(-0.5);
        backrightmotor.setPower(-0.5);
    }

    public void stop(double seconds){
        frontleftmotor.setPower(0);
        frontrightmotor.setPower(0);
        backleftmotor.setPower(0);
        backrightmotor.setPower(0);
    }
}
