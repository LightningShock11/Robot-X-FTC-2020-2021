package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.libraries.XOpMode;
import robotx.modules.MechanumDriveNoLag;
import robotx.modules.PlanetArm;

@TeleOp(name = "TestBotOp", group = "Tests")
public class TestBotOp extends XOpMode {
    PlanetArm planetArm;
    //MechanumDriveNoLag mechanumDriveNoLag;

    public void initModules(){
        super.initModules();

        planetArm = new PlanetArm(this);
        activeModules.add(planetArm);

        //mechanumDriveNoLag = new MechanumDriveNoLag(this);
        //activeModules.add(mechanumDriveNoLag);
    }
}
