package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import robotx.libraries.XModule;

/**
 * Created by John David Sniegocki and Sam McDowell on 10.28.2019
 */
public class StoneArm extends XModule {

    public DcMotor stoneArm;
    double armPower;
    boolean deployed = false;
    public Servo clawServo;
    long setTime;
    boolean deploy = false;
    ElapsedTime timer = new ElapsedTime();

    public StoneArm(OpMode op) {
        super(op);
    }

    public void init() {
        //initialize motor
        stoneArm = opMode.hardwareMap.dcMotor.get("stoneArm");
        stoneArm.setDirection(DcMotorSimple.Direction.REVERSE);
        //stoneArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        clawServo = opMode.hardwareMap.servo.get("clawServo");
        clawServo.setPosition(0.3);
        setTime = System.currentTimeMillis();
        stoneArm.setPower(-0.35);
    }
    public void grab(){ //Automatically grab stone and deploy arm or release stone and retract arm
        if (deployed){
            clawServo.setPosition(0.3);
            deploy = true;
        }
        else {
            clawServo.setPosition(0.0);
            deploy = true;
        }
        timer.reset();
    }
    public void deploy(){
        if (deployed){
            stoneArm.setPower(-0.35);
            deployed = false;
        }
        else {
            stoneArm.setPower(0.35);
            deployed = true;
        }
    }

    public void loop () {
        if (xGamepad2().right_trigger > 0 || xGamepad2().left_trigger > 0){
            stoneArm.setPower((xGamepad2().right_trigger - xGamepad2().left_trigger)/2);
        }

        if (xGamepad2().a.wasPressed()){
            grab();
        }
        if (deploy && timer.seconds() > .5){
            deploy();
            deploy = false;
        }

        if(xGamepad2().dpad_left.wasPressed()){
            clawServo.setPosition(0);
        }
        if(xGamepad2().dpad_right.wasPressed()) {
            clawServo.setPosition(0.3);
        }
    }
    public void stop(){
        stoneArm.setPower(.35);
    }
}