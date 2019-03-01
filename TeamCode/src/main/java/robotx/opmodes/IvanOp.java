package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.libraries.XOpMode;
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
    //TwoMotorDrive twoMotorDrive;
    //OneStickDrive oneStickDrive;
    MechanumDriveNoLag mechanumDriveNoLag;
    SortingProtoArm sortingProtoArm;
    LiftSystemXY liftSystemXY;
    XSweeper xSweeper;
    DumpingBucket dumpingBucket;

    public void initModules(){
        super.initModules();

        mechanumDriveNoLag = new MechanumDriveNoLag(this); //Driving system
        activeModules.add(mechanumDriveNoLag);

        liftSystemXY = new LiftSystemXY(this); //lift system
        activeModules.add(liftSystemXY);

    }
    public void init(){super.init();}
}
