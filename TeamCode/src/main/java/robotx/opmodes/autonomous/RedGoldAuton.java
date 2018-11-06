package robotx.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import robotx.libraries.AutonomousMovement;
import robotx.libraries.XLinearOpMode;
import robotx.modules.TwoMotorDrive;
import robotx.modules.TwoWheelAutonIMU;

/**
 * Created by Kush Dalal on 10/24/2018.
 */

@Autonomous(name = "RedGoldAuton", group = "Autonomous")
public class RedGoldAuton extends XLinearOpMode {

    AutonomousMovement movement;

    TwoWheelAutonIMU sensors;
    TwoMotorDrive twoMotorDrive;


    public void runOpMode() {
        //////////////////////init//////////////////////////
        telemetry.addData("Stage", "Init");
        this.updateTelemetry(telemetry);

        twoMotorDrive = new TwoMotorDrive(this);
        twoMotorDrive.init();

        sensors = new TwoWheelAutonIMU(this);
        sensors.init();

        //movement = new AutonomousMovement(this, sensors, twoMotorDrive);
        movement.init();


        ////////////////////////////////////////////////////

        movement.driveForward(1, 10);
        sleep(10);
        movement.stop();






    }

}
