package robotx.opmodes.OldOpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.libraries.XOpMode;
import robotx.modules.ContinuousRotationServoTest;

/**
 * Created by Ben Sabo on 12/21/2017.
 */
@TeleOp(name = "ContinuousServoTestOp", group = "Tests")
public class ContinuousServoTestOp extends XOpMode {
    ContinuousRotationServoTest continuousServoTestOp;

    public void initModules() {
        super.initModules();

        continuousServoTestOp = new ContinuousRotationServoTest(this);
        activeModules.add(continuousServoTestOp);
    }

    public void init() {
        super.init();
    }
}