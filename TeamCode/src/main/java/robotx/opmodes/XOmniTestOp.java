package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.libraries.XOpMode;
import robotx.modules.OmniTest;

/**
 * Created by Ben Sabo on 9/22/2017.
 */
@TeleOp (name = "OmniTestOp", group = "Tests")
public class XOmniTestOp extends XOpMode {
    OmniTest omniTest;

    public void initModules(){
        super.initModules();

        omniTest = new OmniTest(this);
        activeModules.add(omniTest);
    }
    public void init(){
        super.init();
    }
}
