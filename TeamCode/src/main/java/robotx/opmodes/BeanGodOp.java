package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.libraries.XOpMode;
import robotx.modules.LiftSystemXY;
import robotx.modules.OneStickDrive;
import robotx.modules.RLdrive;
import robotx.modules.SortingProtoArm;
import robotx.modules.SweeperSystem;
import robotx.modules.TwoMotorDrive;
import robotx.modules.XSweeper;

@TeleOp(name = "BeanGodOp", group = "Testing")
public class BeanGodOp extends XOpMode {
    //TwoMotorDrive twoMotorDrive;
    OneStickDrive oneStickDrive;
    //RLdrive rLdrive;
    SortingProtoArm sortingProtoArm;
    LiftSystemXY liftSystemXY;
    XSweeper xSweeper;

    public void initModules(){
        super.initModules();

        //twoMotorDrive = new TwoMotorDrive(this);
        //activeModules.add(twoMotorDrive);

        oneStickDrive = new OneStickDrive(this);
        activeModules.add(oneStickDrive);

        //rLdrive = new RLdrive(this);
        //activeModules.add(rLdrive);

        sortingProtoArm = new SortingProtoArm(this);
        activeModules.add(sortingProtoArm);

        liftSystemXY = new LiftSystemXY(this);
        activeModules.add(liftSystemXY);

        xSweeper = new XSweeper(this);
        activeModules.add(xSweeper);

    }
    public void init(){super.init();}
}
