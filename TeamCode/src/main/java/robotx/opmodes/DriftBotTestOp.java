package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.*;
import com.qualcomm.robotcore.eventloop.opmode.*;

import robotx.libraries.*;
import robotx.modules.*;
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
