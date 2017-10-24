package robotx.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

import robotx.libraries.AutonomousMovement;
import robotx.libraries.XLinearOpMode;
import robotx.modules.ButtonPusher;
import robotx.modules.FlipperLauncher;
import robotx.modules.LiftSystem;
import robotx.modules.SweeperSystem;
import robotx.modules.TileRunnerAuton;
import robotx.modules.TwoMotorDrive;

/**
 * Created by Nicholas on 2/7/2017.
 */
@Autonomous(name = "BetaNoBeaconOp", group = "Autonomous")
public class BetaNoBeaconOp extends XLinearOpMode {

    TwoMotorDrive tileRunnerDrive;
    ButtonPusher buttonPusher;
    TileRunnerAuton sensors;
    AutonomousMovement movement;
    SweeperSystem sweeperSystem;
    FlipperLauncher flipperLauncher;
    LiftSystem liftSystem;

    OpticalDistanceSensor floorSensor;

    public void runOpMode() {
        // Do initialization.
        telemetry.addData("Stage", "Init");
        this.updateTelemetry(telemetry);
        tileRunnerDrive = new TwoMotorDrive(this);
        tileRunnerDrive.init();

        buttonPusher = new ButtonPusher(this);
        buttonPusher.init();

        sensors = new TileRunnerAuton(this);
        sensors.init();

        sweeperSystem = new SweeperSystem(this);
        sweeperSystem.init();

        tileRunnerDrive.leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        tileRunnerDrive.rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        sensors.leftMotor = tileRunnerDrive.leftMotor;
        sensors.rightMotor = tileRunnerDrive.rightMotor;

        movement = new AutonomousMovement(this, sensors, tileRunnerDrive);
        movement.init();

        floorSensor = hardwareMap.opticalDistanceSensor.get("BP_floorSensor");
        floorSensor.enableLed(true);

        flipperLauncher = new FlipperLauncher(this);
        flipperLauncher.init();

        liftSystem = new LiftSystem(this);
        liftSystem.init();

        // Calibrate gyro.
        sensors.calibrateGyro();

        waitForStart(); // Wait for start to be pressed.
        telemetry.addData("Stage", "Start");
        this.updateTelemetry(telemetry);

        tileRunnerDrive.start();
        buttonPusher.start();
        sweeperSystem.start();
        liftSystem.start();

        // 10 sec delay
        safeWaitMillis(10_000);

        // Drive forward and turn.
        movement.driveForward(0.6, 116);
        movement.pointTurnRight(90);

        // Launch the first ball.
        flipperLauncher.flipOnce();
        safeWaitMillis(300);

        // Load the second ball.
        sweeperSystem.startCollecting();
        flipperLauncher.openDoor();

        // Wait for 3 seconds to pass for the ball to get into the launcher.
        safeWaitMillis(2000);

        // Launch the second ball.
        sweeperSystem.turnOff();
        flipperLauncher.closeDoor();

        // Wait for 0.5 seconds to pass.
        safeWaitMillis(500);
        flipperLauncher.flipOnce();
        safeWaitMillis(500);

        // Turn towards center vortex base.
        //movement.pointTurnLeft(90);

        // Park partially on the center vortex.
        //movement.driveForward(0.6, 50);

        tileRunnerDrive.brakeAllMotors();
        sweeperSystem.turnOff();
    }

}
