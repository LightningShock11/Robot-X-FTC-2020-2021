package robotx.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.I2cAddr;

import robotx.libraries.AutonomousMovement;
import robotx.libraries.XLinearOpMode;
import robotx.modules.ButtonPusher;
import robotx.modules.LineFollower;
import robotx.modules.SweeperSystem;
import robotx.modules.TileRunnerAuton;
import robotx.modules.TwoMotorDrive;
import robotx.modules.TwoWheelLauncher;
import robotx.modules.XOpticalDistanceSensor;

/**
 * Created by Nicholas on 12/15/2016.
 */
@Autonomous(name = "NoBeaconOp", group = "Autonomous")
@Disabled
public class NoBeaconOp extends XLinearOpMode {

	TwoMotorDrive tileRunnerDrive;
	ButtonPusher buttonPusher;
	TileRunnerAuton sensors;
	AutonomousMovement movement;
	SweeperSystem sweeperSystem;
	TwoWheelLauncher twoWheelLauncher;
	LineFollower lineFollower;

	ColorSensor floorSensor;
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

		floorSensor = hardwareMap.colorSensor.get("floorColorSensor");
		floorSensor.setI2cAddress(I2cAddr.create8bit(0x5c));
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
        long startwaitTime = System.currentTimeMillis();

        while ( (System.currentTimeMillis()-startwaitTime)<15000 && !stopping() ) {
        }

		movement.driveBackward(0.4, 115);

		sweeperSystem.startCollecting();
		twoWheelLauncher.startLaunching();

		// Wait for either 5 seconds to pass or 2 balls to get launched.
		long startLaunchingTime = System.currentTimeMillis();
		while ( (System.currentTimeMillis()-startLaunchingTime)<5000 && twoWheelLauncher.getNumberLaunched()<2 && !stopping() ) {

		}

		sweeperSystem.turnOff();
		twoWheelLauncher.close();

		movement.driveBackward(1.0, 55);


	}

}
