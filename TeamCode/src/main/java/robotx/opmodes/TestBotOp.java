package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.libraries.XOpMode;
import robotx.modules.ContinuousServoTest;
import robotx.modules.MechanumDriveNoLag;
import robotx.modules.StoneClaw;
import robotx.modules.StoneDetectionColor;
import robotx.modules.TwoWheelAutonIMU;

@TeleOp(name = "TestBotOp", group = "Tests")
public class TestBotOp extends XOpMode {

    //MechanumDriveNoLag mechanumDriveNoLag;
    StoneClaw stoneClaw;
    ContinuousServoTest continuousServoTest;
    StoneDetectionColor detection;
    //TwoWheelAutonIMU twoWheelAutonIMU;

    public void initModules(){
        super.initModules();

        //mechanumDriveNoLag= new MechanumDriveNoLag(this);
        //activeModules.add(mechanumDriveNoLag);

        //stoneClaw = new StoneClaw(this);
        //activeModules.add(stoneClaw);

        //continuousServoTest = new ContinuousServoTest(this);
        //activeModules.add(continuousServoTest);

        detection = new StoneDetectionColor(this);
        activeModules.add(detection);

        //twoWheelAutonIMU = new TwoWheelAutonIMU(this);
       // activeModules.add(twoWheelAutonIMU);
    }
}
