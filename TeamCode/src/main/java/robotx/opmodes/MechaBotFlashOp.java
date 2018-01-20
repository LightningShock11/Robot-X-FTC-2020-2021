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
@TeleOp (name = "MechaBotFlashOp", group = "Competition")
public class MechaBotFlashOp extends XOpMode {

    //MechanumDriveNoLag mechanumDriveNoLag;
    MechanumDrive mechanumDrive;
    GlyphClaw glyphClaw;
    JewelColor jewelColor;


    public void initModules(){
        super.initModules();

        mechanumDrive = new MechanumDrive(this);
        activeModules.add(mechanumDrive);


        /*mechanumDrive = new MechanumDrive(this);
        activeModules.add(mechanumDrive);
        */

        glyphClaw = new GlyphClaw(this);
        activeModules.add(glyphClaw);

        jewelColor = new JewelColor(this);
        activeModules.add(jewelColor);
    }
    public void init(){super.init();}
}
