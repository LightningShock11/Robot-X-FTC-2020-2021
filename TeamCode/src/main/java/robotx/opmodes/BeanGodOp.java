package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.OldModules.LiftSystem;
import robotx.libraries.XOpMode;
<<<<<<< HEAD
import robotx.modules.LiftSystemY;
=======
import robotx.modules.OneStickDrive;
>>>>>>> f32547d297fcb0057628b018619dac1cf187049c
import robotx.modules.SortingProtoArm;
import robotx.modules.TwoMotorDrive;

@TeleOp(name = "BeanGodOp", group = "Testing")
public class BeanGodOp extends XOpMode {
    TwoMotorDrive twoMotorDrive;
    SortingProtoArm sortingProtoArm;
    LiftSystemY liftSystemY;


    public void initModules(){
        super.initModules();

        twoMotorDrive = new TwoMotorDrive(this);
        activeModules.add(twoMotorDrive);

        sortingProtoArm = new SortingProtoArm(this);
        activeModules.add(sortingProtoArm);

        liftSystemY = new LiftSystemY(this);
        activeModules.add(liftSystemY);
    }
    public void init(){super.init();}

}
