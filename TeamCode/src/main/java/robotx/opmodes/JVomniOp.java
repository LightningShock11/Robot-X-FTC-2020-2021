package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.libraries.XOpMode;
import robotx.modules.XOmniTest;
import robotx.modules.GlyphClaw;

/**
 * Created by Kush dalal on 11/10/2017.
 */
@TeleOp (name = "JVomniOp", group = "Competition")
public class JVomniOp extends XOpMode {

    XOmniTest xOmniTest;
    GlyphClaw glyphClaw;

    public void initModules(){
        super.initModules();

        xOmniTest = new XOmniTest(this);
        activeModules.add(xOmniTest);

        glyphClaw = new GlyphClaw(this);
        activeModules.add(glyphClaw);
    }
    public void init(){
        super.init();
    }
}
