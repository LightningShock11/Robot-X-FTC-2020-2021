package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.libraries.XOpMode;
import robotx.modules.*;

/**
 * Created by Robot-X Team Member on 11/09/2017.
 */
@TeleOp (name = "OmniTestOp", group = "Tests")
public class OmniTestOp extends XOpMode {
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
