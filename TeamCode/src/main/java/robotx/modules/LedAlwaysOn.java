package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;


import robotx.libraries.XModule;

/**
 * Created by Ben Sabo on 1/19/2018.
 */

public class LedAlwaysOn extends XModule {
    DcMotor blueLed;

    public LedAlwaysOn(OpMode op){super(op);}

    public void init(){
        blueLed = opMode.hardwareMap.dcMotor.get("blueLed");
        blueLed.setPower(1.0);
    }
    public void loop(){
        blueLed.setPower(1.0);
    }
}
