package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.*;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.libraries.*;
import robotx.modules.*;

/**
 * Created by Ben Sabo on 9/22/2017.
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
