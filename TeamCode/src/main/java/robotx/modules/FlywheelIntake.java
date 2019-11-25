package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import robotx.libraries.XModule;

public class FlywheelIntake extends XModule {
    public DcMotor flywheelLeft;
    public DcMotor flywheelRight;
    boolean isFlywheelOn = false;


    public FlywheelIntake(OpMode op){super(op);}


    public void init(){
        flywheelLeft = opMode.hardwareMap.dcMotor.get("flywheelLeft");
        flywheelLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        flywheelRight = opMode.hardwareMap.dcMotor.get("flywheelRight");
    }
    public void toggleFly(){
        if (isFlywheelOn){
            isFlywheelOn = false;
            flywheelLeft.setPower(0.0);
            flywheelRight.setPower(0.0);
        }
        else{
            isFlywheelOn = true;
            flywheelRight.setPower(1.0);
            flywheelLeft.setPower(1.0);
        }
    }
    public void toggleFlyReverse(){
        if (isFlywheelOn){
            isFlywheelOn = false;
            flywheelLeft.setPower(0.0);
            flywheelRight.setPower(0.0);
        }
        else{
            isFlywheelOn = true;
            flywheelRight.setPower(-1.0);
            flywheelLeft.setPower(-1.0);
        }
    }
    public void loop(){

        if(xGamepad2().dpad_down.wasPressed()) {
           toggleFly();
        }
        if(xGamepad2().dpad_up.wasPressed()){
            toggleFlyReverse();
        }
    }
}
