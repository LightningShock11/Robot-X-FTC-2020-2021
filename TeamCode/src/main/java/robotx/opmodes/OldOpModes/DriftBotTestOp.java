package robotx.opmodes.OldOpModes;

import com.qualcomm.robotcore.eventloop.opmode.*;

import robotx.modules.OldModules.DriftBotTest;
import robotx.libraries.*;

/**
 * Created by Kush Dalal on 10/15/2017.
 */
@TeleOp(name = "DriftTestOp", group = "Tests")
@Disabled
public class DriftBotTestOp  extends XOpMode {
    DriftBotTest driftBotTest;
    public void initModules() {
        super.initModules();

        driftBotTest = new DriftBotTest(this);
        activeModules.add(driftBotTest);

    }
    public void init(){
        super.init();
    }

}
