package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.libraries.XOpMode;
import robotx.modules.ContinuousRotationServoTest;

/**
 * Created by Ben Sabo on 12/21/2017.
 */
@TeleOp(name = "ContinuousServoTestOp", group = "tests")
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