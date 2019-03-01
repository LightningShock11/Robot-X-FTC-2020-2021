package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.libraries.XOpMode;
import robotx.modules.CraneController;
import robotx.modules.DumpingBucket;
import robotx.modules.GondalaTest;
import robotx.modules.LiftSystemXY;
import robotx.modules.MechanumDrive;
import robotx.modules.MechanumDriveNoLag;
import robotx.modules.RLdrive;
import robotx.modules.SortingProtoArm;
import robotx.modules.XSweeper;

@TeleOp(name = "IvanOp", group = "Testing")
public class IvanOp extends XOpMode {

    MechanumDriveNoLag mechanumDriveNoLag;
    CraneController craneController;

    public void initModules(){
        super.initModules();

        mechanumDriveNoLag = new MechanumDriveNoLag(this); //Driving system
        activeModules.add(mechanumDriveNoLag);

        craneController = new CraneController(this); //lift and crane systems
        activeModules.add(craneController);

    }
    public void init(){super.init();}
}
