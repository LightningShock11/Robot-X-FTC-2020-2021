package robotx.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.*;

import robotx.libraries.*;
import robotx.modules.*;

/**
 * Created by Kush on 2/7/2017.
 */
@Autonomous(name = "BetaRedOp", group = "Autonomous")
public class BetaRedOp extends XLinearOpMode {

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

        movement.driveBackward(0.6, 120);

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

        // Drive over to the beacons and spin.
        movement.driveBackward(1.0, 120);
        movement.pointTurnLeft(128);

        // Drive slowly backwards towards the far beacon.
        tileRunnerDrive.setBothPower(0.3);
        while(floorSensor.getLightDetected() <= 0.25  && !stopping() ) {
            telemetry.addData("Floor Sensor Light", floorSensor.getLightDetected() + " / 0.25");
            telemetry.update();
        }
        tileRunnerDrive.setBothPower(0.0);

        // Press the far Red beacon button.
        buttonPusher.pressRedButton();
        safeWaitMillis(500);
        buttonPusher.retractBothArms();

        // Drive quickly Backward towards the close beacon.
        movement.driveBackward(0.8, 80);

        // Drive slowly Backward towards the close beacon.
        tileRunnerDrive.setBothPower(-0.3);
        while(floorSensor.getLightDetected() <= 0.25  && !stopping() ) {
            telemetry.addData("Floor Sensor Light", floorSensor.getLightDetected() + " / 0.25");
            telemetry.update();
        }
        tileRunnerDrive.setBothPower(0.0);

        // Press the close Red beacon button.
        buttonPusher.pressRedButton();
        safeWaitMillis(500);
        buttonPusher.retractBothArms();

        // Turn slightly right away from the corner vortex.
        movement.pointTurnLeft(30);

        // Move towards the corner vortex.
        movement.driveBackward(0.8, 20);

        // Turn back towards the corner ramp.
        movement.pointTurnRight(40);

        // Drive partially onto the ramp.
        tileRunnerDrive.setBothPower(-0.8);
        safeWaitMillis(1700);

        tileRunnerDrive.brakeAllMotors();
        sweeperSystem.turnOff();
    }

}
