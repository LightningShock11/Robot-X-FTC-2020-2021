package robotx.opmodes;

import robotx.libraries.XOpMode;
import robotx.modules.RgbSignals;

public class RgbTest extends XOpMode {
    RgbSignals rgbSignals;

    public void initModules(){
        rgbSignals = new RgbSignals(this);
        activeModules.add(rgbSignals);
    }
}
