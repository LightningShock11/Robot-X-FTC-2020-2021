package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.libraries.XOpMode;
import robotx.modules.*;

/**
 * Created by KD on 11/25/2017.
 */

@TeleOp (name = "MechanumModuleOp", group = "idk")
public class MechanumModuleOp extends XOpMode {
    MechanumDrive mechanumDrive;

    public void initModules(){
        super.initModules();

        mechanumDrive = new MechanumDrive(this);
        activeModules.add(mechanumDrive);
    }
    public void init(){
        super.init();
    }
}
