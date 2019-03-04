package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcontroller.external.samples.ConceptTensorFlowObjectDetection;

import robotx.libraries.XOpMode;
import robotx.modules.DumpingBucket;
import robotx.modules.GondalaTest;
import robotx.modules.LiftSystemXY;
import robotx.modules.MechanumDrive;
import robotx.modules.MechanumDriveNoLag;
import robotx.modules.RLdrive;
import robotx.modules.SortingProtoArm;
import robotx.modules.XSweeper;

@TeleOp(name = "IvanOp", group = "Worlds")
public class IvanOp extends XOpMode {
    MechanumDriveNoLag mechanumDriveNoLag;
    XSweeper xSweeper;

    public void initModules(){
        super.initModules();

        mechanumDriveNoLag = new MechanumDriveNoLag(this); //Driving system
        activeModules.add(mechanumDriveNoLag);
    }
    public void init(){super.init();}
}
