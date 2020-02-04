package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.util.ElapsedTime;

import robotx.libraries.XModule;

public class FlywheelIntake extends XModule {
    public DcMotor flywheelLeft;
    public DcMotor flywheelRight;
    boolean isFlywheelOn = false;
    public ColorSensor intakeColor;
    public boolean stone = false;

    ElapsedTime timer = new ElapsedTime();
    long setTime;
    boolean jiggle = false;

    boolean flyForward = false;
    boolean flyBackward = false;


    public FlywheelIntake(OpMode op){super(op);}


    public void init(){
        flywheelLeft = opMode.hardwareMap.dcMotor.get("flywheelLeft");
        flywheelLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        flywheelRight = opMode.hardwareMap.dcMotor.get("flywheelRight");
        intakeColor = opMode.hardwareMap.colorSensor.get("intakeColor");
        intakeColor.setI2cAddress(I2cAddr.create7bit(0x39)); // All REV color sensors use this address
        setTime = System.currentTimeMillis();

    }
    public void toggleFly(){
        if (isFlywheelOn){
            isFlywheelOn = false;
            flywheelLeft.setPower(0.0);
            flywheelRight.setPower(0.0);
            flyForward = false;
        }
        else{
            isFlywheelOn = true;
            flywheelRight.setPower(1.0);
            flywheelLeft.setPower(1.0);
            flyForward = true;
        }
    }
    public void toggleFlyReverse(){
        if (isFlywheelOn){
            isFlywheelOn = false;
            flywheelLeft.setPower(0.0);
            flywheelRight.setPower(0.0);
            flyBackward = false;
        }
        else{
            isFlywheelOn = true;
            flywheelRight.setPower(-1.0);
            flywheelLeft.setPower(-1.0);
            flyBackward = true;
        }
    }
    public void jiggleIntake(){
        flywheelRight.setPower(1.0);
        flywheelLeft.setPower(-1.0);

        timer.reset();

        jiggle = true;
    }
    public void loop(){
        opMode.telemetry.addData("Stone in robot?", stone);
        opMode.telemetry.update();

        if(xGamepad2().dpad_down.wasPressed()) {
           toggleFly();
        }
        if(xGamepad2().dpad_up.wasPressed()){
            toggleFlyReverse();
        }

        if (intakeColor.red() > 50 && intakeColor.green() > 50){
            stone = true;
        }
        else {
            stone = false;
        }

        if (jiggle && timer.seconds() > .2){
            if (flyForward) {
                flywheelLeft.setPower(1.0);
                flywheelRight.setPower(1.0);
            }
            else if (flyBackward){
                flywheelRight.setPower(-1.0);
                flywheelLeft.setPower(-1.0);
            }
            jiggle = false;
        }

        if (xGamepad2().x.wasPressed()){
            jiggleIntake();
        }
    }
}
