package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.libraries.XOpMode;
import robotx.modules.TwoMotorDrive;

@TeleOp(name = "BeanGodOp", group = "Testing")
public class BeanGodOp extends XOpMode {
    TwoMotorDrive twoMotorDrive;

    public void initModules(){
        super.initModules();

        twoMotorDrive = new TwoMotorDrive(this);
        activeModules.add(twoMotorDrive);
    }
    public void init(){super.init();}

}
