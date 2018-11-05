package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.libraries.XOpMode;
import robotx.modules.LiftSystemXY;
import robotx.modules.SortingProtoArm;
import robotx.modules.SweeperSystem;
import robotx.modules.TwoMotorDrive;

@TeleOp(name = "BeanGodOp", group = "Testing")
public class BeanGodOp extends XOpMode {
    TwoMotorDrive twoMotorDrive;
    SortingProtoArm sortingProtoArm;
    LiftSystemXY liftSystemXY;
    SweeperSystem sweeperSystem;


    public void initModules(){
        super.initModules();

        twoMotorDrive = new TwoMotorDrive(this);
        activeModules.add(twoMotorDrive);

        sortingProtoArm = new SortingProtoArm(this);
        activeModules.add(sortingProtoArm);

        liftSystemXY = new LiftSystemXY(this);
        activeModules.add(liftSystemXY);

        sweeperSystem = new SweeperSystem(this);
        activeModules.add(sweeperSystem);
    }
    public void init(){super.init();}

}
