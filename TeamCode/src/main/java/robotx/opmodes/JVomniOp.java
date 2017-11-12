package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.libraries.XOpMode;
import robotx.modules.*;

/**
 * Created by Kush dalal on 11/10/2017.
 */
@TeleOp (name = "JVomniOp", group = "Competition")
public class JVomniOp extends XOpMode {

    OmniTest omniTest;
    GlyphClawJV glyphClawJV;

    public void initModules() {
        super.initModules();

        omniTest = new OmniTest(this);
        activeModules.add(omniTest);

        glyphClawJV = new GlyphClawJV(this);
        activeModules.add(glyphClawJV);
    }
    public void init(){
        super.init();
    }
}
