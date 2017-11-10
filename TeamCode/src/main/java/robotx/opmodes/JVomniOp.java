package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.libraries.XOpMode;
import robotx.modules.XOmniTest;
import robotx.modules.GlyphClawJV;

/**
 * Created by Kush dalal on 11/10/2017.
 */
@TeleOp (name = "JVomniOp", group = "Competition")
public class JVomniOp extends XOpMode {

    XOmniTest xOmniTest;
    GlyphClawJV glyphClawJV;

    public void initModules(){
        super.initModules();

        xOmniTest = new XOmniTest(this);
        activeModules.add(xOmniTest);

        glyphClawJV = new GlyphClawJV(this);
        activeModules.add(glyphClawJV);
    }
    public void init(){
        super.init();
    }
}
