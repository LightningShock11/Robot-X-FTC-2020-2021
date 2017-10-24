package robotx.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.*;

import robotx.libraries.*;
import robotx.modules.*;

/**
 * Created by Nicholas on 2/2/2017.
 */
@Autonomous(name = "CarefulBlueOp", group = "Autonomous")
public class CarefulBlueOp extends XLinearOpMode {

    TwoMotorDrive tileRunnerDrive;
    ButtonPusher buttonPusher;
    TileRunnerAuton sensors;
    AutonomousMovement movement;
    SweeperSystem sweeperSystem;
    TwoWheelLauncher twoWheelLauncher;
    LineFollower lineFollower;

    OpticalDistanceSensor floorSensor;
    XOpticalDistanceSensor opticalDistanceSensor;

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

        twoWheelLauncher = new TwoWheelLauncher(this);
        twoWheelLauncher.init();

        tileRunnerDrive.leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        tileRunnerDrive.rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        sensors.leftMotor = tileRunnerDrive.leftMotor;
        sensors.rightMotor = tileRunnerDrive.rightMotor;

        movement = new AutonomousMovement(this, sensors, tileRunnerDrive);
        movement.init();

        floorSensor = hardwareMap.opticalDistanceSensor.get("BP_floorSensor");
        floorSensor.enableLed(true);

        opticalDistanceSensor = new XOpticalDistanceSensor(this, "BP_distanceSensor");

        lineFollower = new LineFollower(this, tileRunnerDrive);
        lineFollower.init();

        // Calibrate gyro.
        sensors.calibrateGyro();

        waitForStart(); // Wait for start to be pressed.
        telemetry.addData("Stage", "Start");
        this.updateTelemetry(telemetry);

        tileRunnerDrive.start();
        buttonPusher.start();
        sweeperSystem.start();
        twoWheelLauncher.start();

        movement.driveBackward(0.4, 75);

        sweeperSystem.startCollecting();
        twoWheelLauncher.startLaunching();

        // Wait for either 5 seconds to pass or 2 balls to get launched.
        long startLaunchingTime = System.currentTimeMillis();
        while ( (System.currentTimeMillis()-startLaunchingTime)<5000 && !stopping() ) {

        }

        sweeperSystem.turnOff();
        twoWheelLauncher.close();

        movement.pointTurnRight(85);

        movement.driveBackward(1.0, 80);

        movement.pointTurnLeft(40);

        movement.driveBackward(1.0, 20);

        movement.pointTurnLeft(40);

        // Drive forward slowly until we see the white line.
        tileRunnerDrive.setBothPower(-0.15);
        while(floorSensor.getLightDetected() <= 0.25  && !stopping() ) {
            telemetry.addData("Floor Sensor Light", floorSensor.getLightDetected());
            telemetry.update();
        }
        tileRunnerDrive.setBothPower(0.0);

        movement.driveBackward(0.5, 12);

        tileRunnerDrive.setRightPower(-0.15);
        tileRunnerDrive.setLeftPower(0.15);
        while(floorSensor.getLightDetected() <= 0.25 && !stopping() ) {
            telemetry.addData("Floor Sensor Light", floorSensor.getLightDetected());
            telemetry.update();
        }
        tileRunnerDrive.setBothPower(0.0);

        tileRunnerDrive.setBothPower(-0.10);
        while(opticalDistanceSensor.distanceInCentimeters()>=5.5  && !stopping() ) {
            telemetry.addData("ODS (goal 3.5)", opticalDistanceSensor.distanceInCentimeters());
            telemetry.update();
        }
        tileRunnerDrive.setBothPower(0.0);

        buttonPusher.pressBlueButton();

        long startFinalWaitTime = System.currentTimeMillis();
        while( !stopping() && System.currentTimeMillis()-startFinalWaitTime > 3000 ) {
            // Wait 3 seconds for the arm to extend and press button.
        }

        tileRunnerDrive.brakeAllMotors();
        sweeperSystem.turnOff();
        twoWheelLauncher.close();
    }

}
