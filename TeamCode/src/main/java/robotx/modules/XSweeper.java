package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.sun.tools.javac.tree.DCTree;

import robotx.libraries.XModule;

public class XSweeper extends XModule {
    DcMotor sweeperMotor;
    boolean sweeperIsOn = false;

    public XSweeper(OpMode op){super(op);}

    public void init(){
        sweeperMotor = opMode.hardwareMap.dcMotor.get("sweeperMotor");
        sweeperMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void toggleSweeper(){
        if (sweeperIsOn){
            sweeperMotor.setPower(0.0);
            sweeperIsOn = false;
        }
        else{
            sweeperMotor.setPower(1.0);
            sweeperIsOn = true;
        }
    }
    public void switchDirection(){
        if (sweeperMotor.getPower() == 1.0){
            sweeperMotor.setPower(-1.0);
        }
        else if (sweeperMotor.getPower() == -1.0){
            sweeperMotor.setPower(1.0);
        }
    }

    public void loop(){
        if (xGamepad2().x.wasPressed()){
            toggleSweeper();
        }
        if (xGamepad2().b.wasPressed()){
            switchDirection();
        }
    }
}
