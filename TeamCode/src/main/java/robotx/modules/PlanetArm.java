package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.sun.tools.javac.tree.DCTree;

import robotx.libraries.XModule;

public class PlanetArm extends XModule {
    DcMotor rotateMotor;
    DcMotor extendMotor;

    public PlanetArm(OpMode op){super(op);}

    public void init(){
        rotateMotor = opMode.hardwareMap.dcMotor.get("rotateMotor");
        extendMotor = opMode.hardwareMap.dcMotor.get("extendMotor");
    }

    public void loop(){
        rotateMotor.setPower(xGamepad2().right_trigger - xGamepad2().left_trigger);

        if (xGamepad2().right_bumper.isDown()){
            extendMotor.setPower(1.0);
        }
        else if (xGamepad2().left_bumper.isDown()){
            extendMotor.setPower(-1.0);
        }
        else {
            extendMotor.setPower(0.0);
        }
    }
}
