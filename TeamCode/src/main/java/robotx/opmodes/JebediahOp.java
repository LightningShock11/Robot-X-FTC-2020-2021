package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.libraries.XOpMode;
import robotx.modules.ClawLift;
import robotx.modules.DriverCentric;
import robotx.modules.FlywheelIntake;
import robotx.modules.MechanumDriveNoLag;
import robotx.modules.RgbSignals;
import robotx.modules.StoneClaw;

@TeleOp(name = "JebediahOp", group = "Tests")
public class JebediahOp extends XOpMode {

    //MechanumDriveNoLag mechanumDriveNoLag;
    FlywheelIntake flywheelIntake;
    ClawLift clawLift;
    DriverCentric driverCentric;
    RgbSignals rgbSignals;
    StoneClaw stoneClaw;


    public void initModules(){
        super.initModules();

        //mechanumDriveNoLag= new MechanumDriveNoLag(this);
        //activeModules.add(mechanumDriveNoLag);

        driverCentric = new DriverCentric(this);
        activeModules.add(driverCentric);

        flywheelIntake = new FlywheelIntake(this);
        activeModules.add(flywheelIntake);

        clawLift = new ClawLift(this);
        activeModules.add(clawLift);

        stoneClaw = new StoneClaw(this);
        activeModules.add(stoneClaw);

        //rgbSignals = new RgbSignals(this);
        //activeModules.add(rgbSignals);
    }
}
