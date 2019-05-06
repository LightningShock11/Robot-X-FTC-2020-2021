package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.libraries.XOpMode;
import robotx.modules.MechanumDriveNoLag;
import robotx.modules.TwoWheelAutonIMU;

@TeleOp(name = "TestBotOp", group = "Tests")
public class TestBotOp extends XOpMode {

    MechanumDriveNoLag mechanumDriveNoLag;
    //TwoWheelAutonIMU twoWheelAutonIMU;

    public void initModules(){
        super.initModules();

        mechanumDriveNoLag= new MechanumDriveNoLag(this);
        activeModules.add(mechanumDriveNoLag);

        //twoWheelAutonIMU = new TwoWheelAutonIMU(this);
       // activeModules.add(twoWheelAutonIMU);
    }
}
