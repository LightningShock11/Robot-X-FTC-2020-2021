package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.libraries.XOpMode;
import robotx.modules.OneStickDrive;
import robotx.modules.SortingProtoArm;
import robotx.modules.TwoMotorDrive;

@TeleOp(name = "BeanGodOp", group = "Testing")
public class BeanGodOp extends XOpMode {
    TwoMotorDrive twoMotorDrive;
    SortingProtoArm sortingProtoArm;


    public void initModules(){
        super.initModules();

        twoMotorDrive = new TwoMotorDrive(this);
        activeModules.add(twoMotorDrive);

        sortingProtoArm = new SortingProtoArm(this);
        activeModules.add(sortingProtoArm);
    }
    public void init(){super.init();}

}
