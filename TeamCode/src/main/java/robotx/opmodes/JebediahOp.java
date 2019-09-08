package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.libraries.XOpMode;
import robotx.modules.ClawLift;
import robotx.modules.FlywheelIntake;
import robotx.modules.MechanumDriveNoLag;

@TeleOp(name = "JebediahOp", group = "Tests")
public class JebediahOp extends XOpMode {

    MechanumDriveNoLag mechanumDriveNoLag;
    FlywheelIntake flywheelIntake;
    ClawLift clawLift;


    public void initModules(){
        super.initModules();

        mechanumDriveNoLag= new MechanumDriveNoLag(this);
        activeModules.add(mechanumDriveNoLag);

        flywheelIntake = new FlywheelIntake(this);
        activeModules.add(flywheelIntake);

        clawLift = new ClawLift(this);
        activeModules.add(clawLift);
    }
}
