package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.sun.tools.javac.tree.DCTree;

import robotx.libraries.XModule;

public class XSweeper extends XModule {
    DcMotor sweeperMotor;
    boolean sweeperIsOn = false;

    public XSweeper(OpMode op){super(op);}

    public void init(){
        sweeperMotor = opMode.hardwareMap.dcMotor.get("sweeperMotor");
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

    public void loop(){
        if (xGamepad2().x.wasPressed()){
            toggleSweeper();
        }
    }
}
