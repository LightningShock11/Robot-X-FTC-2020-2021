package robotx.opmodes.OldOpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.libraries.XOpMode;
import robotx.modules.OldModules.GlyphClaw;
import robotx.modules.OldModules.JewelColor;
import robotx.modules.MechanumDriveNoLag;

/**
 * Created by Ben Sabo on 2/8/2018.
 */
@TeleOp(name = "MechaBotNoLedOp", group = "Competition")
public class MechaBotNoLedOp extends XOpMode {
    MechanumDriveNoLag mechanumDriveNoLag;
    GlyphClaw glyphClaw;
    JewelColor jewelColor;

    public void initModules(){
        super.initModules();

        mechanumDriveNoLag = new MechanumDriveNoLag(this);
        activeModules.add(mechanumDriveNoLag);


        glyphClaw = new GlyphClaw(this);
        activeModules.add(glyphClaw);

        jewelColor = new JewelColor(this);
        activeModules.add(jewelColor);
    }
}
