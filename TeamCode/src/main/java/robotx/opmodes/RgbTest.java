package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.libraries.XOpMode;
import robotx.modules.RgbSignals;

@TeleOp(name = "RGB Test", group = "test")
public class RgbTest extends XOpMode {
    RgbSignals rgbSignals;

    public void initModules(){
        rgbSignals = new RgbSignals(this);
        activeModules.add(rgbSignals);
    }
}
