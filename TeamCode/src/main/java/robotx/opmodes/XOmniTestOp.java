package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.libraries.XOpMode;
import robotx.modules.XOmniTest;

/**
 * Created by Kushdalal on 10/25/2017.
 */
@TeleOp (name = "XOmniTestOp", group = "Tests")
public class XOmniTestOp extends XOpMode {
    XOmniTest xOmniTest;

    public void initModules(){
        super.initModules();

        xOmniTest = new XOmniTest(this);
        activeModules.add(xOmniTest);
    }
    public void init(){
        super.init();
    }
}
