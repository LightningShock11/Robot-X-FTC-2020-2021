package robotx.OldOpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.libraries.XOpMode;
import robotx.OldModules.GlyphClaw;
import robotx.OldModules.JewelColor;
import robotx.OldModules.LedFlash;
import robotx.modules.MechanumDriveNoLag;
import robotx.OldModules.TeleOpTimerDisplay;

/**
 * Created by Ben Sabo on 10/25/2017.
 */
@TeleOp (name = "MechaBotFlashOp", group = "Competition")
public class MechaBotFlashOp extends XOpMode {

    MechanumDriveNoLag mechanumDriveNoLag;
    //MechanumDrive mechanumDrive;
    GlyphClaw glyphClaw;
    JewelColor jewelColor;
    LedFlash ledFlash;
    TeleOpTimerDisplay timer;

    public void initModules(){
        super.initModules();

        /*mechanumDrive = new MechanumDrive(this);
        activeModules.add(mechanumDrive);*/


        mechanumDriveNoLag = new MechanumDriveNoLag(this);
        activeModules.add(mechanumDriveNoLag);

        glyphClaw = new GlyphClaw(this);
        activeModules.add(glyphClaw);

        jewelColor = new JewelColor(this);
        activeModules.add(jewelColor);

        ledFlash = new LedFlash(this);
        activeModules.add(ledFlash);

        timer = new TeleOpTimerDisplay(this);
        timer.displayGamePeriod = true;
        activeModules.add(timer);
    }
    public void init(){super.init();}
}
