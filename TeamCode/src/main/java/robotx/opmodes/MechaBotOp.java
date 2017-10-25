package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.libraries.XOpMode;
import robotx.modules.MechaBot;

/**
 * Created by Ben Sabo on 10/25/2017.
 */
@TeleOp (name = "MechaBotOp", group = "Competition")
public class MechaBotOp extends XOpMode {

    MechaBot mechaBot;

    public void initModules(){
        super.initModules();

        mechaBot = new MechaBot(this);
        activeModules.add(mechaBot);
    }
    public void init(){super.init();}
}
