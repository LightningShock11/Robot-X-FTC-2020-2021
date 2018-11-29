package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.sun.tools.javac.tree.DCTree;

import robotx.libraries.XModule;

public class XSweeper extends XModule {
    CRServo sweeperServo;
    boolean sweeperIsOn = false;

    public XSweeper(OpMode op){super(op);}

    public void init(){
        sweeperServo = opMode.hardwareMap.crservo.get("sweeperServo");
    }

    public void toggleSweeper(){
        if (sweeperIsOn){
            sweeperServo.setPower(0.0);
            sweeperIsOn = false;
        }
        else{
            sweeperServo.setPower(1.0);
            sweeperIsOn = true;
        }
    }
    public void switchDirection(){
        if (sweeperServo.getPower() == 1.0){
            sweeperServo.setPower(-1.0);
        }
        else if (sweeperServo.getPower() == -1.0){
            sweeperServo.setPower(1.0);
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
