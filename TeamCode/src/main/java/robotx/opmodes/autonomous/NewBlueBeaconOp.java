package robotx.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.*;

import robotx.libraries.*;
import robotx.modules.*;

/**
 * Created by Nicholas on 12/5/2016.
 */
@Autonomous(name = "NewBlueBeaconOp", group = "Autonomous")
@Disabled
public class NewBlueBeaconOp extends XLinearOpMode {

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

		movement.driveBackward(0.4, 75);

		sweeperSystem.startCollecting();
		twoWheelLauncher.startLaunching();

		// Wait for either 5 seconds to pass or 2 balls to get launched.
		long startLaunchingTime = System.currentTimeMillis();
		while ( (System.currentTimeMillis()-startLaunchingTime)<5000 && !stopping() ) {

		}

		sweeperSystem.turnOff();
		twoWheelLauncher.close();

		movement.pointTurnRight(56);

		movement.driveBackward(1.0, 80);

		tileRunnerDrive.setBothPower(-0.15);
		while(floorSensor.green() <= 7  && !stopping() ) {
			telemetry.addData("Floor Sensor Green", floorSensor.green());
			telemetry.update();
		}
		tileRunnerDrive.setBothPower(0.0);

		tileRunnerDrive.setBothPower(-0.15);
		sleep(500);
		tileRunnerDrive.setBothPower(0.0);

		lineFollower.followFromRightAuton();

		tileRunnerDrive.setRightPower(-0.15);
		while(floorSensor.green() <= 7  && !stopping() ) {
			telemetry.addData("Floor Sensor Green", floorSensor.green());
			telemetry.update();
		}
		tileRunnerDrive.setBothPower(0.0);

		buttonPusher.pressBlueButton();
		sleep(1000);
		buttonPusher.retractBothArms();

		// Maneuver over to knock the cap ball off and park on the center zone.
		/*movement.driveForward(0.8, 40);

		movement.pointTurnRight(40);

		movement.driveForward(0.8, 40);

		movement.pointTurnLeft(64);

		movement.driveForward(0.8, 50);*/

		movement.driveForward(0.8, 20);

		movement.pointTurnRight(40);

		movement.driveForward(0.8, 40);

		movement.pointTurnLeft(80);
		
		movement.driveForward(0.8, 80);

        tileRunnerDrive.brakeAllMotors();
        sweeperSystem.turnOff();
        twoWheelLauncher.close();
	}

}
