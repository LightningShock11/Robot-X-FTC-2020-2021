package robotx.OldOpModes;

import com.qualcomm.robotcore.eventloop.opmode.*;

import robotx.modules.*;

import robotx.libraries.XOpMode;

/**
 * Created by Ben Sabo on 10/23/2017.
 */
@TeleOp (name = "ArmColorOp", group = "Tests")
@Disabled
public class ArmColorOp extends XOpMode {
    JewelColor armColor;

    public void initModules(){
        super.initModules();

        armColor = new JewelColor(this);
        activeModules.add(armColor);
    }
    public void init(){super.init();}
}
