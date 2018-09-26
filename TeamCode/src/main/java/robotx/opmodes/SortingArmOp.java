package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.libraries.XOpMode;
import robotx.modules.SortingProtoArm;

@TeleOp(name = "SortingArmOp", group = "Testing")
public class SortingArmOp extends XOpMode {
    SortingProtoArm sortingProtoArm;

    public void initModules(){
        super.initModules();
        sortingProtoArm = new SortingProtoArm(this);
        activeModules.add(sortingProtoArm);
    }
    public void init(){
        super.init();
    }
}
