package robotx.opmodes;

import android.graphics.Paint;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.libraries.XOpMode;
import robotx.modules.CapstonePositioning;
import robotx.modules.ContinuousServoTest;
import robotx.modules.FlywheelIntake;
import robotx.modules.FoundationPins;
import robotx.modules.OrientationDrive;
import robotx.modules.RgbRedTeam;
import robotx.modules.StoneArm;
import robotx.modules.StoneLift;

@TeleOp(name = "JebediahNoRgbOP", group = "Competition")
public class JebediahNoRgbOp extends XOpMode {

    //public StoneClaw stoneClaw;
    public FlywheelIntake flywheelIntake;
    //CoachDDrive coachDDrive;
    public OrientationDrive orientationDrive;
    public StoneArm stoneArm;
    public StoneLift stoneLift;
    public FoundationPins foundationPins;
    public CapstonePositioning capstonePositioning;

    public void initModules(){
        super.initModules();
        //stoneClaw = new StoneClaw(this);
        //activeModules.add(stoneClaw);

        flywheelIntake = new FlywheelIntake(this);
        activeModules.add(flywheelIntake);

        stoneArm = new StoneArm(this);
        activeModules.add(stoneArm);

        //coachDDrive = new CoachDDrive(this);
        //activeModules.add(coachDDrive);

        orientationDrive = new OrientationDrive(this);
        activeModules.add(orientationDrive);

        stoneLift = new StoneLift(this);
        activeModules.add(stoneLift);

        foundationPins = new FoundationPins(this);
        activeModules.add(foundationPins);

        capstonePositioning = new CapstonePositioning(this);
        activeModules.add(capstonePositioning);
    }
}
