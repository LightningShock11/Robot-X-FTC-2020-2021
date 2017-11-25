package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.libraries.XOpMode;
import robotx.modules.TwoWheelSucker;

/**
 * Created by Robot-X Team Member on 11/09/2017.
 */
@TeleOp (name = "TwoWheelSuckerOp", group = "Tests")
public class TwoWheelSuckerOp extends XOpMode {
    TwoWheelSucker twoWheelSucker;

    public void initModules(){
        super.initModules();

        twoWheelSucker = new TwoWheelSucker(this);
        activeModules.add(twoWheelSucker);
    }
    public void init(){
        super.init();
    }
}