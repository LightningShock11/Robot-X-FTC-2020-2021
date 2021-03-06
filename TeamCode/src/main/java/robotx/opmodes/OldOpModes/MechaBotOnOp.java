package robotx.opmodes.OldOpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.libraries.XOpMode;
import robotx.modules.OldModules.GlyphClaw;
import robotx.modules.OldModules.JewelColor;
import robotx.modules.OldModules.LedAlwaysOn;
import robotx.modules.MechanumDriveNoLag;
import robotx.modules.TeleOpTimerDisplay;

/**
 * Created by Ben Sabo on 10/25/2017.
 */
@TeleOp (name = "MechaBotOnOp", group = "Competition")
public class MechaBotOnOp extends XOpMode {

    MechanumDriveNoLag mechanumDriveNoLag;
    GlyphClaw glyphClaw;
    JewelColor jewelColor;
    LedAlwaysOn ledAlwaysOn;
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

        ledAlwaysOn = new LedAlwaysOn(this);
        activeModules.add(ledAlwaysOn);

        timer = new TeleOpTimerDisplay(this);
        timer.displayGamePeriod = true;
        activeModules.add(timer);
    }
    public void init(){super.init();}
}
