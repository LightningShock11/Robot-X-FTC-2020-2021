package robotx.OldOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.libraries.XOpMode;
import robotx.OldModules.TwoWheelSucker;

/**
 * Created by Robot-X Team Member on 11/09/2017.
 */
@TeleOp (name = "TwoWheelSuckerOp", group = "Tests")
@Disabled
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
