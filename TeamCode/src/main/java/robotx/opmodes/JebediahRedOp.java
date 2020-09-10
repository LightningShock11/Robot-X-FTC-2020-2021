package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcontroller.external.samples.ConceptVuforiaSkyStoneNavigation;

import robotx.libraries.XOpMode;
import robotx.modules.CharlesDrive;
import robotx.modules.ClawLift;
import robotx.modules.CoachDDrive;
import robotx.modules.DriverCentric;
import robotx.modules.FlywheelIntake;
import robotx.modules.FoundationPins;
import robotx.modules.FourMotorTest;
import robotx.modules.MechanumDriveNoLag;
import robotx.modules.OrientationDrive;
import robotx.modules.RgbRedTeam;
import robotx.modules.RgbSignals;
import robotx.modules.StoneArm;
import robotx.modules.StoneClaw;
import robotx.modules.StoneLift;

@Disabled
@TeleOp(name = "JebediahRedOp", group = "Competition")
public class JebediahRedOp extends XOpMode {

    RgbRedTeam rgbRedTeam;
    //public StoneClaw stoneClaw;
    public FlywheelIntake flywheelIntake;
    //CoachDDrive coachDDrive;
    public OrientationDrive orientationDrive;
    public StoneArm stoneArm;
    public StoneLift stoneLift;
    public FoundationPins foundationPins;


    public void initModules(){
        super.initModules();
        //stoneClaw = new StoneClaw(this);
        //activeModules.add(stoneClaw);

        flywheelIntake = new FlywheelIntake(this);
        activeModules.add(flywheelIntake);

        stoneArm = new StoneArm(this);
        activeModules.add(stoneArm);

        rgbRedTeam = new RgbRedTeam(this);
        activeModules.add(rgbRedTeam);

        //coachDDrive = new CoachDDrive(this);
        //activeModules.add(coachDDrive);

        orientationDrive = new OrientationDrive(this);
        activeModules.add(orientationDrive);

        stoneLift = new StoneLift(this);
        activeModules.add(stoneLift);

        foundationPins = new FoundationPins(this);
        activeModules.add(foundationPins);
    }
}
