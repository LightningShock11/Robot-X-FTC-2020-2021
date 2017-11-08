package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.modules.*;

import robotx.libraries.XOpMode;

/**
 * Created by Ben Sabo on 10/23/2017.
 */
@TeleOp (name = "ArmColorOp", group = "Tests")
public class ArmColorOp extends XOpMode {
    JewelColor armColor;

    public void initModules(){
        super.initModules();

        armColor = new JewelColor(this);
        activeModules.add(armColor);
    }
    public void init(){super.init();}
}
