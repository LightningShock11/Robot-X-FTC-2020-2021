package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.libraries.XOpMode;
import robotx.modules.GlyphClaw;
import robotx.modules.JewelColor;
import robotx.modules.LedAlwaysOn;
import robotx.modules.MechaBot;
import robotx.modules.MechanumDrive;
import robotx.modules.MechanumDriveNoLag;

/**
 * Created by Ben Sabo on 10/25/2017.
 */
@TeleOp (name = "MechaBotOnOp", group = "Competition")
public class MechaBotOnOp extends XOpMode {

    //MechanumDriveNoLag mechanumDriveNoLag;
    MechanumDrive mechanumDrive;
    GlyphClaw glyphClaw;
    JewelColor jewelColor;
    //LedAlwaysOn ledAlwaysOn;


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

        //ledAlwaysOn = new LedAlwaysOn(this);
        //activeModules.add(ledAlwaysOn);
    }
    public void init(){super.init();}
}
