package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.libraries.XOpMode;
import robotx.modules.CharlesDrive;
import robotx.modules.ClawLift;
import robotx.modules.CoachDDrive;
import robotx.modules.DriverCentric;
import robotx.modules.FlywheelIntake;
import robotx.modules.FourMotorTest;
import robotx.modules.MechanumDriveNoLag;
import robotx.modules.OrientationDrive;
import robotx.modules.RgbSignals;
import robotx.modules.StoneArm;
import robotx.modules.StoneClaw;

@TeleOp(name = "JebediahOp", group = "Competition")
public class JebediahOp extends XOpMode {

    //RgbSignals rgbSignals;
    StoneClaw stoneClaw;
    FlywheelIntake flywheelIntake;
    //CoachDDrive coachDDrive;
    OrientationDrive orientationDrive;
    StoneArm stoneArm;


    public void initModules(){
        super.initModules();
        stoneClaw = new StoneClaw(this);
        activeModules.add(stoneClaw);

        flywheelIntake = new FlywheelIntake(this);
        activeModules.add(flywheelIntake);

        stoneArm = new StoneArm(this);
        activeModules.add(stoneArm);

        //rgbSignals = new RgbSignals(this);
        //activeModules.add(rgbSignals);

        //coachDDrive = new CoachDDrive(this);
        //activeModules.add(coachDDrive);

        orientationDrive = new OrientationDrive(this);
        activeModules.add(orientationDrive);
    }
}
