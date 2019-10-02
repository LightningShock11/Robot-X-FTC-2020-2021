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
import robotx.modules.RgbSignals;
import robotx.modules.StoneClaw;

@TeleOp(name = "JebediahOp", group = "Tests")
public class JebediahOp extends XOpMode {

    //FlywheelIntake flywheelIntake;
    //ClawLift clawLift;
    //DriverCentric driverCentric;
    //RgbSignals rgbSignals;
    //StoneClaw stoneClaw;
    CoachDDrive coachDDrive;
    FourMotorTest fourMotorTest;


    public void initModules(){
        super.initModules();

        //driverCentric = new DriverCentric(this);
        //activeModules.add(driverCentric);

        //flywheelIntake = new FlywheelIntake(this);
       // activeModules.add(flywheelIntake);

        //clawLift = new ClawLift(this);
        //activeModules.add(clawLift);

        //stoneClaw = new StoneClaw(this);
        //activeModules.add(stoneClaw);

        //rgbSignals = new RgbSignals(this);
        //activeModules.add(rgbSignals);

        coachDDrive = new CoachDDrive(this);
        activeModules.add(coachDDrive);

        fourMotorTest = new FourMotorTest(this);
        activeModules.add(fourMotorTest);
    }
}
