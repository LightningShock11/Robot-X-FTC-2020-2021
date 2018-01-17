package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.libraries.XOpMode;
import robotx.modules.GlyphClaw;
import robotx.modules.JewelColor;
import robotx.modules.MechaBot;
import robotx.modules.MechanumDrive;
import robotx.modules.MechanumDriveNoLag;

/**
 * Created by Ben Sabo on 10/25/2017.
 */
@TeleOp (name = "MechaBotOp", group = "Competition")
public class MechaBotOp extends XOpMode {

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
    public void init(){super.init();}
}
