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

        //Initialize all systems on the robot. ALL SYSTEMS GO
        telemetry.addData("Stage", "Init");
        this.updateTelemetry(telemetry);

        twoMotorDrive = new TwoMotorDrive(this);
        twoMotorDrive.init();

        sensors = new TwoWheelAutonIMU(this);
        sensors.init();

        movement = new AutonomousMovement(this, sensors, twoMotorDrive);
        movement.init();

        sensors.leftMotor = twoMotorDrive.leftMotor;
        sensors.rightMotor = twoMotorDrive.rightMotor;


        /////////////////////Start//////////////////////////

        //Start all systems.
        waitForStart(); // Wait for start to be pressed.
        telemetry.addData("Stage", "Start");
        telemetry.addLine().addData("Heading", sensors.getHeadingAngle());
        this.updateTelemetry(telemetry);

        movement.start();
        sensors.start();
        twoMotorDrive.start();


        /////////////////////Movement///////////////////////

        //movement test
<<<<<<< HEAD
        movement.driveForward(3.0, 10.0);
=======
        movement.driveForward(1, -10);
>>>>>>> b68980321645d0ca4732aaa6a63d53ee9b52729d
        sleep(2000);
        movement.stop();



        ////////////////////////////////////////////////////
    }

}
